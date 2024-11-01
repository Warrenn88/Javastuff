package cs608.Nesteruk;

import cs608.AlgoUtil;
import cs608.Metrics;

import java.io.PrintWriter;

import static cs608.DatasetOne.dataOne;

public class BubbleSort
{
    static String cmpCounter = "compares";
    static String swapCounter = "swaps";
    static String passCounter = "passes";
    Metrics ctrs;

    BubbleSort(Metrics mtr) {
        ctrs = mtr;
        ctrs.addCounter(cmpCounter);
        ctrs.addCounter(swapCounter);
        ctrs.addCounter(passCounter);
    }

    BubbleSort() {
        this(new Metrics());
    }

    void swap(int[] data, int s1, int s2) {
        int tmp;
        tmp = data[s1];
        data[s1] = data[s2];
        data[s2] = tmp;
        ctrs.count(swapCounter);
    }

    public void sort(int[] data) {
        int imx, nm1 = data.length - 1;
        int ix, jx, kx;
        for (int i = 0; i < nm1; ++i){
            ctrs.count(passCounter);
            for (int j = 0; j < nm1 - i; ++j) {
                ctrs.count(cmpCounter);
                if (data[j] > data[j + 1]) {
                        swap(data, j, j + 1);
                }
            }
        }
    }

    public void sortf(int[] data) {
        int imx = data.length;
        int jx, kx;
        boolean swapped = true;
        for (int i = 0; i < imx - 1 && swapped; ++i) {
            swapped = false;
            ctrs.count(passCounter);
            for (int j = 0; j < imx - 1 - i; ++j) {
                ctrs.count(cmpCounter);
                if(data[j] > data[j+1]) {
                    swap(data, j, j + 1);
                    swapped = true;
                }
            }
        }
    }

    public void sorth(int[] data) {
        int hi, lo;
        int jx, kx;
        boolean swapped;
        hi = data.length-1;
        lo = 0;
        swapped = true;

        while(lo < hi && swapped) {
            swapped = false;
            ctrs.count(passCounter);

            for (jx = lo; jx < hi; ++jx) {
                ctrs.count(cmpCounter);
                if(data[jx] > data[jx + 1]) {
                    swap(data, jx, jx + 1);
                    swapped = true;
                }
            }
            hi--;

            if(!swapped) break;

            swapped = false;
            ctrs.count(passCounter);

            for (jx = hi; jx > lo; --jx) {
                ctrs.count(cmpCounter);
                if (data[jx] < data[jx-1]) {
                    swap(data, jx, jx - 1);
                    swapped = true;
                }
            }
            lo++;
        }
    }

    public static void main(String[] args)
    {
        BubbleSort bbl;
        PrintWriter pw = new PrintWriter(System.out);
        Metrics metrics = new Metrics();
//  int[] testData = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
        int[] data;
        int ix;
        bbl = new BubbleSort(metrics);
        //    data = new int[testData.length];
        data = new int[dataOne.length];
        pw.printf("array length is %d\n", data.length);
        pw.printf("estimated compares is n(n-1)/2 = %d\n", (data.length * (data.length - 1)) / 2);
        for (ix = 0; ix < 3; ++ix)
        {
//    System.arraycopy(testData, 0, data, 0, data.length);
            System.arraycopy(dataOne, 0, data, 0, data.length);
            metrics.reset();
            pw.print("BubbleSort");
            switch (ix)
            {
                case 0:
                    pw.print(" basic:");
                    bbl.sort(data);
                    break;
                case 1:
                    pw.print(" run till no swap:");
                    bbl.sortf(data);
                    break;
                default:
                    pw.print(" run up/down till no swap:");
                    bbl.sorth(data);
                    break;
            }
            pw.println(AlgoUtil.verifyOrder(data) ? " succeeded" : " failed");
            metrics.display(pw);
        }
        pw.close();
    }
}