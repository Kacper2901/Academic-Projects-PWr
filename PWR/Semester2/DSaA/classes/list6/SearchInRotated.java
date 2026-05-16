import java.util.Arrays;

public class SearchInRotated {
        public int search(int[] nums, int target) {
            if(nums.length == 1){
                if(nums[0] == target) return 0;
                else return -1;
            }
            return searchInSubarray(nums, target, 0, nums.length - 1);
        }

        private int searchInSubarray(int[] nums, int target, int l, int r){
            if(l==r && nums[l] == target) return l;
            else if(l==r) return -1;

            int mid = l + (r-l)/2;
            if(isSubarraySorted(nums,l, mid)){
                if(isInSubarray(nums, target, l, mid)) return searchInSubarray(nums,target,l,mid);
                else return searchInSubarray(nums,target,mid+1, r);
            }
            else {
                if(isInSubarray(nums, target,mid+1, r)) return searchInSubarray(nums,target,mid+1,r);
                else return searchInSubarray(nums,target,l, mid);
            }
        }

        private boolean isSubarraySorted(int[] nums, int l, int r){
            return nums[l] <= nums[r];
        }

        private boolean isInSubarray(int[] nums,int target, int l, int r){
            return target >= nums[l] && target <= nums[r];
        }

    void main(){
        int[] arr = {4,5,6,7,0,1,2};
        System.out.println(Arrays.toString(arr));
        System.out.println(search(arr,0));
    }
}
