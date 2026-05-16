import java.util.Arrays;

public class MinimumInRotated {
    public int findMin(int[] nums) {
        if (nums.length == 1 || nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        return nums[findMinInSubarray(nums,0, nums.length-1)];
    }

    private boolean isSubarraySorted(int[] nums, int l, int r){
        return nums[l] < nums[r];
    }

    private int findMinInSubarray(int[] nums, int l, int r){
        if(l == r) return l;
        int mid = l + (r-l)/2;
        if(isSubarraySorted(nums,mid,r)) return findMinInSubarray(nums,l,mid);
        else return findMinInSubarray(nums,mid+1,r);
    }

    void main(){
        int[] arr = {3,4,5,1};
        System.out.println(Arrays.toString(arr));
        System.out.println(findMin(arr));
    }
}
