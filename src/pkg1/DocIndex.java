package pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.stanford.nlp.process.Stemmer;

public class DocIndex {
	
	private String fpath ="CS172_PS2\\data\\ap89_collection";
	private ReadFiles rf = new ReadFiles();
	
	public Map<String, Integer> CreateIndex() throws IOException
	{
		//Create a DocIndex
		Map<String, Integer> DocIndex = new TreeMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new FileReader(fpath));
		String read;
		String docname = null;
		Stemmer s = new Stemmer();
		while((read = reader.readLine())!=null)
		{	
			
			StringTokenizer st = new StringTokenizer(read," ");
 			while(st.hasMoreElements()) 
 			{	
 				String token = st.nextToken().toLowerCase();
 				if(token.toUpperCase().equals("<DOCNO>"))
 				{
 					token = st.nextToken();
 					docname = token;
 					//token = st.nextToken();
 				}
 				
 				else if(token.toUpperCase().equals("<TEXT>"))
 				{ 					
 					int count=0;
 					while((read = reader.readLine())!=null&&!read.equals("</TEXT>"))
 					{
 						read = read.replaceAll( "\\p{Punct}", "" ); 
 						StringTokenizer st2 = new StringTokenizer(read," ");
 						if(st2.hasMoreElements())
 						{
 							token = st2.nextToken().toLowerCase();
 	 	 					token = s.stem(token);
 	 	 					if(!rf.inStopList(token)) count++;
 	 	 					while(st2.hasMoreElements())
 	 	 					{ 		
 	 	 						
 	 	 						token = st2.nextToken().toLowerCase();
 	 	 						token = s.stem(token);
 	 	 						if(!rf.inStopList(token)) count++;
 	 	 					}
 						}
 	 					
 	 					
 					}
 					DocIndex.put(docname, count);
 				}		
			}			
		}
		reader.close();		
		return DocIndex;	
	}
}
