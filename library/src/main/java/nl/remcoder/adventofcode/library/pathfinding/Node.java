package nl.remcoder.adventofcode.library.pathfinding;

import java.util.Map;

public interface Node extends Comparable<Node> {
    Map<? extends Node, Long> getNeighbors();
    long getDistance();
    boolean isVisited();
    void setVisited(boolean visited);
    void setDistance(long distance);
    void printStateInformation();

    @Override
    default int compareTo(Node o) {
        return Long.compare(this.getDistance(), o.getDistance());
    }
}
