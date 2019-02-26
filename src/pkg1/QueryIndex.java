package pkg1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.stanford.nlp.process.Stemmer;

public class QueryIndex {
	private String fpath ="CS172_PS2\\data\\query_list.txt";
	private ReadFiles rf = new ReadFiles();
	
	public Map<String, Integer> CreateIndex() throws IOException
	{
		Map<String, Integer> QueryIndex = new TreeMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new FileReader(fpath));
		String read;
		Stemmer s = new Stemmer();
		while((read = reader.readLine())!=null)
		{	
			read = read.replaceAll( "\\p{Punct}", "" ); 
			StringTokenizer st = new StringTokenizer(read," ");
			String token = st.nextToken();
			String QueryID = token;
			int count=0;
			while(st.hasMoreElements())
			{				
				token = st.nextToken().toLowerCase();
				token = s.stem(token);
				if(!rf.inStopList(token)) count++;
 				QueryIndex.put(QueryID, count); 						
			}		
		}
		reader.close();
		return QueryIndex;
	}
}
