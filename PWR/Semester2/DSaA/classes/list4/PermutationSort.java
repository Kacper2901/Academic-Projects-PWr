import static aisd.sort.ArrayOperations.*;

public class PermutationSort<E> {
    public boolean nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return false;
        int i = nums.length - 2;

        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if(i < 0) return false;
        int j = nums.length - 1;

        while (nums[j] <= nums[i]) {
            j--;
        }

        swap(nums, i, j);
        reverse(nums, i + 1, nums.length - 1);
        return true;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start++, end--);
        }
    }

    public void permutationSort(int[] arr){
        while(true){
            if(!nextPermutation(arr)) break;
        }
    }

    void main(){
        int[] arr = {1,4,2,3,5,6,34,32,65,23,1,5,2};//max12
        long startTime = System.nanoTime();
        permutationSort(arr);
        System.out.println((double)(System.nanoTime() - startTime)/1000000000.0);
        printArray(arr);
    }
}
