package hackerrank.implementation;

import java.io.*;
import java.util.*;


public class BreakingTheRecord {

    /*
     * Complete the breakingRecords function below.
     */
    static int[] breakingRecords(int[] score) {

        int min=-1;
        int max=-1;
        int maxCounter=0;
        int minCounter=0;

        for(int i=0;i<score.length;i++){
            if(i==0){
                min=score[i];
                max=score[i];
            }else{
                if(score[i]>max){
                    maxCounter++;
                    max=score[i];
                }else if(score[i]<min){
                    minCounter++;
                    min=score[i];
                }
            }
        }

        int[] result=new int[2];
        result[0]=maxCounter;
        result[1]=minCounter;

        return result;
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(scan.nextLine().trim());

        int[] score = new int[n];

        String[] scoreItems = scan.nextLine().split(" ");

        for (int scoreItr = 0; scoreItr < n; scoreItr++) {
            int scoreItem = Integer.parseInt(scoreItems[scoreItr].trim());
            score[scoreItr] = scoreItem;
        }

        int[] result = breakingRecords(score);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bw.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bw.write(" ");
            }
        }

        bw.newLine();
        bw.close();
    }
}
// input:
// 9
// 10 5 20 20 4 5 2 25 1
// output:
// 2 4