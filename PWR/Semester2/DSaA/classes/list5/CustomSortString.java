public class CustomSortString {

    int getIdxBasedOnAscii(int ascii){
        return ascii - 97;
    }

    int getAsciiBasedOnIdx(int idx){
        return idx+97;
    }
    public String customSortString(String order, String s) {
        StringBuilder sb = new StringBuilder(s.length());
        //ascii - 97
        int[] charactersCount = new int[26];

        int currCharIdx;
        for(int i = 0; i < s.length(); i++){
            currCharIdx = getIdxBasedOnAscii(s.charAt(i));
            charactersCount[currCharIdx]++;
        }

        for(int i = 0; i < order.length(); i++){
            char currChar = order.charAt(i);
            currCharIdx = getIdxBasedOnAscii(currChar);
            for(int j = 0; j < charactersCount[currCharIdx]; j++){
                sb.append((char)getAsciiBasedOnIdx(currCharIdx));
            }
            charactersCount[currCharIdx] = 0;
        }

        for(int i = 0; i < 26; i++){
            for(int j = 0; j < charactersCount[i]; j++){
                sb.append((char) getAsciiBasedOnIdx(i));
            }
        }
        return sb.toString();
    }

    void main(){
        System.out.println(customSortString("bcafg", "abcd"));
    }
}
