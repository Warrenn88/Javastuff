package cs608.Nesteruk;

public class CryptoTmplt
{
    static final String A = "Alice";
    static final String B = "Bob";

    int expoMod(int val, int exp, int mod) {
        int accum = 1;
        val = val % mod;
        while (exp > 0) {
            if ((exp % 2) == 1) {
                accum = (accum * val) % mod;
            }
            exp = exp / 2;
            val = (val * val) % mod;
        }
        return accum;
    }

    boolean isPrimitiveRoot(int n, int m) {
        int mm1 = m - 1;
        int[] factors = new int[20]; // Assuming maximum 20 unique prime factors
        int factorCount = 0;
        int num = mm1;
        for (int i = 2; i <= num / i; i++) {
            if (num % i == 0) {
                factors[factorCount++] = i;
                while (num % i == 0)
                    num /= i;
            }
        }
        if (num > 1)
            factors[factorCount++] = num;
        for (int jx = 0; jx < factorCount; jx++) {
            int e = mm1 / factors[jx];
            if (expoMod(n, e, m) == 1)
                return false;
        }
        return true;
    }

    void dhmKeyExch(int p, int g, int as, int bs) {
        int a = expoMod(g, as, p);
        int b = expoMod(g, bs, p);
        int sa = expoMod(b, as, p);
        int sb = expoMod(a, bs, p);
        System.out.printf("prime modulus=%d, primitive root=%d\n", p, g);
        System.out.printf("%s with secret %d sends %s %d^%d mod %d=%d\n", A, as, B, g, as, p, a);
        System.out.printf("%s with secret %d sends %s %d^%d mod %d=%d\n", B, bs, A, g, bs, p, b);
        System.out.printf("%s computes %d^%d mod %d=%d\n", A, b, as, p, sa);
        System.out.printf("%s computes %d^%d mod %d=%d\n", B, a, bs, p, sb);
        if (sb != sa)
            System.out.println("the shared secrets are not equal");
    }

    public static void main(String[] args)
    {
        CryptoTmplt cr = new CryptoTmplt();
        int r1 = 101, r2 = 103, r3 = 105, mod = 199, pr, rts;
        int bs = 157, as = 137;   // Bob's secret, Alice's secret
        boolean r;
        rts = pr = 0;
        r = cr.isPrimitiveRoot(r1, mod);
        if (r)
        {
            pr = r1;
            rts += 1;
        }
        System.out.printf("%d is%s a primitive root of %d\n", r1, (r) ? "" : " not", mod );
        r = cr.isPrimitiveRoot(r2, mod);
        if (r)
        {
            pr = r2;
            rts += 1;
        }
        System.out.printf("%d is%s a primitive root of %d\n", r2, (r) ? "" : " not", mod );
        r = cr.isPrimitiveRoot(r3, mod);
        if (r)
        {
            pr = r3;
            rts += 1;
        }
        System.out.printf("%d is%s a primitive root of %d\n", r3, (r) ? "" : " not", mod );
        if (0 == pr || rts != 1)
        {
            System.out.println("no single primitive root - check failed");
            return;
        }
        cr.dhmKeyExch(mod, pr, as, bs);
    }
}