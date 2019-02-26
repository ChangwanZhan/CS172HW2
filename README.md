# IRHW2
CS172HW2
Design of my program:
I finished this homwork in Java. In my program, there includes 8 classes--ReadFiles, PostIndex, DocIndex, DocLength,  QueryPostIndex, QueryIndex, CosineRank, and Test.

Class: ReadFiles
In this class, it contains a function: boolean inStopList(String s), which can judge whethe the input String is included in the stoplist. If it returns true, we can know that the input String s is included in the stoplist.

Class: PostIndex
In this class, it contains a function: Map<String, Map<String, Integer>> CreateIndex(). It can process ap89_collection and then create a document index for it. When it meets "<DOCNO>", store the current Doc ID. Then, when it meets"<TEXT>", it starts reading the contents of the file. For each word, punctuations are removed and it first convert it to lower case, then stemming it. Then, judge whether the word is in the stop list and has appeared before. If it did not appear before, then we create a new index inside the TreeMap; if it has appeared before, we find the key in the map then add one to its current value. Thus, a post index for documents can be created.

Class: DocIndex
In this class, it contains a function: Map<String, Integer> CreateIndex(), which can create a document index for current collection. When it meets "<DOCNO>", store the current Doc ID. Then, when it meets"<TEXT>", it starts reading the contents of the file. For each word,  punctuations are removed and it first convert it to lower case, stemming it, and then judge it whether is included in the stop list. If it is not in the stop list, the counting number of the current document add 1. Thus, a document index for the current collection can be created.

Class: DocLength
In this class, it contains four functions.
1. The first functio is Map<String, Double> CreateIDFIndex(int DocSum, Map<String, Map<String, Integer>> PostIndex). This function can create a map which stored the idf value for each word. The PostIndex created before is used. IDF = (log_2 (Sum of Documents/ PostIndex.get(Current Word).get("Sum"))) + 1.
2. The second function is Map<String, Map<String, Double>> CreateTFIndex(Map<String, Integer> DocIndex, Map<String, Map<String, Integer>> PostIndex). It creates a map which stores the tf value for each words from different documents. Both post index and document index created before are applied to calculate TF value. TF = PostIndex.get(Current Word).get(Current DocID) /DocIndex.get(Current DocID). 
3. The third function is Map<String, Map<String, Double>> CreateTFIDFIndex(Map<String, Double> IDFIndex, Map<String, Map<String, Double>> TFIndex). The map it created can store the value of tfidf for each word in different documents. tf-idf = tf* idf.
4. The fourth function is Map<String, Double> CreateIndex( Map<String, Map<String, Double>> TFIDFIndex). It can create a map which stores the length information of each document. The length is calculated by(sum (tfidf value of each word in the document)^2)^(1/2).

Class: QueryPostIndex
In this class, it contains a function: Map<String, Map<String, Integer>> CreateIndex(). It can create a post index for queries. It stores the first string as the current Query ID. Then, it starts reading the contents of the file. For each word,  punctuations are removed and it first convert it to lower case, then stemming it. Then, judge whether the word is in the stop list and has appeared before. If it did not appear before, then we create a new index inside the TreeMap; if it has appeared before, we find the key in the map then add one to its current value. Thus, a post index for documents can be created.

Class: QueryIndex
In this class, it contains a function: Map<String, Integer> CreateIndex(), which can create a query index for current collection. It stores the first string as the current Query ID. Then, it starts reading the contents of the file. For each word,  punctuations are removed and it first convert it to lower case, stemming it, and then judge it whether is included in the stop list. If it is not in the stop list, the counting number of the current document add 1. Thus, a document index for the current collection can be created.

Class: CosineRank
In this class, it contains two functions:
1. The first function is: Map<String, Map<String, Double>> CosinSimilarity(Map<String, Map<String, Double>> QueryTFIDF, Map<String, Map<String, Double>> DocTFIDF, Map<String, Double> DocLength, Map<String, Double> QueryLength). It calculates the cosine similarity of queries and documents and stores them.
2. The second function is: Map<String, Map<String, Integer>> SortCosine(Map<String, Map<String, Double>> CosSim). It ranks the documents by their cosine similarity with the query.

Class: Test
First, create a document index and a post index for the collection. Then, create a query index and a post indext for queries. After that, calculate and store tf-idf value for each word in different documents. Meanwhile, calculate and store tf-idf value for each word in different queries. Then, calculate cosine similarity between documents and queries, and rank them to find top 100 relavant documents. Output the result into file: "CS172_PS2\\data\\results_file.txt".

How to run the program:
Just click "Run" button in Eclipse, when it outputs "Finish!" in the console, you can see the results in the file: "CS172_PS2\\data\\results_file.txt"

















