package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lowestAValue = new AtomicInteger(Integer.MAX_VALUE);
        var lowestVValue = new AtomicInteger(Integer.MAX_VALUE);
        var lowestPValue = new AtomicInteger(Integer.MAX_VALUE);
        var index = new AtomicInteger(-1);
        var lowestIndex = new AtomicInteger(Integer.MAX_VALUE);

        input.forEach(s -> {
            index.addAndGet(1);
            var values = s.split(" ");
            var asum = Arrays.stream(values[2].replaceAll("[^\\d,]", "")
                                              .split(","))
                             .mapToInt(Integer::parseInt)
                             .sum();
            var vsum = Arrays.stream(values[1].replaceAll("[^\\d,]", "")
                                              .split(","))
                             .mapToInt(Integer::parseInt)
                             .sum();
            var psum = Arrays.stream(values[0].replaceAll("[^\\d,]", "")
                                              .split(","))
                             .mapToInt(Integer::parseInt)
                             .sum();
            if (asum < lowestAValue.get()) {
                lowestAValue.set(asum);
                lowestVValue.set(vsum);
                lowestPValue.set(psum);
                lowestIndex.set(index.get());
            } else if (asum == lowestAValue.get()) {
                if (vsum < lowestVValue.get()) {
                    lowestVValue.set(vsum);
                    lowestPValue.set(psum);
                    lowestIndex.set(index.get());
                } else if (psum < lowestPValue.get()) {
                    lowestPValue.set(psum);
                    lowestIndex.set(index.get());
                }
            }
        });

        return lowestIndex.get();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var index = new AtomicInteger(0);

        var particles = new ArrayList<>(input.map(s -> parseParticle(s, index)).toList());

        var stepsWithoutCollision = 0;

        while (stepsWithoutCollision < 1000) {
            var particlesToRemove = new ArrayList<Particle>();

            var collisionFound = false;
            for (var particle : particles) {
                if (particlesToRemove.contains(particle)) {
                    continue;
                }
                for (var otherParticle : particles) {
                    if (particle != otherParticle && isCollision(particle, otherParticle)) {
                        collisionFound = true;
                        particlesToRemove.add(particle);
                        particlesToRemove.add(otherParticle);
                    }
                }
            }
            if (!collisionFound) {
                stepsWithoutCollision++;
            }

            particles.removeAll(particlesToRemove);

            for (var particle : particles) {
                particle.move();
            }
        }

        return particles.size();
    }

    private Particle parseParticle(String line, AtomicInteger index) {
        var values = line.split(" ");
        var pvalues = values[0].replaceAll("[^\\d,\\-]", "").split(",");
        var vvalues = values[1].replaceAll("[^\\d,\\-]", "").split(",");
        var avalues = values[2].replaceAll("[^\\d,\\-]", "").split(",");
        return new Particle(index.getAndAdd(1), Integer.parseInt(pvalues[0]), Integer.parseInt(pvalues[1]),
                            Integer.parseInt(pvalues[2]), Integer.parseInt(vvalues[0]), Integer.parseInt(vvalues[1]),
                            Integer.parseInt(vvalues[2]), Integer.parseInt(avalues[0]), Integer.parseInt(avalues[1]),
                            Integer.parseInt(avalues[2]));
    }

    private static boolean isCollision(Particle particle1, Particle particle2) {
        return particle1.px == particle2.px && particle1.py == particle2.py && particle1.pz == particle2.pz;
    }

    private static class Particle {
        private final int id;
        private int px;
        private int py;
        private int pz;
        private int vx;
        private int vy;
        private int vz;
        private final int ax;
        private final int ay;
        private final int az;

        Particle(int id, int px, int py, int pz, int vx, int vy, int vz, int ax, int ay, int az) {
            this.id = id;
            this.px = px;
            this.py = py;
            this.pz = pz;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.ax = ax;
            this.ay = ay;
            this.az = az;
        }

        void move() {
            vx += ax;
            vy += ay;
            vz += az;
            px += vx;
            py += vy;
            pz += vz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Particle particle = (Particle) o;

            return id == particle.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return "Particle{" + "id=" + id + ", px=" + px + ", py=" + py + ", pz=" + pz + ", vx=" + vx + ", vy=" + vy +
                   ", vz=" + vz + ", ax=" + ax + ", ay=" + ay + ", az=" + az + '}';
        }
    }
}
