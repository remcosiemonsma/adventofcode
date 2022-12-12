package nl.remcoder.adventofcode.library.pathfinding;

import java.util.Map;

public abstract class Node implements Comparable<Node> {
    private long distance = Long.MAX_VALUE;
    private boolean visited;
    public abstract Map<? extends Node, Long> getNeighbors();
    public long getDistance() {
        return distance;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public void setDistance(long distance) {
        this.distance = distance;
    }
    public abstract void printStateInformation();

    @Override
    public int compareTo(Node o) {
        return Long.compare(this.getDistance(), o.getDistance());
    }
}
