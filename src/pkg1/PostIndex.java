package pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.stanford.nlp.process.Stemmer;

public class PostIndex {
	
	private String fpath ="CS172_PS2\\data\\ap89_collection";
	private ReadFiles rf = new ReadFiles();
	
	public Map<String, Map<String, Integer>> CreateIndex() throws IOException
	{
		//create a post index
		//Word-<Docid-number>
		Map<String, Map<String, Integer>> PostIndex = new TreeMap<String, Map<String, Integer>>();
		BufferedReader reader = new BufferedReader(new FileReader(fpath));
		String read;
		Stemmer s = new Stemmer();
		String docname = null;
		while((read = reader.readLine())!=null)
		{	
			//read = read.replaceAll( "\\p{Punct}", "" ); 
			StringTokenizer st = new StringTokenizer(read," ");			
 			while(st.hasMoreElements()) 
 			{	
 				String token = st.nextToken().toLowerCase();
 				token = s.stem(token);
 				if(token.toUpperCase().equals("<DOCNO>"))
 				{
 					token = st.nextToken();
 					docname = token;
 				}
 				else if(token.toUpperCase().equals("<TEXT>"))
 				{
 					while((read = reader.readLine())!=null&&!read.equals("</TEXT>"))
 					{
 						read = read.replaceAll( "\\p{Punct}", "" ); 
 						StringTokenizer st2 = new StringTokenizer(read," ");
 						while(st2.hasMoreElements())
 						{ 		
 							token = st2.nextToken().toLowerCase();
 							token = s.stem(token);
 							if(!rf.inStopList(token))
 							{	
 								if(!PostIndex.containsKey(token))
 								{
 	 	 						//if(token.equals("sinhales"));
 									Map<String, Integer> DocNum = new TreeMap<String, Integer>();
 									DocNum.put("Sum", 1);
 									DocNum.put(docname, 1);
 									PostIndex.put(token, DocNum);
 								}
 								else
 								{
 									if(PostIndex.get(token).containsKey(docname))
 									{
 	 	 							
 										int sum1 = PostIndex.get(token).get(docname)+1;
 										PostIndex.get(token).put(docname, sum1);
 									}
 									else
 									{
 										int sum2 = PostIndex.get(token).get("Sum")+1;
 										PostIndex.get(token).put("Sum", sum2);
 										PostIndex.get(token).put(docname, 1);
 									}
 								}
 							}
 						}
 					}	
 				}
			}			
		}
		reader.close();
		return PostIndex;
	}
}
