package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {

    private int wantedConnections = 10;

    @Override
    public Integer handlePart1(Stream<String> input) {
        var points = input.map(line -> {
                              var split = line.split(",");
                              var point = new double[split.length];
                              for (int i = 0; i < split.length; i++) {
                                  point[i] = Double.parseDouble(split[i]);
                              }
                              return point;
                          })
                          .toList();

        var pairs = findPairs(points);

        var circuits = new ArrayList<Set<Integer>>();

        for (var i = 0; i < wantedConnections; i++) {
            var pair = pairs.removeFirst();
            var firstPossibleCircuit = circuits.stream().filter(circuit -> circuit.contains(pair.i())).findFirst();
            var secondPossibleCircuit = circuits.stream().filter(circuit -> circuit.contains(pair.j())).findFirst();

            if (firstPossibleCircuit.isPresent()) {
                var firstCircuit = firstPossibleCircuit.get();
                if (secondPossibleCircuit.isPresent()) {
                    var secondCircuit = secondPossibleCircuit.get();
                    if (firstCircuit != secondCircuit) {
                        var newCircuit = new HashSet<Integer>();
                        newCircuit.addAll(firstCircuit);
                        newCircuit.addAll(secondCircuit);

                        circuits.remove(firstCircuit);
                        circuits.remove(secondCircuit);
                        circuits.add(newCircuit);
                    }
                } else {
                    firstCircuit.add(pair.j());
                }
            } else {
                if (secondPossibleCircuit.isPresent()) {
                    var secondCircuit = secondPossibleCircuit.get();
                    secondCircuit.add(pair.i());
                } else {
                    var newCircuit = new HashSet<Integer>();
                    newCircuit.add(pair.i());
                    newCircuit.add(pair.j());

                    circuits.add(newCircuit);
                }
            }
        }

        circuits.sort(Comparator.<Set<Integer>>comparingInt(Set::size).reversed());

        var result = 1;

        for (var i = 0; i < 3; i++) {
            result *= circuits.get(i).size();
        }

        return result;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var points = input.map(line -> {
                              var split = line.split(",");
                              var point = new double[split.length];
                              for (int i = 0; i < split.length; i++) {
                                  point[i] = Double.parseDouble(split[i]);
                              }
                              return point;
                          })
                          .toList();

        var pairs = findPairs(points);

        var circuits = new ArrayList<Set<Integer>>();

        var pair = pairs.removeFirst();

        var first = new HashSet<Integer>();
        first.add(pair.i());
        first.add(pair.j());

        circuits.add(first);

        while (circuits.getFirst().size() != points.size()) {
            var nextpair = pairs.removeFirst();
            pair = nextpair;
            var firstPossibleCircuit = circuits.stream().filter(circuit -> circuit.contains(nextpair.i())).findFirst();
            var secondPossibleCircuit = circuits.stream().filter(circuit -> circuit.contains(nextpair.j())).findFirst();

            if (firstPossibleCircuit.isPresent()) {
                var firstCircuit = firstPossibleCircuit.get();
                if (secondPossibleCircuit.isPresent()) {
                    var secondCircuit = secondPossibleCircuit.get();
                    if (firstCircuit != secondCircuit) {
                        var newCircuit = new HashSet<Integer>();
                        newCircuit.addAll(firstCircuit);
                        newCircuit.addAll(secondCircuit);

                        circuits.remove(firstCircuit);
                        circuits.remove(secondCircuit);
                        circuits.add(newCircuit);
                    }
                } else {
                    firstCircuit.add(pair.j());
                }
            } else {
                if (secondPossibleCircuit.isPresent()) {
                    var secondCircuit = secondPossibleCircuit.get();
                    secondCircuit.add(pair.i());
                } else {
                    var newCircuit = new HashSet<Integer>();
                    newCircuit.add(pair.i());
                    newCircuit.add(pair.j());

                    circuits.add(newCircuit);
                }
            }
        }

        return (int) (points.get(pair.i)[0] * points.get(pair.j)[0]);
    }

    public void setWantedConnections(int wantedConnections) {
        this.wantedConnections = wantedConnections;
    }

    private record Pair(int i, int j, double dist) {

    }

    private List<Pair> findPairs(List<double[]> points) {
        int KNEIGH = 550; // aantal buren per punt

        KDTree3D tree = new KDTree3D(points);

        // min-heap op afstand
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a.dist));
        HashSet<Long> seen = new HashSet<>();

        for (int i = 0; i < points.size(); i++) {
            double[] p = points.get(i);

            List<int[]> neigh = tree.kNearest(p, KNEIGH);
            for (int[] n : neigh) {
                int j = n[0];
                if (j == i) {
                    continue;
                }
                int a = Math.min(i, j);
                int b = Math.max(i, j);

                long key = (((long) a) << 32) | (b & 0xffffffffL);
                if (seen.contains(key)) {
                    continue;
                }
                seen.add(key);

                double dx = p[0] - points.get(j)[0];
                double dy = p[1] - points.get(j)[1];
                double dz = p[2] - points.get(j)[2];
                double d = Math.sqrt(dx * dx + dy * dy + dz * dz);

                pq.add(new Pair(a, b, d));
            }
        }

        // sorteer de kleinste 1000
        List<Pair> all = new ArrayList<>(pq);
        all.sort(Comparator.comparingDouble(a -> a.dist));
        return all;
    }

    private static class KDTree3D {

        // --- Node definitie ---
        static class Node {

            double[] point; // [x,y,z]

            int index;      // originele index in inputlijst

            Node left, right;

            int axis;       // 0=x, 1=y, 2=z

            Node(double[] p, int idx, int axis) {
                this.point = p;
                this.index = idx;
                this.axis = axis;
            }
        }

        private final Node root;

        // --- Bouwen van de KD-tree uit een lijst punten ---
        public KDTree3D(List<double[]> points) {
            List<int[]> indexed = new ArrayList<>();
            for (int i = 0; i < points.size(); i++) {
                indexed.add(new int[]{i, 0}); // 0 placeholder axis
            }
            this.root = build(points, indexed, 0);
        }

        private Node build(List<double[]> pts, List<int[]> idxs, int depth) {
            if (idxs.isEmpty()) {
                return null;
            }

            int axis = depth % 3;
            idxs.sort(Comparator.comparingDouble(a -> pts.get(a[0])[axis]));
            int mid = idxs.size() / 2;
            int[] midIdx = idxs.get(mid);
            Node node = new Node(pts.get(midIdx[0]), midIdx[0], axis);

            node.left = build(pts, idxs.subList(0, mid), depth + 1);
            node.right = build(pts, idxs.subList(mid + 1, idxs.size()), depth + 1);

            return node;
        }

        // --- k Nearest Neighbors ---
        public List<int[]> kNearest(double[] target, int k) {
            PriorityQueue<int[]> best =
                    new PriorityQueue<>((a, b) -> Double.compare(b[1], a[1])); // max-heap
            search(root, target, k, best);
            return new ArrayList<>(best);
        }

        private void search(Node node, double[] target, int k,
                            PriorityQueue<int[]> best) {
            if (node == null) {
                return;
            }

            double dist = distSq(target, node.point);
            if (best.size() < k) {
                best.add(new int[]{node.index, (int) Double.doubleToLongBits(dist)});
            } else if (dist < Double.longBitsToDouble(best.peek()[1])) {
                best.poll();
                best.add(new int[]{node.index, (int) Double.doubleToLongBits(dist)});
            }

            double diff = target[node.axis] - node.point[node.axis];
            Node near = diff < 0 ? node.left : node.right;
            Node far = diff < 0 ? node.right : node.left;

            search(near, target, k, best);

            // check mogelijk andere helft van splitsing
            if (best.size() < k || diff * diff < Double.longBitsToDouble(best.peek()[1])) {
                search(far, target, k, best);
            }
        }

        private double distSq(double[] a, double[] b) {
            double dx = a[0] - b[0], dy = a[1] - b[1], dz = a[2] - b[2];
            return dx * dx + dy * dy + dz * dz;
        }
    }
}
