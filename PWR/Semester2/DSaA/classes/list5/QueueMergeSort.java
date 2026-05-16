import java.util.*;

public class QueueMergeSort {

    LinkedList<Integer> linkedListMergeSort(LinkedList<Integer> list){
        //initialize a queue and treat every single element from given list as sorted sublist
        LinkedList<LinkedList<Integer>> sortedSublistsQueue = new LinkedList<>();

        for(Integer e: list){
            LinkedList<Integer> l = new LinkedList<>();
            l.add(e);
            sortedSublistsQueue.add(l);
        }

        //when queue size == 1 then size of sorted sublist == list.size and we have nothing to merge
        while(sortedSublistsQueue.size() > 1){
            LinkedList<Integer> l1 = sortedSublistsQueue.poll();
            LinkedList<Integer> l2 = sortedSublistsQueue.poll();
            sortedSublistsQueue.add(merge(l1, l2)); //take two adjacent lists and merge them into bigger sorted list
        }
        return sortedSublistsQueue.poll();
    }

    LinkedList<Integer> merge(LinkedList<Integer> l1, LinkedList<Integer> l2){
        LinkedList<Integer> merged = new LinkedList<>();
        //while both lists are NOT empty choose smaller value from them
        while(!l1.isEmpty() && !l2.isEmpty()){
            if(l1.getFirst() <= l2.getFirst()){
                merged.add(l1.poll());
            }
            else{
                merged.add(l2.poll());
            }
        }
        //add remaining values
        while (!l1.isEmpty()){
            merged.add((l1.poll()));
        }
        while (!l2.isEmpty()){
            merged.add(l2.poll());
        }

        return merged;
    }

    void main(){
        Random rand = new Random();
        LinkedList<Integer> l1 = new LinkedList<>();

        for(int i = 0; i < 67; i++){
            l1.add((int) (rand.nextDouble()*100) + 1);
        }

        System.out.println(l1);
        l1 = linkedListMergeSort(l1);
        System.out.println(l1);
    }
}
