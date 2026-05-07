package dsaa.lab07;

import java.util.LinkedList;

public class HashTable{
	LinkedList arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;
    private int currSize;
	private final double maxLoadFactor;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	public HashTable(int initCapacity, double maxLF) {
        this.size = initCapacity;
		this.maxLoadFactor=maxLF;
        this.currSize = 0;
        arr = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new LinkedList();
        }
	}

	public boolean add(Object elem) {
        int h = elem.hashCode();
        if(get(elem) == null){
            arr[h%arr.length].add(elem);
            currSize++;

            if((double)currSize/arr.length > maxLoadFactor){
                doubleArray();
            }
		    return true;
        }
        return false;
	}

	
	private void doubleArray() {

        HashTable targetHashTable = new HashTable(this.size*2, maxLoadFactor);
        for(int i = 0; i < arr.length; i++){
            for(Object elem: arr[i]){
                targetHashTable.add(elem);
            }
        }
        this.arr = targetHashTable.arr;
        this.size*=2;

	}


	@Override
	public String toString() {
		//TODO
		// use	IWithName x=(IWithName)elem;
        String retString = "";
        for(int i = 0; i < arr.length; i++) {
            retString += i + ": ";
            if (!arr[i].isEmpty()) {
                for (int j = 0; j < arr[i].size(); j++) {
                    IWithName doc = (IWithName) arr[i].get(j);
                    retString += doc.getName();
                    if (j < arr[i].size() - 1) {
                        retString += ", ";
                    }
                }
            }
            retString += "\n";
        }

		return retString;
	}

	public Object get(Object toFind) {
		int key = toFind.hashCode()%arr.length;
        for(Object elem: arr[key]){
            if(toFind.equals(elem)){
                return elem;
            }
        }
		return null;
	}


}

