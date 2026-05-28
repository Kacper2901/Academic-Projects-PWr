package dsaa.lab11;

import javax.print.Doc;
import java.util.*;
import java.util.Map.Entry;

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

	
	public String DijkstraSSSP(String startVertexStr) {
        if(!name2Int.containsKey(startVertexStr)) return null;
        int[] dist = new int[size];
        Document[] shortestPathParents = new Document[size];
        PriorityQueue<Document> priorityQueue = new PriorityQueue<>((doc1,doc2) ->
                Integer.compare(dist[name2Int.get(doc1.name)], dist[name2Int.get(doc2.name)]));

        int startingVertexIdx = name2Int.get(startVertexStr);
        for(int i = 0; i < size; i++){
            if(i == startingVertexIdx) dist[i] = 0;
            else dist[i] = Integer.MAX_VALUE;
        }

        priorityQueue.add(arrDoc[startingVertexIdx]);

        while(!priorityQueue.isEmpty()){
            Document currDoc = priorityQueue.poll();
            int currDocIdx = name2Int.get(currDoc.name);

            for(int i = 0; i < size; i++){
                int weight = arr[currDocIdx][i];
                if(weight > 0 && dist[currDocIdx] != Integer.MAX_VALUE){
                    if(weight + dist[currDocIdx] < dist[i]){
                        dist[i] = weight + dist[currDocIdx];
                        shortestPathParents[i] = currDoc;
                        priorityQueue.add(arrDoc[i]);
                    }
                }
            }
        }
        return buildAnswer(shortestPathParents, dist,startingVertexIdx);
	}

    String buildAnswer(Document[] shortestPathParents, int[] dist, int startDocIdx){
        Document startDoc = arrDoc[startDocIdx];
        StringBuilder sb = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        for(int i = 0; i < size; i++){
            Document currDoc = arrDoc[i];
            if(dist[i] == Integer.MAX_VALUE){
                sb.append("no path to ").append(currDoc.name).append("\n");
                answer.append(sb);
                sb.setLength(0);
                continue;
            }
            while(!currDoc.name.equals(startDoc.name)){
                sb.append(currDoc.name).append(">-");
                int currDocIdx = name2Int.get(currDoc.name);
                currDoc = shortestPathParents[currDocIdx];
            }
            int currDocIdx = name2Int.get(currDoc.name);
            sb.append(currDoc.name);
            sb.reverse();
            answer.append(sb);
            sb.setLength(0);
            answer.append("=").append(dist[i]).append("\n");
        }
        return answer.toString();
    }
}
