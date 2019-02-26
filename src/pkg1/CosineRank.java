package pkg1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class CosineRank {
	public Map<String, Map<String, Double>> CosinSimilarity(Map<String, Map<String, Double>> QueryTFIDF, Map<String, Map<String, Double>> DocTFIDF, Map<String, Double> DocLength, Map<String, Double> QueryLength)
	{
		// QueryID-<DocID-Cosine Similarity>
		// calculate and store the cosine similarity
		Map<String, Map<String, Double>> CosSim = new TreeMap<String, Map<String, Double>>();
		for (Entry<String, Map<String, Double>> entry: QueryTFIDF.entrySet())//entry: QueryID
		{
			String QueryID = entry.getKey();
			Map<String, Double> QueryWord = entry.getValue();
			Map<String, Double> DocCosSim = new TreeMap<String, Double>();			
			for (Entry<String, Map<String, Double>> entry2: DocTFIDF.entrySet())//entry2: DocID
			{
				String DocID = entry2.getKey();
				double cdot = 0;
				for (Entry<String, Double> entry3: QueryWord.entrySet())// entry3: Word in Query
				{
					String Word = entry3.getKey();
					double qtfidf = entry3.getValue();
					if(entry2.getValue().containsKey(Word))
					{
						double dtfidf = entry2.getValue().get(Word);
						cdot = cdot+qtfidf*dtfidf;
					}
				}
				double cossim = cdot/(DocLength.get(DocID)*QueryLength.get(QueryID));
				DocCosSim.put(DocID, cossim);
			}
			CosSim.put(QueryID, DocCosSim);
		}		
		return CosSim;
	}
	
	
	
	public Map<String, Map<String, Integer>> SortCosine(Map<String, Map<String, Double>> CosSim)
	{
		//Query-<DocID-rank>
		//sort and rank documents by their cosine similarity
		Map<String, Map<String, Integer>> QDocRank = new TreeMap<String, Map<String, Integer>>();
		for (Entry<String, Map<String, Double>> entry: CosSim.entrySet())
		{
			String QueryID = entry.getKey();
			List<Entry<String, Double>> list = new ArrayList<Entry<String,Double>>(entry.getValue().entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
			{

				@Override
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// TODO Auto-generated method stub
					return o2.getValue().compareTo(o1.getValue());
				}
				
			});
			int rank = 1;
			Map<String, Integer> DocRank = new TreeMap<String, Integer>();
			for (Entry<String, Double> entry2: list)
			{
				DocRank.put(entry2.getKey(), rank);
				rank++;
			}
			QDocRank.put(QueryID, DocRank);
		}
				
		return QDocRank;
	}
	
}

