import distance.Alg;

import java.util.*;

public class Main {

    private static void handTesting(){
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write first word: ");
            String word1 = sc.nextLine();
            System.out.println("Write second word: ");
            String word2 = sc.nextLine();

            int res = 0;
            res = Alg.levenstein(word1, word2);
            System.out.println("Levenstein         : " + res);

            res = Alg.recursLevenstein(word1, word2);
            System.out.println("Levenstein recurs  : " + res);

            res = Alg.memoRecursLevenstein(word1, word2, null);
            System.out.println("Levenstein recurs + memorization  : " + res);

            res = Alg.damerauLevenstein(word1, word2);
            System.out.println("Damerau-Levenshtein: " + res);

            System.out.println("Done. Starting next...");
            System.out.println("--------------------------------");

        }
    }

    public static void main(String[] args) {
        handTesting();
    }
}


