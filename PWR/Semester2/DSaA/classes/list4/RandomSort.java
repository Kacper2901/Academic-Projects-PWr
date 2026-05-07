import java.util.Random;

import static aisd.sort.ArrayOperations.*;
import java.util.Random;

public class RandomSort {

    public void shuffleArr(int[] arr){
        Random rand = new Random();

        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    public boolean isSorted(int[] arr){
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i] > arr[i+1]) return false;
        }
        return true;
    }

    public void randomSort(int[] arr){
        while(!isSorted(arr)){
            shuffleArr(arr);
        }
    }



    void main(){
        int[] arr = {6,5,4,3,2,1,7,8,9,10,11,12};
        randomSort(arr);
        printArray(arr);
    }
}
