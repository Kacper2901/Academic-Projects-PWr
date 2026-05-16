import java.util.Arrays;
import java.util.Random;

public class ShellSort {
    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    void shellSort(int[] arr){
        int gap = arr.length/2;

        while(gap != 0){
            for(int i = 0; i < arr.length - gap; i++){
                int j = i;
                while(j >= 0 && arr[j + gap] < arr[j]){
                    swap(arr, j, j + gap);
                    j -= gap;
                }
                System.out.println(Arrays.toString(arr));

            }
            gap /= 2;
        }
        System.out.println(Arrays.toString(arr));
    }

    void main(){
        int k = 14;
        Random rand = new Random();
        int[] arr = new int[k];
        for(int i = 0; i < k; i++){
            arr[i] = rand.nextInt(3*k);
        }
        shellSort(arr);
    }
}
