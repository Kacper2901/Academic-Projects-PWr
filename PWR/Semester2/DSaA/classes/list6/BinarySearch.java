import java.util.*;

public class BinarySearch {
     private class Student{
        char nameFirstLetter;
        int age;
        Student(char nameFirstLetter, int age){
            this.nameFirstLetter = nameFirstLetter;
            this.age = age;
        }


        String showStudent(){
            String s = Character.toString(nameFirstLetter) + Integer.toString(age);
            return s;
        }
    }

    void main(){
        int[] arr = new int[100];
        Student[] students = new Student[10];
        for(int i = 0; i < 100; i++){
            arr[i] = 2*i;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("8 idx: " + Arrays.binarySearch(arr, 8)); //returns index of given array
        System.out.println("9 idx: " + Arrays.binarySearch(arr,9)); //returns (-1 - position of that number
                                                            // if it was actually there)
        System.out.print("[");
        for(int i = 0; i < 10; i++){
            students[i] = new Student((char)(i+97), i);
            System.out.print(students[i].showStudent() + ", ");
        }
        System.out.println("]");
        Student key = new Student('d', 1231242); //we will search by letter so age can be random
        Comparator<Student> com = (s1, s2) -> Character.compare(s1.nameFirstLetter, s2.nameFirstLetter);


        System.out.println("student with letter d idx: " + Arrays.binarySearch(students, key, com)); //searches for key according to given comparator



        List<Student> list = new ArrayList<>();
        list.add(new Student('x',20));
        list.add(new Student('y',12));
        list.add(new Student('z', 123));

        Comparator<Student> letterComparator = (s1,s2) -> Integer.compare(s1.age,s2.age);

        list.sort(letterComparator);

        Student klucz = new Student('b', 123);
        int index = Collections.binarySearch(list, klucz, letterComparator); //it works on lists

        System.out.print("list of students: ");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i).showStudent() + " -> ");
        }
        System.out.println("student with age 123 index: " + index);

        System.out.println();
        System.out.println();
        System.out.println();
        int size = 100_000_000;
        int[] bigArray = new int[size];
        for (int i = 0; i < size; i++) bigArray[i] = i;

        int target = 99_999_999;

        long startLin = System.nanoTime();
        int linearIdx = -1;
        for (int i = 0; i < bigArray.length; i++) {
            if (bigArray[i] == target) {
                linearIdx = i;
                break;
            }
        }
        long endLin = System.nanoTime();

        long startBin = System.nanoTime();
        int binaryIdx = Arrays.binarySearch(bigArray, target);
        long endBin = System.nanoTime();

        System.out.println("Linear search: " + (endLin - startLin) / 1_000_000.0 + " ms");
        System.out.println("Binary search: " + (endBin - startBin) / 1_000_000.0 + " ms");





    }


}
