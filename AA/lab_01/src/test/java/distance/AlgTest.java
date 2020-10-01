package distance;

import benchmark.CPUTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.Random;

class AlgTest {
    private final Random random = new Random();

    private char genChar() {
        return (char)('a' + random.nextInt(26));
    }
    private String genWord(int len) {
        char[] word = new char[len];
        for (int i = 0; i < word.length; i++) {
            word[i] = genChar();
        }

        return new String(word);
    }

    void test(String str1, String str2) {
        System.out.printf("word1 [" + str1 + "]{" + str1.length() + "} word2 [" + str2 + "]{" + str2.length() + "}");

        long startTime = CPUTime.getCPUTime();
        int levRes = Alg.levenstein(str1, str2);
        long levTime = CPUTime.getCPUTime() - startTime;

        startTime = CPUTime.getCPUTime();
        //int recRes = Alg.recursLevenstein(str1, str2);
        long recTime = CPUTime.getCPUTime() - startTime;

        startTime = CPUTime.getCPUTime();
        int damRes = Alg.damerauLevenstein(str1, str2);
        long damTime = CPUTime.getCPUTime() - startTime;

        startTime = CPUTime.getCPUTime();
        //int damRecRes = Alg.recursDamerauLevenstein(str1, str2);
        long damRecTime = CPUTime.getCPUTime() - startTime;

        startTime = CPUTime.getCPUTime();
        int levRecMemoRes = Alg.memoRecursLevenstein(str1, str2, null);
        long levRecMemoTime = CPUTime.getCPUTime() - startTime;

        System.out.println();
        System.out.println("Levenstein                 : " + levTime);
        //System.out.println("Levenstein recurs          : " + recTime);
        System.out.println("Levenstein recurs + memo   : " + levRecMemoTime );
        System.out.println("Damerau-Levenshtein        : " + damTime);
        //System.out.println("Damerau-Levenshtein recurs : " + damRecTime);
        //assert(levRes == recRes);
        //assert(recRes == damRes);
        //assert(damRes == damRecRes);
        System.out.println("RESULT: " + levRes);
    }

    @RepeatedTest(value = 10, name = "repetition {currentRepetition} / {totalRepetitions")
    void compareRandomlyAll(RepetitionInfo repetitionInfo) {
        //int size1 = random.nextInt(13);
        //int size2 = random.nextInt(13);

        String word1 = genWord(500);
        String word2 = genWord(500);

        System.out.println("::: Repetition: " + repetitionInfo.getCurrentRepetition());
        test(word1, word2);
    }

    @Test
    @Disabled
    void benchmark() {

        String str1 = genWord(5);
        String str2 = genWord(5);
        long levTime = 0;
        for (int i = 0; i < 10; i++) {
            long startTime = CPUTime.getCPUTime();
            int levRes = Alg.levenstein(str1, str2);
            levTime += CPUTime.getCPUTime() - startTime;
        }
        levTime /= 10;

        long levResTime = 0;
        for (int i = 0; i < 10; i++) {
            long startTime = CPUTime.getCPUTime();
            int tmp = Alg.recursLevenstein(str1, str2);
            levResTime += CPUTime.getCPUTime() - startTime;
        }
        levResTime /= 10;

        long levResMemoTime = 0;
        for (int i = 0; i < 10; i++) {
            long startTime = CPUTime.getCPUTime();
            int tmp = Alg.recursLevenstein(str1, str2);
            levResMemoTime += CPUTime.getCPUTime() - startTime;
        }
        levResMemoTime /= 10;

        long demTime = 0;
        for (int i = 0; i < 10; i++) {
            long startTime = CPUTime.getCPUTime();
            int tmp = Alg.recursLevenstein(str1, str2);
            demTime += CPUTime.getCPUTime() - startTime;
        }
        demTime /= 10;
        System.out.println("Levenstein           : " + levTime);
        System.out.println("Levenstein recurs    : " + levResTime);
        System.out.println("Levenstein recursmtr : " + levResMemoTime);
        System.out.println("Damerau-Levenshtein  : " + demTime);
    }
}