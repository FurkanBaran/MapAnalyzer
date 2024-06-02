
/**
 * Union-Find data structure for cycle detection in the barely connected map.
 */
class UnionFind {
    private final int[] parent;
    private final int[] rank;

    /**
     * Constructs a UnionFind object with the given size.
     *
     * @param size the size of the UnionFind object.
     */
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    /**
     * Finds the root of the given element.
     *
     * @param p the element to find the root of.
     * @return the root of the element.
     */
    public int find(int p) {
        if (parent[p] != p) {
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    /**
     * Unions the sets containing the given elements.
     *
     * @param p the first element.
     * @param q the second element.
     * @return true if the sets were successfully unioned, false otherwise.
     */
    public boolean union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return false;

        if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        } else if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        return true;
    }
}
