package org.kotopka;

public class FordFulkerson {

    private final boolean[] marked;
    private final FlowEdge[] edgeTo;
    private double value;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        int size = G.V();
        if (s < 0 || t < 0 || s >= size || t >= size) throw new IllegalArgumentException("Invalid network: " + s + "->" + t);

        this.marked = new boolean[size];
        this.edgeTo = new FlowEdge[size];
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

}
