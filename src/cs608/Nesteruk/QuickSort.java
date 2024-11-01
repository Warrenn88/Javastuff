package cs608.Nesteruk;

import cs608.AlgoUtil;
import cs608.Metrics;

import java.io.PrintWriter;

import static cs608.DatasetOne.dataOne;

public class QuickSort
{
    static String metCompare = "compares";
    static String metSwapCounter = "swaps";
    static String metSortSize = "data size";
    Metrics ctrs;
    Partition part;
    PrintWriter pw;

    QuickSort(Metrics mtr, PrintWriter pw) {
        ctrs = mtr;
        ctrs.addCounter(metCompare);
        ctrs.addCounter(metSwapCounter);
        part = new Partition(mtr);
        this. pw = pw;
    }
    QuickSort(Metrics mtr) {
        this(mtr, null);
    }

    void swap(int[] data, int s1, int s2) {
        int tmp;
        tmp = data[s1];
        data[s1] = data[s2];
        data[s2] = tmp;
        ctrs.count(metSwapCounter);
    }

    void sort(int[] data, int beg, int end) {
        int dl = end - beg + 1;
        if (beg < end) {
            int pv = part.partition(data, beg, end);
            System.out.println("DEBUG STATEMENT 1: beg = "+ beg +"pv = "+ pv + "end = " + end);
            if (pv > beg) {
                sort(data, beg, pv - 1);
                System.out.println("DEBUG STATEMENT 2: beg = "+ beg +"pv = "+ pv + "end = " + end);
            }
            if (pv < end) {
                sort(data, pv + 1, end);
                System.out.println("DEBUG STATEMENT 3: beg = "+ beg +"pv = "+ pv + "end = " + end);
            }
        }
    }

    public static void main(String[] args)
    {
        QuickSort qs;
        Metrics metrics = new Metrics(metSortSize);
        PrintWriter pw = new PrintWriter(System.out);
        int[] testData1 = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
        int[] testData2 = {3, 2, 1, 0};
        int[] testData3 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] data;
//  data = testData1;
        data = dataOne;
        metrics.incr(metSortSize, data.length);
        qs = new QuickSort(metrics);
        qs.sort(data, 0, data.length - 1);
        if (AlgoUtil.verifyOrder(data))
            pw.println("QuickSort succeeded");
        else
            pw.println("QuickSort failed");
        metrics.display(pw);
        pw.printf("n lg n = %5.1f\n", data.length * AlgoUtil.log2(data.length));
        pw.close();
    }
}
