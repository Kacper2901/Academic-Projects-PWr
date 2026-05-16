public class CanBeSorted {

    public boolean canSortArray(int[] nums) {
        if(nums.length == 1) return true;
        int i = 0;
        int prevGroupMax = Integer.MIN_VALUE;

        while (i < nums.length){
            int currMin = nums[i];
            int currMax = nums[i];
            while(i < nums.length - 1 && Integer.bitCount(nums[i]) == Integer.bitCount(nums[i+1])){
                i++;
                if(currMax < nums[i]) currMax = nums[i];
                if(currMin > nums[i]) currMin = nums[i];
            }
            if(currMin < prevGroupMax) return false;
            prevGroupMax = currMax;
            i++;
        }
        return true;
    }
}
