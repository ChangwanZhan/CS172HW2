package pkg1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.stanford.nlp.process.Stemmer;

public class QueryPostIndex {
	private String fpath ="CS172_PS2\\data\\query_list.txt";
	private ReadFiles rf = new ReadFiles();
	
	public Map<String, Map<String, Integer>> CreateIndex() throws IOException
	{
		Map<String, Map<String, Integer>> QPostIndex = new TreeMap<String, Map<String, Integer>>();
		BufferedReader reader = new BufferedReader(new FileReader(fpath));
		String read;
		Stemmer s = new Stemmer();
		String queryid = null;
		while((read = reader.readLine())!=null)
		{	
			read = read.replaceAll( "\\p{Punct}", "" ); 
			StringTokenizer st = new StringTokenizer(read," ");
			String token = st.nextToken();
			String QueryID = token;			
 			while(st.hasMoreElements()) 
 			{
 				token = st.nextToken().toLowerCase();
				token = s.stem(token);
				if(!rf.inStopList(token))
				{
					if(!QPostIndex.containsKey(token))
					{
						Map<String, Integer> QueryNum = new TreeMap<String, Integer>();
	 					QueryNum.put(QueryID, 1);
	 					QPostIndex.put(token, QueryNum);
					}
					else
	 				{
						if(QPostIndex.get(token).containsKey(QueryID))
	 					{
							
							int sum1 = QPostIndex.get(token).get(QueryID)+1;
	 						QPostIndex.get(token).put(QueryID, sum1);
	 					}
	 					else
	 					{
	 						QPostIndex.get(token).put(QueryID, 1);
	 					}
	 				}
				}
			}
 		}
		reader.close();
		return QPostIndex;
	}



}
