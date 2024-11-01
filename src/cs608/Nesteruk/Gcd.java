package cs608.Nesteruk;

public class Gcd {
    public static void main(String[] args) {
        int[][] pairs = {{288, 51}, {288, 55}, {461952, 116298}};
        int a, b, q, r;
        for (int[] pair : pairs) {
            a = pair[0];
            b = pair[1];

            while (a != b) {
                if (a > b) {
                    a = a-b;
                } else {
                    b = b-a;
                }
            }
            System.out.printf("GCD(%d,%d)=%d\n", pair[0], pair[1], b);
        }
    }
}

/*
For Assign1, review the first Algorithms lecture which is about the Greatest Common Divisor algorithm, then implement the GCD algorithm with the attached template.
To make it interesting, use only addition and subtraction operations, no multiplication, division, or modulo operators.  Of course, use any logical comparisons you need.
The input follows the numbers in the slides, and the output should be:
GCD(288,51)=3
GCD(288,55)=1
GCD(461952,116298)=18
*/

