import java.util.Arrays;
import java.util.Random;

public class HeapSort {


    void maxHeapify(int[] arr, int parentIdx, int n){
        int leftChildIdx = 2*parentIdx + 1;
        int rightChildIdx = 2*parentIdx + 2;
        int maxValIdx = parentIdx;

        //find the greatest of parent and both childs
        if(leftChildIdx < n && arr[maxValIdx] < arr[leftChildIdx]){
           maxValIdx = leftChildIdx;
        }

        if(rightChildIdx < n && arr[maxValIdx] < arr[rightChildIdx]){
            maxValIdx = rightChildIdx;
        }

        //swap with the parent and heapify affected subtree
        if(maxValIdx != parentIdx){
            int temp = arr[parentIdx];
            arr[parentIdx] = arr[maxValIdx];
            arr[maxValIdx] = temp;
            maxHeapify(arr, maxValIdx, n);
        }
    }

    void heapSort(int arr[]){
        int n = arr.length;
        //for parent at index x, left child index is 2*x + 1, right child index is 2*x + 2
        int lastParentIdx = n/2 - 1;

        //build maxHeap
        for(int i = lastParentIdx; i >= 0; i--){
            maxHeapify(arr, i, n);
        }

        //push max at the end of the tree
        for(int i = n - 1; i > 0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            maxHeapify(arr, 0, i);
            System.out.println(Arrays.toString(arr));

        }


    }

    void main(){
        Random rand = new Random();
        int k = 20;
        int[] arr = new int[k];
        for(int i = 0; i < k; i ++){
            arr[i] = rand.nextInt(100);
        }

        System.out.println(Arrays.toString(arr));
        System.out.println();
        heapSort(arr);
        System.out.println();
        System.out.println(Arrays.toString(arr));
    }
}
