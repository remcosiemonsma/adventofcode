package nl.remcoder.adventofcode.day20;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<Particle> particles = new ArrayList<>();

        AtomicInteger index = new AtomicInteger(0);

        Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI())).forEach(s -> {
            String[] values = s.split(" ");
            String[] pvalues = values[0].replaceAll("[^\\d,\\-]", "").split(",");
            String[] vvalues = values[1].replaceAll("[^\\d,\\-]", "").split(",");
            String[] avalues = values[2].replaceAll("[^\\d,\\-]", "").split(",");
            particles.add(new Particle(index.getAndAdd(1), Integer.parseInt(pvalues[0]), Integer.parseInt(pvalues[1]),
                    Integer.parseInt(pvalues[2]), Integer.parseInt(vvalues[0]), Integer.parseInt(vvalues[1]),
                    Integer.parseInt(vvalues[2]), Integer.parseInt(avalues[0]), Integer.parseInt(avalues[1]),
                    Integer.parseInt(avalues[2])));
        });

        int stepsWithoutCollision = 0;

        while (stepsWithoutCollision < 1000) {
            List<Particle> particlesToRemove = new ArrayList<>();

            boolean collisionFound = false;
            for (Particle particle : particles) {
                if (particlesToRemove.contains(particle)) {
                    continue;
                }
                for (Particle otherParticle : particles) {
                    if (particle != otherParticle && isCollision(particle, otherParticle)) {
                        collisionFound = true;
                        particlesToRemove.add(particle);
                        particlesToRemove.add(otherParticle);
                        System.out.println("Collision! " + particle + " " + otherParticle);
                    }
                }
            }
            if (!collisionFound) {
                stepsWithoutCollision++;
            }

            particles.removeAll(particlesToRemove);

            for (Particle particle : particles) {
                particle.move();
            }
        }

        System.out.println(particles.size());
    }

    private static boolean isCollision(Particle particle1, Particle particle2) {
        return particle1.px == particle2.px && particle1.py == particle2.py && particle1.pz == particle2.pz;
    }

    private static class Particle {
        int id;
        int px;
        int py;
        int pz;
        int vx;
        int vy;
        int vz;
        int ax;
        int ay;
        int az;

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
