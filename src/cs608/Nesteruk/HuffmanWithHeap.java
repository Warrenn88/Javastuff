package cs608.Nesteruk;

import cs608.HNode;

import java.io.PrintWriter;
import java.util.ArrayList;

public class HuffmanWithHeap
{
    int heapMax = 2;
    int heapLast = 0;
    ArrayList<HNode> heap = new ArrayList<>(heapMax);
    HNode btRoot = new HNode("", 0.0);   // sentinel, avoids null root special case
    HuffmanWithHeap() {
        int ix;
        for (ix = 0; ix < heapMax; ++ix)
            heap.add(ix, null);
    }
    void expandHeap() {
        int sz, ix;
        sz = heap.size();
        heap.ensureCapacity(2 * heap.size());
        for (ix = sz; ix < 2 * sz; ++ix)
            heap.add(ix, null);
        heapMax = heap.size();
    }
    void expandHeap(int sz) {
        int jx, ix;
        jx = heap.size();
        heap.ensureCapacity(sz);
        for (ix = jx; ix < sz; ++ix)
            heap.add(ix, null);
        heapMax = heap.size();
    }
    void reheapDn(int par) {
        HNode val;
        int lastPar, chld;
        val = heap.get(par);
        lastPar = heapLast / 2;
        while (par <= lastPar)
        {
            chld = par + par;
            if (chld < heapLast)  // there is a chld + 1 sibling
            {
                if (heap.get(chld).compareTo(heap.get(chld + 1)) > 0)
                    chld += 1;
            }
            if (val.compareTo(heap.get(chld)) <= 0)
                break;
            heap.set(par, heap.get(chld));
            par = chld;
        }
        heap.set(par, val);
    }
    void reheapUp(int chld) {
        int par;
        HNode val;
        val = heap.get(chld);
        par = chld / 2;
        while (par > 0 && val.compareTo(heap.get(par)) <= 0)
        {
            heap.set(chld, heap.get(par));
            chld = par;
            par = chld / 2;
        }
        heap.set(chld, val);
    }

    void insert(HNode e) {
        if (++heapLast >= heapMax)
            expandHeap();
        heap.set(heapLast, e);
        reheapUp(heapLast);
    }

    HNode remove() {
        HNode lo;
        lo = heap.get(1);
        heap.set(1, heap.get(heapLast--));
        reheapDn(1);
        return lo;
    }

    HNode btInsert(HNode hn, HNode subtree) {
        int cc;
        cc = hn.getLetter().compareTo(subtree.getLetter());
        if (cc < 0)
        {
            if (null == subtree.getLeft())
            {
                subtree.setLeft(hn);
                return null;
            }
            return btInsert(hn, subtree.getLeft());
        }
        else if (cc > 0)
        {
            if (null == subtree.getRight())
            {
                subtree.setRight(hn);
                return null;
            }
            return btInsert(hn, subtree.getRight());
        }
        return hn;
    }

    HNode btFind(String key, HNode subtree) {
        int cc;
        while (null != subtree)
        {
            cc = key.compareTo(subtree.getLetter());
            if (cc < 0)
            {
                subtree = subtree.getLeft();
            }
            else if (cc > 0)
            {
                subtree =  subtree.getRight();
            }
            else
                return subtree;
        }
        return null;
    }

    void inOrder(HNode nod, PrintWriter pw) {
        if (nod == null) {
            return;
        }
        inOrder(nod.getLeft(), pw);

        if (nod.isLeaf()) {
            pw.println("Letter: " + nod.getLetter() + " | Code: " + nod.getCode());
        }

        inOrder(nod.getRight(), pw);
    }

    void buildPriorityQueueAndTree(String[][] freqPairs) {
        for (String[] pair : freqPairs) {
            String letter = pair[0];
            double weight = Double.parseDouble(pair[1]);

            HNode newNode = new HNode(letter, weight);

            insert(newNode);

            btInsert(newNode, btRoot);
        }
    }

    HNode
    buildHuffmanTree() {
        while (heapLast > 1) {
            HNode node1 = remove();
            HNode node2 = remove();

            HNode combinedNode = new HNode(node1.getWeight() + node2.getWeight());

            combinedNode.setLeft(node1);
            combinedNode.setRight(node2);

            node1.setParent(combinedNode);
            node2.setParent(combinedNode);

            insert(combinedNode);
        }

        return remove();
    }

    void makeCodes(HNode node, HNode root) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            StringBuilder sb = new StringBuilder();
            HNode current = node;

            while (current != root) {
                HNode parent = current.getParent();

                if (parent.getLeft() == current) {
                    sb.append("0");
                } else {
                    sb.append("1");
                }

                current = parent;
            }

            node.setCode(sb.reverse().toString());
        }

        makeCodes(node.getLeft(), root);
        makeCodes(node.getRight(), root);
    }

    void decodeBits(String bits, HNode root, PrintWriter pw) {
        HNode seq = root;

        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);

            if (bit == '0') {
                seq = seq.getLeft();
            } else {
                seq = seq.getRight();
            }

            if (seq.isLeaf()) {
                pw.print(seq.getLetter());
                seq = root;
            }
        }

        pw.flush();
    }

    void execute(String[][] freqPairs, PrintWriter pw) {
        HNode root;
        String codedPhrase = "1000011101101101001111000110111111011000010110000111111001011" +
                "001011100101011111101001010110000001011000110101110011101100001101010100001";

        buildPriorityQueueAndTree(freqPairs);

        root = buildHuffmanTree();

        makeCodes(root, root);

        inOrder(btRoot.getRight(), pw);

        decodeBits(codedPhrase, root, pw);
    }

    public static void main(String[] args)
    {
        PrintWriter pw = new PrintWriter(System.out);
        HuffmanWithHeap hc = new HuffmanWithHeap();
        String[][] slidePairs = {
                {"I", "7.0"},
                {"O", "7.5"},
                {"A", "8.2"},
                {"T", "9.1"},
                {"E", "12.7"},
        };
        String[][] freqPairs = {
                {"a", "188742"},
                {"b", "44410"},
                {"c", "99301"},
                {"d", "81028"},
                {"e", "277759"},
                {"g", "67608"},
                {"h", "60660"},
                {"i", "224262"},
                {"l", "128685"},
                {"n", "167704"},
                {"p", "74319"},
                {"o", "163578"},
                {"r", "171232"},
                {"s", "267423"},
                {"t", "162759"},
                {"u", "80992"},
                {"v", "22822"},
                {"y", "39733"},
                {" ", "150333"}};
        hc.execute(freqPairs, pw);
        pw.close();
    }
}
