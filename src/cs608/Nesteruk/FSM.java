package cs608.Nesteruk;

import java.io.PrintWriter;

public class FSM
{
    static String[] data0 = {"abbbabbba", "aaaaab", "bbaabbaa", "bbaabbaba", "ababababaa", "ababababaaa", "aaabbba"};
    static boolean[] mtch0 = {true, false, false, true, false, true, true};
    static String[] data1 = {"abbbabbba", "aaaaab", "bbaabbaa", "ababababaa", "aaabb", "babaaabbab", "aaabaa"};
    static boolean[] mtch1 = {true, true, false, false, true, true, true};
    static String[] data2 = {"a", "b", "aa", "ab", "ba", "bbabaab", "ababaab", "aab", "abb", "aabb", "bba",
            "aaba", "aaab", "abababaab", "abababaabba", "aabba", "abababaabbaba", "abababaabbaa", "abababaabbab", "aabbaa"};
    static boolean[] mtch2 = {false, false, false, false, false, false, true, true,
            false, false, false, false, false, true, true, true, true, false, false, false};
    FiniteStateMachine[] machines;
    String[][] data;
    boolean[][] match;
    FSM() {
        machines = new FiniteStateMachine[] {new FSM0(), new FSM1(), new FSM2()};
        data = new String[][] {data0, data1, data2};
        match = new boolean[][] {mtch0, mtch1, mtch2};
    }
    public static void main(String[] args)
    {
        int ix;
        PrintWriter pw = new PrintWriter(System.out);
        FSM fsm = new FSM();
        FiniteStateMachine machine;
        String[] data;
        boolean[] match;
        int select;
        // set select to 2 to select FSM2, your Finite State Machine, and the test data.
        // FSM0 and FSM1 are examples taken from the slides
        select = 2;
        machine = fsm.machines[select];
        data = fsm.data[select];
        match = fsm.match[select];
        pw.printf("executing FSM %d\n", select);
        pw.print(machine);
        for (ix = 0; ix < data.length; ++ix)
        {
            if (machine.exec(data[ix]))
            {
                if (match[ix])
                    pw.printf("%2d string \"%s\" correctly accepted\n", ix + 1, data[ix]);
                else
                    pw.printf("--> machine failed by accepting string \"%s\"\n", data[ix]);
            }
            else
            {
                if (!match[ix])
                    pw.printf("%2d string \"%s\" correctly rejected\n", ix + 1, data[ix]);
                else
                    pw.printf("--> machine failed by rejecting string \"%s\"\n", data[ix]);
            }
        }
        pw.flush();
    }
    class FiniteStateMachine {
        int start;  // start state
        int[][] delta;  // transition function
        int[] accept;  // accept state(s)
        String alpha;  // alphabet
        boolean exec(String str) {
            int state, ix, ln, sym;
            if (null == delta || null == accept || null == alpha)
                throw new IllegalArgumentException(String.format("FSM %s configuration not set", getClass().getName()));
            ln = str.length();
            state = start;
            for (ix = 0; ix < ln; ++ix)
            {
                sym = alpha.indexOf(str.charAt(ix));
                if (0 > sym)
                    throw new IllegalArgumentException(String.format("input character %c not in alphabet", str.charAt(ix)));
                state = delta[state][sym];
            }
            for (int ast : accept)
                if (state == ast)
                    return true;
            return false;
        }

        public String toString()
        {
            int ix;
            StringBuilder sb = new StringBuilder();
            sb.append("alpha: ");
            sb.append(alpha);
            sb.append(String.format("  start state: %d", start));
            sb.append("  accept states:");
            for (int acc : accept)
                sb.append(String.format(" %d", acc));
            sb.append('\n');
            sb.append("transition\n    ");
            for (ix = 0; ix < alpha.length(); ++ix)
                sb.append(String.format(" '%c'", alpha.charAt(ix)));
            sb.append('\n');
            ix = -1;
            for (int[] rw : delta)
            {
                sb.append(String.format("%3d", ++ix));
                for (int nst : rw)
                    sb.append(String.format(" %3d", nst));
                sb.append('\n');
            }
            return sb.toString();
        }
    }
    class FSM0 extends FiniteStateMachine {
        FSM0() {
            start = 0;  // start state
            delta = new int[][] {{1, 0}, {0, 0}};  // transition function
            accept = new int[] {1};  // accept state(s)
            alpha = "ab";  // alphabet
        }
    }
    class FSM1 extends FiniteStateMachine {
        // any string with an odd number of a's, with sequences of a's delimited by 0 or more b's
        FSM1() {
            start = 0;  // start state
            delta = new int[][] {{1, 0}, {0, 1}};  // transition function
            accept = new int[] {1};  // accept state(s)
            alpha = "ab";  // alphabet
        }
    }
    class FSM2 extends FiniteStateMachine {
        FSM2() {
            start = 0;
            alpha = "ab";

            delta = new int[][] {
                    {1, 6},
                    {2, 0},
                    {6, 3},
                    {6, 4},
                    {3, 6},
                    {6, 6},
                    {6, 6}
            };

            accept = new int[] {3};
        }
    }

}
