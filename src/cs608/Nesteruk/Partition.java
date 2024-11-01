package cs608.Nesteruk;

import cs608.AlgoUtil;
import cs608.Metrics;

import java.io.PrintWriter;

public class Partition
{
    static String metCompare = "compares";
    static String metSwapCounter = "swaps";
    Metrics ctrs;
    PrintWriter pw;

    Partition(Metrics mtr, PrintWriter pw)
    {
        ctrs = mtr;
        ctrs.addCounter(metCompare);
        ctrs.addCounter(metSwapCounter);
        this.pw = pw;
    }
    Partition(Metrics mtr)
    {
        this(mtr, null);
    }

    void swap(int[] data, int s1, int s2)
    {
        int tmp;
        tmp = data[s1];
        data[s1] = data[s2];
        data[s2] = tmp;
        ctrs.count(metSwapCounter);
    }

    int partition(int[] data, int lo, int hi) {
        int lft = lo, rgt = hi - 1, pivot = data[hi];
        boolean working = true;

        while (working) {
            while (lft <= rgt && data[lft] < pivot) {
                lft++;
                ctrs.count(metCompare);
            }
            while (rgt >= lft && data[rgt] > pivot) {
                rgt--;
                ctrs.count(metCompare);
            }
            if (lft < rgt) {
                swap(data, lft, rgt);
                lft++;
                rgt--;
            } else {
                working = false;
            }
        }


        if (lft != hi) {
            swap(data, lft, hi);
        }

        return lft;
    }

    public static void main(String[] args)
    {
        Partition ps;
        Metrics metrics = new Metrics();
        PrintWriter pw = new PrintWriter(System.out);
//  PrintStream po = System.out;
        int[] testData1 = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
        int[] testData2 = {3, 2, 1, 0};
        int[] data;
        data = testData1;
        ps = new Partition(metrics, pw);
        pw.print("array:");
        AlgoUtil.dispArray(data, pw);
        ps.partition(data, 0, data.length - 1);
        pw.print("array:");
        AlgoUtil.dispArray(data, pw);
        metrics.display(pw);
        pw.close();
    }
}

