package org.kotopka;

public class FlowEdge {

    private final int v;
    private final int w;
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capacity) {
        if (v < 0 || w < 0) throw new IllegalArgumentException("Invalid edge: " + v + "->" + w);
        if (capacity < 0)   throw new IllegalArgumentException("Invalid capacity: " + capacity);

        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    public int from() { return v; }

    public int to() { return w; }

    public int other(int vertex) {
        if (vertex == v) return w;
        return v;
    }

    public double capacity() { return capacity; }

    public double flow() { return flow; }

    public double residualCapacityTo(int vertex) {
        if      (vertex == v) return flow;
        else if (vertex == w) return capacity - flow;
        else throw new IllegalArgumentException("Invalid vertex " + vertex);
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if      (vertex == v) flow -= delta;
        else if (vertex == w) flow += delta;
        else throw new IllegalArgumentException("Invalid vertex " + vertex);
    }

    @Override
    public String toString() {
        return String.format("%d->%d %.2f %.2f", v, w, capacity, flow);
    }
}
