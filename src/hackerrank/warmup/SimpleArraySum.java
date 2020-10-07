package hackerrank.warmup;
import java.util.*;


public class SimpleArraySum {
    static int simpleArraySum(int n, int[] ar) {
        int count=0;
        for (int j : ar) {
            count = j + count;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] ar = new int[n];
        for(int ar_i = 0; ar_i < n; ar_i++){
            ar[ar_i] = in.nextInt();
        }
        int result = simpleArraySum(n, ar);
        System.out.println(result);
    }
}

