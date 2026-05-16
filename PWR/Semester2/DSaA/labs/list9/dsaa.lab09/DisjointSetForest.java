package dsaa.lab09;

import java.util.Arrays;

public class DisjointSetForest implements DisjointSetDataStructure {
	
	private class Element{
		int rank;
		int parent;
	}

	Element []arr;
	
	public DisjointSetForest(int size) {
		arr = new Element[size];
        for(int i = 0; i < arr.length; i++){
            makeSet(i);
        }
	}
	
	@Override
	public void makeSet(int item) {
		arr[item] = new Element();
        arr[item].rank = 0;
        arr[item].parent = item;
	}

	@Override
	public int findSet(int item) {
        if(arr[item].parent == item) return item;
        int itemParent = findSet(arr[item].parent);
        arr[item].parent = itemParent;
		return itemParent;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		int rootA = findSet(itemA);
        int rootB = findSet(itemB);
        int rankA = arr[rootA].rank;
        int rankB = arr[rootB].rank;

        if(rootA == rootB) return false;
        if(rankA == rankB){
            arr[rootA].parent = rootB;
            arr[rootB].rank++;
            return true;
        }

        int greaterRankRoot = (rankA > rankB) ? rootA : rootB;
        int smallerRankRoot = (greaterRankRoot == rootA) ? rootB : rootA;

        arr[smallerRankRoot].parent = greaterRankRoot;


		return true;
	}


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Disjoint sets as forest:\n");



        for(int i = 0; i < arr.length; i++){
            int parent = arr[i].parent;
            sb.append(i).append(" -> ").append(parent).append("\n");
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
