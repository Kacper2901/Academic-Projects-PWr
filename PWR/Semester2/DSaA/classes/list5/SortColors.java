import java.util.Arrays;
import java.util.Random;

public class SortColors {

    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void sortColors(int[] nums) {
        int nextZeroIdx = 0;
        int curr = 0;
        int nextTwoIdx = nums.length - 1;


        while(curr <= nextTwoIdx){
            if(nums[curr] == 0){
                swap(nums, nextZeroIdx, curr);
                nextZeroIdx++;
                curr++;
            }
            else if(nums[curr] == 2){
                swap(nums,nextTwoIdx,curr);
                nextTwoIdx--;
            }
            else{
                curr++;
            }
            System.out.println(Arrays.toString(nums));

        }
    }

    public void main(){
        Random rand = new Random();
        int[] arr = new int[10];
        for(int i = 0; i < 10; i++){
            arr[i] = rand.nextInt(3);
        }
        System.out.println(Arrays.toString(arr));

        sortColors(arr);
        System.out.println(Arrays.toString(arr));
    }
}
