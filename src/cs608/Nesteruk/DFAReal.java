package cs608.Nesteruk;

import java.io.PrintStream;

public class DFAReal
{
    static String[] data4 = {"81e13", "23", "24.", "25.1", "26.23", "27.456", ".2", ".34", ".561", "100.001",
            "1.E-4", "1.6e+11", ".5e-5", "16E0", "1.1.1", "1..0", "..1"};
    static double[] val4 = {81e13, 23, 24., 25.1, 26.23, 27.456, .2, .34, .561, 100.001,
            1.E-4, 1.6e+11, .5e-5, 16E0, -1, -1, -1};
    static boolean[] mtch4 = {true, true, true, true, true, true, true, true,
            true, true, true, true, true, true, false, false, false};
    FiniteStateMachine[] machines;
    String[][] data;
    boolean[][] match;
    DFAReal(PrintStream ps) {
        machines = new FiniteStateMachine[] {new FSM0(ps)};
        data = new String[][] {data4};
        match = new boolean[][] {mtch4};
    }
    public static void main(String[] args)
    {
        int ix;
        PrintStream ps = new PrintStream(System.out);
        DFAReal dfa = new DFAReal(ps);
        FiniteStateMachine machine;
        String[] data;
        boolean[] match;
        double value;
        int select = 0;
        machine = dfa.machines[select];
        data = dfa.data[select];
        match = dfa.match[select];
        ps.printf("executing FSM %d\n", select);
        for (ix = 0; ix < data.length; ++ix)
        {
            if (machine.exec(data[ix], ps))
            {
                if (match[ix])
                    ps.printf("string \"%s\" correctly accepted\n", data[ix]);
                else
                    ps.printf("--> machine failed by accepting string \"%s\"\n", data[ix]);
                value = machine.getValue();
                if (value == val4[ix])
                    ps.printf("value %f is correct\n", value);
                else
                    ps.printf("--> value %f is incorrect, should be %.15f\n", value, val4[ix]);
            }
            else
            {
                if (!match[ix])
                    ps.printf("string \"%s\" correctly rejected\n", data[ix]);
                else
                    ps.printf("--> machine failed by rejecting string \"%s\"\n", data[ix]);
            }
        }
        ps.flush();
    }
    abstract static class FiniteStateMachine {
        int start;
        int[][] delta;
        int[][] rules;
        int[] accept;
        String alpha;
        String[] cclass;
        PrintStream ps = System.out;
        FiniteStateMachine() {
        }
        FiniteStateMachine(PrintStream ps) {
            this();
            this.ps = ps;
        }
        int charClass(char inp) {
            int ix;
            if (null == cclass)
            {
                return alpha.indexOf(inp);
            }
            for (ix = 0; ix < cclass.length; ++ix)
            {
                if (0 <= cclass[ix].indexOf(inp))
                    return ix;
            }
            return -1;
        }
        boolean exec(String str, PrintStream ps) {
            int state, ix, ln, sym;
            char input;
            if (null == delta || null == accept || null == alpha)
                throw new IllegalArgumentException(String.format("FSM %s configuration not set", getClass().getName()));
            ln = str.length();
            if (null != rules)
                rule(0, -1, ' ');
            state = start;
            for (ix = 0; ix < ln; ++ix)
            {
                input = str.charAt(ix);
                sym = charClass(input);
                if (0 > sym)
                    throw new IllegalArgumentException(String.format("input character %c not in alphabet", input));
                state = delta[state][sym];
                if (state < 0 || state >= delta.length)
                    return false;
                if (null != rules)
                    rule(state, sym, input);
            }
            for (ix = 0; ix < accept.length; ++ix)
            {
                if (state == accept[ix])
                {
                    if (null != rules)
                    {
                        rule(-1, -1, ' ');
                        ps.printf("input string:\"%s\", double value:%f\n", str, getValue());
                    }
                    return true;
                }
            }
            return false;
        }
        abstract void rule(int i, int cc, char c);
        abstract double getValue();
    }
    static class FSM0 extends FiniteStateMachine {
        long integerPart;
        long fractionPart;
        long fractionDivisor;
        int exponentSign;
        long exponentPart;
        double finalValue;
        FSM0(PrintStream ps) {
            super(ps);
            start = 0;
            alpha = "0123456789.eE+-";
            cclass = new String[]{"0123456789", ".", "eE", "+-"};
            delta = new int[][] {
                    {1, 7, -1, -1},
                    {1, 2, 4, -1},
                    {3, -1, 4, -1},
                    {3, -1, 4, -1},
                    {6, -1, -1, 5},
                    {6, -1, -1, -1},
                    {6, -1, -1, -1},
                    {8, -1, -1, -1},
                    {8, -1, 4, -1},
            };
            accept = new int[] {1, 2, 3, 6, 8};

            rules = new int[][] {
                    {1, 7, -1, -1},
                    {1, 2, 4, -1},
                    {3, -1, -1, -1},
                    {3, -1, 4, -1},
                    {6, -1, -1, 5},
                    {6, -1, -1, -1},
                    {6, -1, -1, -1},
                    {8, -1, -1, -1},
                    {8, -1, 4, -1},
            };
        }

        void rule(int rule, int cc, char chr) {
            switch(rule)
            {
                case -1:
                    double value = integerPart;
                    if (fractionDivisor != 1) {
                        value += (double) fractionPart / fractionDivisor;
                    }

                    if (exponentPart != 0) {
                        long exp = exponentPart;
                        double exponentMultiplier = 1.0;
                        if (exponentSign == 1) {
                            while (exp-- > 0) {
                                exponentMultiplier *= 10.0;
                            }
                        } else {
                            while (exp-- > 0) {
                                exponentMultiplier /= 10.0;
                            }
                        }
                        value *= exponentMultiplier;
                    }
                    finalValue = value;
                    break;
                case 0:
                    integerPart = 0;
                    fractionPart = 0;
                    fractionDivisor = 1;
                    exponentSign = 1;
                    exponentPart = 0;
                    finalValue = 0.0;
                    break;
                case 1:
                    if (cc == 0) {
                        integerPart = integerPart * 10 + (chr - '0');
                    }
                    break;
                case 2:

                    break;
                case 3:
                    if (cc == 0) {  // Digit
                        fractionPart = fractionPart * 10 + (chr - '0');
                        fractionDivisor *= 10;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (cc == 3) {  // '+' or '-'
                        exponentSign = (chr == '-') ? -1 : 1;
                    }
                    break;
                case 6:
                    if (cc == 0) {
                        exponentPart = exponentPart * 10 + (chr - '0');
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (cc == 0) {
                        fractionPart = fractionPart * 10 + (chr - '0');
                        fractionDivisor *= 10;
                    }
                    break;
                default:
                    break;
            }
        }
        double getValue() {
            return finalValue;
        }
    }
}
