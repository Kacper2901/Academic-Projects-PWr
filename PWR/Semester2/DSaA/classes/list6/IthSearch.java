import java.util.Arrays;
import java.util.Random;

public class IthSearch {
    Random rand = new Random();

    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    int partition(int[] arr, int l, int r){
        int pivotIdx = l + rand.nextInt(r - l + 1);
        swap(arr,pivotIdx,r);
        pivotIdx = r;
        r--;


        while(l <= r){
            while(l <= r && arr[l] < arr[pivotIdx]) l++;
            while(l<=r && arr[r] > arr[pivotIdx]) r--;

            if(l<=r){
                swap(arr, l, r);
                l++;
                r--;
            }
        }

        swap(arr, l, pivotIdx);
        return l;
    }
    
    int findKthElement(int[] arr, int k){
        return quickSearch(arr, 0, arr.length - 1, k);
    }
    
    int quickSearch(int[] arr, int l, int r, int k){
        if (l >= r) return arr[l];
        int partitionIdx = partition(arr, l, r);
        if(partitionIdx == k) return arr[partitionIdx];
        if(partitionIdx < k) return quickSearch(arr, partitionIdx + 1, r, k);
        else return quickSearch(arr, l, partitionIdx - 1, k);

    }

    void main(String[] args){
        int[] arr = {3,2,5,1,2,4,3,10};
        int k = 5;
        System.out.println(Arrays.toString(arr));
        int kthElem = findKthElement(arr, k);
        System.out.println(kthElem);
    }
}
