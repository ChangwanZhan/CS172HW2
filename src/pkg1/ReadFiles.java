package pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;



public class ReadFiles {
	
	public boolean inStopList(String s) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("CS172_PS2\\data\\stoplist.txt"));
		String read;
		while((read = reader.readLine())!=null)
		{
			if(s.equals(read)) 
			{
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
		
	}
	
	


}
