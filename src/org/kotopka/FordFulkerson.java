package org.kotopka;

public class FordFulkerson {

    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        int size = G.V();
        if (s < 0 || t < 0 || s >= size || t >= size) throw new IllegalArgumentException("Invalid network: " + s + "->" + t);

        this.value = 0.0;

        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;

            // compute bottleneck
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }
    }

    public boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        // these two arrays have to be created here
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];

        Queue<Integer> q = new Queue<>();

        q.enqueue(s);
        marked[s] = true;

        while (!q.isEmpty()) {
            int v = q.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        if (v < 0 || v >= marked.length) throw new IllegalArgumentException("Invalid vertex: " + v);

        return marked[v];
    }

    public static void main(String[] args) {
        FlowNetwork fn = new FlowNetwork(6);

        fn.addEdge(new FlowEdge(0, 1, 2.0));
        fn.addEdge(new FlowEdge(0, 2, 3.0));
        fn.addEdge(new FlowEdge(1, 3, 3.0));
        fn.addEdge(new FlowEdge(1, 4, 1.0));
        fn.addEdge(new FlowEdge(2, 3, 1.0));
        fn.addEdge(new FlowEdge(2, 4, 1.0));
        fn.addEdge(new FlowEdge(3, 5, 2.0));
        fn.addEdge(new FlowEdge(4, 5, 3.0));

        FordFulkerson ff = new FordFulkerson(fn, 0, 5);

        System.out.println("Flow value: " + ff.value());
    }

}
