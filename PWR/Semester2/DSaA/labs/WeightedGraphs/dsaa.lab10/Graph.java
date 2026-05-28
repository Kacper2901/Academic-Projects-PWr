package dsaa.lab10;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import java.util.SortedMap;

public class Graph {
	int arr[][];
	HashMap<String,Integer> name2Int;
    Document[] arrDoc;
    int size;

	// The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String,Document> internet){
		int size=internet.size();
        this.size = size;
		arr=new int[size][size];
        arrDoc = new Document[size];
        name2Int = new HashMap<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    arr[i][j] = 0;
                } else {
                    arr[i][j] = -1;
                }
            }
        }
        int i = 0;

        for(Document doc: internet.values()){
            name2Int.put(doc.name, i);
            arrDoc[i] = doc;
            i++;
        }
        for(Document doc: internet.values()){
            for(Link l: doc.link.values()){
                String linkName = l.ref;
                int weight = l.weight;
                int docInt = name2Int.get(doc.name);
                int targetInt = name2Int.get(linkName);
                arr[docInt][targetInt] = weight;
            }
        }
	}
	
	public String bfs(String start) {
        if(!name2Int.containsKey(start)) return null;

        LinkedList<Document> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        int currDocInt = name2Int.get(start);
        Document currDoc = arrDoc[currDocInt];
        queue.add(currDoc);

        boolean[] isVisited = new boolean[size];
        isVisited[currDocInt] = true;

        while(!queue.isEmpty()){
            currDoc = queue.poll();
            currDocInt = name2Int.get(currDoc.name);
            for(int i = 0; i < size; i++){
                if(arr[currDocInt][i] > 0 && !isVisited[i]){
                    Document nextDoc = arrDoc[i];
                    queue.add(nextDoc);
                    isVisited[i] = true;
                }
            }
            sb.append(currDoc.name).append(", ");
        }
        sb.setLength(sb.length()-2);
        return sb.toString();
	}
	
	public String dfs(String start) {
        if(!name2Int.containsKey(start)) return null;

        LinkedList<Document> stack = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        int currDocInt = name2Int.get(start);
        Document currDoc = arrDoc[currDocInt];
        stack.push(currDoc);

        boolean[] isVisited = new boolean[size];

        while(!stack.isEmpty()){
            currDoc = stack.pop();
            currDocInt = name2Int.get(currDoc.name);

            if(isVisited[currDocInt]) continue;
            sb.append(currDoc.name).append(", ");
            isVisited[currDocInt] = true;

            for(int i = size - 1; i >= 0; i--){
                if(arr[currDocInt][i] > 0 && !isVisited[i]) {
                    Document nextDoc = arrDoc[i];
                    stack.push(nextDoc);
                }
            }
        }
        sb.setLength(sb.length()-2);
        return sb.toString();
	}

	public int connectedComponents() {
		DisjointSetForest forest = new DisjointSetForest(size);
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(arr[i][j] > 0) forest.union(i, j);
            }
        }
		return forest.countSets();
	}
}
