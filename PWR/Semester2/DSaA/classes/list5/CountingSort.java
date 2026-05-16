import java.util.Arrays;

public class CountingSort {

    void countingSort(int[] arr, int k){
        k++;
        int n = arr.length;
        int[] countingArr = new int[k];
        for(int i = 0; i < n; i++){
            countingArr[arr[i]]++;
        }
        System.out.println("counting array:" + Arrays.toString(countingArr));
        int m = 0;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < countingArr[i]; j++){
                arr[m] = i;
                m++;
            }
        }
    }

    void main(){
        int[] arr = {0,2,1,0,4,4,2,1,1,1};
        System.out.println(Arrays.toString(arr));;
        countingSort(arr, 4);
        System.out.println(Arrays.toString(arr));
    }
}
