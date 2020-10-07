package hackerrank.implementation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BetweenTwoSets {

    /*
     * Complete the getTotalX function below.
     */
    static boolean validInt(int x,int[] a, int[] b) {
        for (int k : a) {
            if (x % k != 0)
                return false;
        }
        for (int i : b) {
            if (i % x != 0)
                return false;
        }
        return true;
    }

    static int getTotalX(int[] a, int[] b) {
        int total=0;
        Arrays.sort(a);
        Arrays.sort(b);
        for(int i=a[a.length-1];i<=b[b.length-1];i++){
            if(validInt(i,a,b)){
                total++;
            }
        }
        return total;
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scan.nextLine().split(" ");

        int n = Integer.parseInt(nm[0].trim());

        int m = Integer.parseInt(nm[1].trim());

        int[] a = new int[n];

        String[] aItems = scan.nextLine().split(" ");

        for (int aItr = 0; aItr < n; aItr++) {
            int aItem = Integer.parseInt(aItems[aItr].trim());
            a[aItr] = aItem;
        }

        int[] b = new int[m];

        String[] bItems = scan.nextLine().split(" ");

        for (int bItr = 0; bItr < m; bItr++) {
            int bItem = Integer.parseInt(bItems[bItr].trim());
            b[bItr] = bItem;
        }

        int total = getTotalX(a, b);

        bw.write(String.valueOf(total));
        bw.newLine();

        bw.close();
    }
}

