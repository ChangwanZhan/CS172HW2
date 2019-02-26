package pkg1;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class DocLength {
	
	
	public Map<String, Double> CreateIndex( Map<String, Map<String, Double>> TFIDFIndex)
	{
		// stores the length information for each documents
		Map<String, Double> DocLength = new TreeMap<String, Double>();
		for(Entry<String, Map<String, Double>> entry: TFIDFIndex.entrySet())
		{
			String DocID = entry.getKey();
			double length = 0;
			for(Entry<String, Double> entry2: entry.getValue().entrySet())
			{
				length = length+entry2.getValue()*entry2.getValue();
			}
			length = Math.sqrt(length);
			DocLength.put(DocID, length);
		}		
		return DocLength;
	}
	
	
	public Map<String, Double> CreateIDFIndex(int DocSum, Map<String, Map<String, Integer>> PostIndex)
	{
		//Word-IDF
		//store the idf value for each word
		Map<String, Double> IDFIndex = new TreeMap<String, Double>();
		//key:word£¬value:idf
		for (Entry<String, Map<String, Integer>> entry: PostIndex.entrySet())
		{	
			//for (Entry<String, Integer> entry2: entry.getValue().entrySet()) System.out.println(entry2.getKey()+entry2.getValue());
			String Word = entry.getKey();
			int DocNum = entry.getValue().get("Sum");
			double idf = (double)DocSum/(double)DocNum;
			idf = Math.log(idf)/Math.log(2);
			idf++;
			IDFIndex.put(Word, idf);			
		}		
		return IDFIndex;
	}
	
	public Map<String, Map<String, Double>> CreateTFIndex(Map<String, Integer> DocIndex, Map<String, Map<String, Integer>> PostIndex)
	{
		//calculate tf value for each word in different documents and store it
		Map<String, Map<String, Double>> TFIndex = new TreeMap<String, Map<String, Double>>();
		// key:word£¬value:docid-tf
		for (Entry<String, Map<String, Integer>> entry: PostIndex.entrySet())
		{	
			String Word = entry.getKey();
			Map<String, Double> DocTF = new TreeMap<String, Double>();
			
			for (Entry<String, Integer> entry2: entry.getValue().entrySet())
			{
				String Doc = entry2.getKey();
				if(!Doc.equals("Sum"))
				{
					double tf1 = entry2.getValue();
					double tf2 = DocIndex.get(Doc);
					double tf = tf1/tf2;
					DocTF.put(Doc, tf);			
				}
				
			}			
			TFIndex.put(Word, DocTF);			
		}			
		return TFIndex;
	}
	
	public Map<String, Map<String, Double>> CreateTFIDFIndex(Map<String, Double> IDFIndex, Map<String, Map<String, Double>> TFIndex)
	{
		Map<String, Map<String, Double>> TFIDFIndex = new TreeMap<String, Map<String, Double>>();
		//key:docid£¬value:word-tfidf
		
		for(Entry<String, Double> entry: IDFIndex.entrySet())
		{
			String word = entry.getKey();
			//Map<String, Double> WordTFIDF = new TreeMap<String, Double>();
			Map<String, Double> DocTF = new TreeMap<String, Double>();
			if(TFIndex.containsKey(word))
			{
				DocTF = TFIndex.get(word);
				double idf = entry.getValue();
				for(Entry<String, Double> entry2: DocTF.entrySet())
				{
					String DocID = entry2.getKey();
					//System.out.println(DocID);
					double tf = entry2.getValue();
					double tfidf = tf*idf;
					if(TFIDFIndex.containsKey(DocID))
					{
						TFIDFIndex.get(DocID).put(word, tfidf);
					}
					else
					{
						Map<String, Double> initial = new TreeMap<String, Double>();
						initial.put(word, tfidf);
						TFIDFIndex.put(DocID, initial);
					}
				}
			}
						
		}		
		return TFIDFIndex;
	}
	
}
