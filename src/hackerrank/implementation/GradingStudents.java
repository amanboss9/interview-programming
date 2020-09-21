package hackerrank.implementation;

import java.io.*;
import java.util.*;

public class GradingStudents {

    /*
     * Complete the gradingStudents function below.
     */
    private static int[] gradingStudents(int[] grades) {
        int[] finalResultArray=new int[grades.length];

        for(int i=0;i<grades.length;i++){
            int nextMultiple=((grades[i]/5) + 1)*5;
            if(nextMultiple-grades[i]< 3 && grades[i] >= 38){
                finalResultArray[i]=nextMultiple;
            }else{
                finalResultArray[i]=grades[i];
            }

        }
        return finalResultArray;
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(scan.nextLine().trim());

        int[] grades = new int[n];

        for (int gradesItr = 0; gradesItr < n; gradesItr++) {
            int gradesItem = Integer.parseInt(scan.nextLine().trim());
            grades[gradesItr] = gradesItem;
        }

        int[] result = gradingStudents(grades);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bw.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bw.write("\n");
            }
        }

        bw.newLine();

        bw.close();
    }
}
// Test case:1
//4
//73
//67
//38
//33

// Output
//75
//67
//40
//33