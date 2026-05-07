package dsaa.lab06;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }
    public void load(Scanner scan) {
        //TODO
        while (scan.hasNext()) {
            String line = scan.next();
            if (line.equals("eod")) break;
            if (line.toLowerCase().startsWith("link=")) {
                Link newLink = createLink(line.substring(5));
                if (newLink != null) {
                    link.add(newLink);
                }
            }
        }
    }


    public static boolean isCorrectId(String id) {
        //TODO
        if (id == null || id.length() == 0) return false;
        if (!Character.isLetter(id.charAt(0))) {
            return false;
        }

        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_'){
                return false;
            }
        }
        return true;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        //TODO

        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');
        String id;
        int weight = 1;

        if (openBracket != -1 && closeBracket != -1 && closeBracket > openBracket) {
            id = link.substring(0, openBracket).toLowerCase();
            if(closeBracket != link.length() - 1) return null;
            try {
                weight = Integer.parseInt(link.substring(openBracket + 1, closeBracket));
                if(weight <=0 ) return null;
            } catch (NumberFormatException e) {
                return null;
            }
        } else if(openBracket == -1 && closeBracket == -1){
            id = link.toLowerCase();
        }
        else return null;

        if (isCorrectId(id) ) {
            return new Link(id, weight);
        }
        return null;
    }

    @Override
    public String toString() {
        String retStr="Document: "+name;
        Iterator<Link> it = link.iterator();
        int i = 0;


        while (i < link.size){
            if(i % 10 == 0){
                retStr += "\n";
            }
            else {
                retStr += " ";
            }
            Link nextLink = it.next();
            retStr += nextLink.toString();
            i++;
        }
        //TODO
        return retStr;
    }

    public String toStringReverse() {
        String retStr="Document: "+name;
        ListIterator<Link> iter=link.listIterator();
        int i = 0;
        while(i < link.size) {
            i++;
            iter.next();
        }
        //TODO

        i = 0;
        while(i < link.size){
            if(i % 10 == 0){
                retStr += "\n";
            }
            else{
                retStr += " ";
            }
            Link nextLink = iter.previous();
            retStr += nextLink.toString();
            i++;
        }
        return retStr;
    }
    public int[] getWeights() {
        int[] weights = new int[link.size];
        int i = 0;
        Iterator<Link> it = link.iterator();
        while(it.hasNext()){
            weights[i] = it.next().weight;
            i++;
        }
        return weights;
    }

    public static void showArray(int[] arr) {
        if(arr.length == 0) return;
        for(int i = 0; i < arr.length-1; i++){
            System.out.print(arr[i] + " ");
        }
        int lastIdx = arr.length - 1;

        System.out.println(arr[lastIdx]);
    }

    void swap(int[] arr, int idx1, int idx2){
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    public void bubbleSort(int[] arr) {
        showArray(arr);
        for(int i = 0; i < arr.length - 1; i++){
            for(int j = arr.length - 1; j > i; j--){
                if(arr[j] < arr[j-1]) swap(arr, j, j-1);
            }
            showArray(arr);
        }
    }

    public void insertSort(int[] arr) {
        showArray(arr);
        for(int i = arr.length - 2; i >= 0; i--){
            int j = i;
            while(j + 1 < arr.length && arr[j] > arr[j+1]){
                swap(arr, j, j+1);
                j++;
            }
            showArray(arr);
        }
        //TODO
    }
    public void selectSort(int[] arr) {
        showArray(arr);
        for(int i = arr.length - 1; i > 0; i--){
            int maxValIdx = i;
            for(int j = i-1; j >= 0; j--){
                if(arr[maxValIdx] < arr[j]) maxValIdx = j;
            }
            swap(arr,maxValIdx, i);
            showArray(arr);
        }
    }

    public void merge(int leftStart, int mid, int rightEnd, int[] arr, int[] temp){
        for(int i = leftStart; i <= rightEnd; i++){
            temp[i] = arr[i];
        }

        int i = leftStart;
        int j = mid + 1;
        int curr = leftStart;

        while(i <= mid && j<=rightEnd){
            if(temp[i] <= temp[j]){
                arr[curr] = temp[i];
                curr++;
                i++;
            }
            else{
                arr[curr] = temp[j];
                curr++;
                j++;
            }
        }

        while(i <= mid){
            arr[curr] = temp[i];
            curr++;
            i++;
        }

        while(j <= rightEnd){
            arr[curr] = temp[j];
            curr++;
            j++;
        }
    }


	public void iterativeMergeSort(int[] arr) {
		showArray(arr);
        int arrLength = arr.length;
        int[] temp = new int[arrLength];
        for(int currSize = 1; currSize <= arrLength-1; currSize *= 2){
            for(int leftStart = 0; leftStart < arrLength - 1; leftStart+=currSize*2) {
                int mid = Math.min(leftStart + currSize - 1, arrLength -1);
                int rightEnd = Math.min(mid + currSize,arrLength - 1);

                merge(leftStart, mid, rightEnd, arr, temp);
            }
            showArray(arr);
        }
	}

    private int getDigitByPlace(int exp, int number){
        return (number / exp) %10;
    }

     private int findMax(int[] arr) {
        if(arr.length == 0) throw new NoSuchElementException();
        int currMax = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > currMax) currMax = arr[i];
        }
        return currMax;
    }


	public void radixSort(int[] arr) {
		showArray(arr);
        if(arr.length == 0) return;
//        int maxValDigits = Integer.toString(findMax(arr)).length();
        for(int i = 1; i <=100; i*=10){
            sortByNthDigit(i,arr);
            showArray(arr);
        }
	}

    private void sortByNthDigit(int exp, int[] arr){
        int arrayLength = arr.length;
        int[] output = new int[arrayLength];
        int[] count = new int[10];

        //count how many number has digit 0-9 in curr place (going from last to first place)
        for(int i = 0; i < arrayLength; i++){
            int digit = getDigitByPlace(exp,arr[i]);
            count[digit]++;
        }

        for(int i = 1; i < 10; i++){
            count[i] += count[i-1]; //f.e. place of digit 1 will be after all numbers with digit 0
        }

        for(int i = arrayLength - 1; i>=0; i--){
            int digit = getDigitByPlace(exp, arr[i]);
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        System.arraycopy(output,0,arr,0,arrayLength);
    }

}
