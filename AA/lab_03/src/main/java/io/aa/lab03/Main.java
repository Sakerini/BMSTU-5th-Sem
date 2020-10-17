package io.aa.lab03;

import io.aa.lab03.benchmark.BenchmarkSorts;
import io.aa.lab03.sort.IFill;
import io.aa.lab03.sort.Sort;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        try (FileWriter fw = new FileWriter(new File("out_sorted.csv"))) {
            new BenchmarkSorts(fw).doBenchamrk(BenchmarkSorts::fillSorted);
        }

        try (FileWriter fw = new FileWriter(new File("out_sortedBackWord.csv"))) {
            new BenchmarkSorts(fw).doBenchamrk(BenchmarkSorts::fillSortedBackword);
        }

        try (FileWriter fw = new FileWriter(new File("out_random.csv"))) {
            new BenchmarkSorts(fw).doBenchamrk(BenchmarkSorts::fillRandom);
        }
    }

}
