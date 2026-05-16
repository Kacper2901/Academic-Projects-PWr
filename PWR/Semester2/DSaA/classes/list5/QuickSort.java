import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    boolean testQuickSort(int arrSize, int maxVal){
        Random rand = new Random();
        int[] arr1 = new int[arrSize];
        boolean isArr1Sorted = true;
        for(int i = 0; i < arrSize; i++){
            arr1[i] = rand.nextInt(maxVal + 1);
        }

        quickSort(arr1, 0,arr1.length - 1);

        for(int i = 1; i < arrSize; i++){
            if(arr1[i-1] > arr1[i]) isArr1Sorted = false;
        }

        return isArr1Sorted;
    }

    int selectPivotIdx(int[] arr, int left, int right){
        int size = right - left + 1;
        Random rand = new Random();
        if(size <= 100){
            return rand.nextInt(left, right + 1);
        }

        int p1 = rand.nextInt(left, right + 1);
        int p2 = rand.nextInt(left, right + 1);
        int p3 = rand.nextInt(left, right + 1);

        int val1 = arr[p1];
        int val2 = arr[p2];
        int val3 = arr[p3];

        if((val1 <= val2 && val1 >= val3) || (val1 >= val2 && val1 <= val3)) return p1;
        else if ((val2 <= val1 && val2 >= val3) || (val2 <= val3 && val2 >= val1)) return p2;
        else return p3;
    }

    int partition(int[] arr, int left, int right, int pivotIdx){
        int pivotVal = arr[pivotIdx];
        while(left <= right){
            while(arr[left] < pivotVal) left++;
            while(arr[right] > pivotVal) right--;

            if(left <= right){
                swap(arr, left,right);
                left++;
                right--;
            }
        }
        return right;
    }

    void quickSort(int[] arr, int left, int right){
        if(left >= right ) return;
        int pivotIdx = selectPivotIdx(arr, left,right);

        int partitionIdx = partition(arr, left, right, pivotIdx);


        quickSort(arr, left, partitionIdx);
        quickSort(arr, partitionIdx + 1, right);
    }



    void main(){
        boolean isWorking = true;
        for(int i = 0; i < 100; i++){
            if(!testQuickSort(1_000_000, 500_000)) isWorking = false;
        }
        if(!isWorking) System.out.println("sth is wrong");
        else System.out.println("its working");
    }

}
