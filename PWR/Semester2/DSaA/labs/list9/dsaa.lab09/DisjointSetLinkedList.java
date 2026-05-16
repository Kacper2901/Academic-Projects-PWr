package dsaa.lab09;

import java.util.Arrays;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

	private class Element{
		int representant;
		int next;
		int length;
		int last;
	}
	
	private static final int NULL=-1;
	
	Element arr[];
	
	public DisjointSetLinkedList(int size) {
		arr = new Element[size];
        for(int i = 0; i < size; i++){
            makeSet(i);
        }
	}
	
	@Override
	public void makeSet(int item) {
		arr[item] = new Element();
        arr[item].representant = item;
        arr[item].length = 1;
        arr[item].last = item;
        arr[item].next = -1;
	}

	@Override
	public int findSet(int item) {
		return arr[item].representant;
	}

	@Override
	public boolean union(int itemA, int itemB) {
        int representantA = findSet(itemA);
        int representantB = findSet(itemB);
        if(representantA == representantB) return false;
        int curr;
        int smallerSetRepresentant = (arr[representantA].length < arr[representantB].length) ? representantA : representantB;
        int greaterSetRepresentant = (smallerSetRepresentant == representantA) ? representantB : representantA;
        int greaterSetLast = arr[greaterSetRepresentant].last;

        curr = smallerSetRepresentant;
        arr[greaterSetRepresentant].length += arr[smallerSetRepresentant].length;

        arr[greaterSetLast].next = smallerSetRepresentant;
        arr[greaterSetRepresentant].last = arr[smallerSetRepresentant].last;

        while(curr != -1){
            arr[curr].representant = greaterSetRepresentant;
            curr = arr[curr].next;
        }

		return true;
	}


    private String setToString(int representant){
        StringBuilder sb = new StringBuilder();
        int curr = representant;
        while (curr != -1){
            sb.append(curr).append(", ");
            curr = arr[curr].next;
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder("Disjoint sets as linked list:\n");
        int[] representants = new int[arr.length];

        for(int i = 0; i < arr.length; i++){
            int representant = findSet(i);
            if(representant == i) representants[i] = representant;
            else representants[i] = -1;
        }
        Arrays.sort(representants);

        for(int i = 0; i < representants.length; i++){
            if(representants[i] != -1) {
                sb.append(setToString(representants[i])).append("\n");
            }
        }
        sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
