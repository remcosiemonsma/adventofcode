package nl.remcoder.adventofcode.library.pathfinding;

import java.util.Map;

public interface Node extends Comparable<Node> {
    Map<? extends Node, Integer> getNeighbors();
    int getDistance();
    boolean isVisited();
    void setVisited(boolean visited);
    void setDistance(int distance);
    void printStateInformation();

    @Override
    default int compareTo(Node o) {
        return Integer.compare(this.getDistance(), o.getDistance());
    }
}
