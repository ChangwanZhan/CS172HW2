package pkg1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test {
	public static void main(String[] args) throws IOException
	{
		DocIndex d = new DocIndex();
		Map<String, Integer> DocIn = d.CreateIndex();
		int DocSum = DocIn.size();
		
		PostIndex p = new PostIndex();
		Map<String, Map<String, Integer>> PostIn = p.CreateIndex();
		
		QueryIndex q = new QueryIndex();
		Map<String, Integer> QueryIn = q.CreateIndex();
		
		QueryPostIndex qp = new QueryPostIndex();
		Map<String, Map<String, Integer>> QPostIn = qp.CreateIndex();
		
		DocLength l = new DocLength();
		Map<String, Double> DocIDF = l.CreateIDFIndex(DocSum, PostIn);
		Map<String, Map<String, Double>> DocTF = l.CreateTFIndex(DocIn, PostIn);
		Map<String, Map<String, Double>> DocTFIDF = l.CreateTFIDFIndex(DocIDF, DocTF);
		Map<String, Double>DocLength =l.CreateIndex(DocTFIDF);
		
		Map<String, Map<String, Double>> QueryTF = l.CreateTFIndex(QueryIn, QPostIn);
		Map<String, Map<String, Double>> QueryTFIDF = l.CreateTFIDFIndex(DocIDF, QueryTF);
		Map<String, Double>QueryLength =l.CreateIndex(QueryTFIDF);
		
				
		CosineRank c = new CosineRank();
		Map<String, Map<String, Double>> CosSim = c.CosinSimilarity(QueryTFIDF, DocTFIDF, DocLength, QueryLength);
		Map<String, Map<String, Integer>> RankCos = c.SortCosine(CosSim);
		
		
		PrintWriter writer = new PrintWriter("CS172_PS2\\data\\results_file.txt");
		
		
		//<query-number> Q0 <docno> <rank> <score> Exp
		
		for (Entry<String, Map<String, Double>> entry: CosSim.entrySet())
		{
			
			String QueryNumber = entry.getKey();
			String Q0 = "Q0";
			for (Entry<String, Double> entry2: entry.getValue().entrySet())
			{
				int docnum = 0;
				if(entry2.getValue()!=0&&docnum<100)
				{
					docnum++;
					String Docno = entry2.getKey();
					String Rank = RankCos.get(QueryNumber).get(Docno).toString();
					String Score = entry2.getValue().toString();
					String Exp = "Exp";
					String outputS = QueryNumber+" "+Q0+" "+Docno+" "+Rank+" "+Score+" "+Exp;
					//System.out.println(outputS);
					writer.println(outputS);
				}				
			}
		}
		
		writer.close();
		System.out.println("Finish!");
	}
}
