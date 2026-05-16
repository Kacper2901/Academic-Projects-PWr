import java.util.Arrays;

public class SquaresOfSortedArray {
    public int[] sortedSquares(int[] nums) {
        int lastNegativeNumIdx = -1;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] < 0) lastNegativeNumIdx++;
            else break;
        }

        for(int i = 0; i < nums.length; i++){
            nums[i] = nums[i]*nums[i];
        }
        if(nums.length == 1) return nums;

        int i = lastNegativeNumIdx + 1;
        int j = lastNegativeNumIdx;
        int currIdx = 0;
        int[] res = new int[nums.length];
        while(j >= 0){
            if(i > nums.length - 1 || nums[j] <= nums[i] ){
                res[currIdx] = nums[j];
                currIdx++;
                j--;
            } else {
                res[currIdx] = nums[i];
                currIdx++;
                i++;
            }
        }
        while(i < nums.length){
            res[i] = nums[i];
            i++;
        }
        while(j >= 0){
            res[currIdx] = nums[j];
            j--;
        }
        return res;
    }

    void main(){
        int[] arr = {-5,-3,-2,-1};
        arr = sortedSquares(arr);
        System.out.println(Arrays.toString(arr));
    }
}
