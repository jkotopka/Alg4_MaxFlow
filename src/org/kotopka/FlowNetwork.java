package org.kotopka;

public class FlowNetwork {

    private final int V;
    private final Bag<FlowEdge>[] adj;

    @SuppressWarnings("unchecked")
    public FlowNetwork(int V) {
        this.V = V;
        this.adj = (Bag<FlowEdge>[]) new Bag[V];

        for (int i = 0; i < adj.length; i++) {
            adj[i] = new Bag<>();
        }
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<FlowEdge> adj(int v) {
        if (v < 0 || v >= adj.length) throw new IllegalArgumentException("Invalid vertex: " + v);

        return adj[v];
    }

}
