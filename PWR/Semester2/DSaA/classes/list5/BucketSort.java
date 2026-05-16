import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BucketSort {

    String getSizeOfBuckets(ArrayList<Integer>[] buckets){
        StringBuilder sb = new StringBuilder("[");
        for(ArrayList<Integer> b: buckets){
            sb.append(b.size()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    void connectBuckets(ArrayList<Integer>[] buckets, int[] arr){
        int j = 0;
        for(int i = 0; i < buckets.length; i++){
            for(Integer e: buckets[i]){
                arr[j] = e;
                j++;
            }
        }
    }

    void insertSort(ArrayList<Integer> list){
        for (int i = 1; i < list.size(); i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
    void bucketSort(int[] arr){
        int n = arr.length;
        int k = (int)Math.sqrt(n) + 1;
        ArrayList<Integer>[] buckets = new ArrayList[k];
        for(int i = 0; i < k; i++){
            buckets[i] = new ArrayList<>();
        }

        int max = arr[0];
        int min = arr[0];

        for(int i = 1; i < n; i++){
            if(arr[i] > max) max = arr[i];
            if(arr[i] < min) min = arr[i];
        }
        if (max == min) return;
        for (int i = 0; i < n; i++){
            int num = arr[i];

            int bucketIdx = findBucket(num,max,min,k);
            buckets[bucketIdx].add(num);
        }

        System.out.println("size of buckets: " +getSizeOfBuckets(buckets));

        for(int i = 0; i < k; i++){
            insertSort(buckets[i]);
        }

        connectBuckets(buckets, arr);


    }

    int findBucket(int num, int max, int min, int k){
        return (int) ((double) (num - min) / (max - min) * (k - 1));    }

    void main(){
        Random rand = new Random();
        int n = 1000;
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = rand.nextInt(10000);
        }
        System.out.println(Arrays.toString(arr));
        bucketSort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
