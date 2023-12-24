package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;

import com.microsoft.z3.*;

public class Day24 implements AdventOfCodeSolution<Long> {
    private long minIntersectionPoint;
    private long maxIntersectionPoint;

    public void setMinIntersectionPoint(long minIntersectionPoint) {
        this.minIntersectionPoint = minIntersectionPoint;
    }

    public void setMaxIntersectionPoint(long maxIntersectionPoint) {
        this.maxIntersectionPoint = maxIntersectionPoint;
    }

    @Override
    public Long handlePart1(Stream<String> input) {
        var hailStones = input.map(this::mapHailStone)
                              .toList();

        var amountCollide = 0L;

        for (int i = 0; i < hailStones.size(); i++) {
            HailStone hailStone = hailStones.get(i);
            for (int j = i + 1; j < hailStones.size(); j++) {
                if (doHailStonesCollideInFuture(hailStone, hailStones.get(j))) {
                    amountCollide++;
                }
            }
        }

        return amountCollide;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var hailStones = input.map(this::mapHailStone)
                              .toList();

        try (var ctx = new Context()) {
            var solver = ctx.mkSolver();

            // Coordinates of the stone
            var x = ctx.mkIntConst("x");
            var y = ctx.mkIntConst("y");
            var z = ctx.mkIntConst("z");

            // Velocity of the stone
            var vx = ctx.mkIntConst("vx");
            var vy = ctx.mkIntConst("vy");
            var vz = ctx.mkIntConst("vz");

            for (var i = 0; i < 3; i++) {
                var t = ctx.mkIntConst("t" + i); // time for the stone to reach the hail
                var hailstone = hailStones.get(i);

                var px = ctx.mkInt(hailstone.x());
                var py = ctx.mkInt(hailstone.y());
                var pz = ctx.mkInt(hailstone.z());

                var pvx = ctx.mkInt(hailstone.vx());
                var pvy = ctx.mkInt(hailstone.vy());
                var pvz = ctx.mkInt(hailstone.vz());

                var xLeft = ctx.mkAdd(x, ctx.mkMul(t, vx)); // x + t * vx
                var yLeft = ctx.mkAdd(y, ctx.mkMul(t, vy)); // y + t * vy
                var zLeft = ctx.mkAdd(z, ctx.mkMul(t, vz)); // z + t * vz

                var xRight = ctx.mkAdd(px, ctx.mkMul(t, pvx)); // px + t * pvx
                var yRight = ctx.mkAdd(py, ctx.mkMul(t, pvy)); // py + t * pvy
                var zRight = ctx.mkAdd(pz, ctx.mkMul(t, pvz)); // pz + t * pvz

                solver.add(ctx.mkGe(t, ctx.mkInt(0L))); // time should always be positive - we don't want solutions for negative time
                solver.add(ctx.mkEq(xLeft, xRight)); // x + t * vx = px + t * pvx
                solver.add(ctx.mkEq(yLeft, yRight)); // y + t * vy = py + t * pvy
                solver.add(ctx.mkEq(zLeft, zRight)); // z + t * vz = pz + t * pvz
            }

            solver.check();

            var model = solver.getModel();

            var rx = model.eval(x, false);
            var ry = model.eval(y, false);
            var rz = model.eval(z, false);

            return Long.parseLong(rx.toString()) + Long.parseLong(ry.toString()) + Long.parseLong(rz.toString());
        }
    }

    private boolean doHailStonesCollideInFuture(HailStone hailStone1, HailStone hailStone2) {
        var x1 = hailStone1.x();
        var y1 = hailStone1.y();
        var x2 = hailStone1.x() + hailStone1.vx();
        var y2 = hailStone1.y() + hailStone1.vy();
        var x3 = hailStone2.x();
        var y3 = hailStone2.y();
        var x4 = hailStone2.x() + hailStone2.vx();
        var y4 = hailStone2.y() + hailStone2.vy();

        var denominator = hailStone1.vx() * hailStone2.vy() - hailStone1.vy() * hailStone2.vx();

        if (denominator == 0) {
            if (hailStone1.x() == hailStone2.x() && hailStone1.y() == hailStone2.y()) {
                System.out.println("Peep");
            }
            return false;
        }

        //The bigdecimals here implement this formula, it does not work with longs due to overflows
        //var py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / (double) denominator;
        //var px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / (double) denominator;

        var bdx1muly2 = BigDecimal.valueOf(x1).multiply(BigDecimal.valueOf(y2));
        var bdy1mulx2 = BigDecimal.valueOf(y1).multiply(BigDecimal.valueOf(x2));
        var bdx3subx4 = BigDecimal.valueOf(x3).subtract(BigDecimal.valueOf(x4));

        var bdx1subx2 = BigDecimal.valueOf(x1).subtract(BigDecimal.valueOf(x2));
        var bdx3muly4 = BigDecimal.valueOf(x3).multiply(BigDecimal.valueOf(y4));
        var bdy3mulx4 = BigDecimal.valueOf(y3).multiply(BigDecimal.valueOf(x4));

        var bdy3suby4 = BigDecimal.valueOf(y3).subtract(BigDecimal.valueOf(y4));
        var bdy1suby2 = BigDecimal.valueOf(y1).subtract(BigDecimal.valueOf(y2));

        var xPart1 = bdx1muly2.subtract(bdy1mulx2).multiply(bdx3subx4);
        var xPart2 = bdx1subx2.multiply(bdx3muly4.subtract(bdy3mulx4));

        var yPart1 = bdx1muly2.subtract(bdy1mulx2).multiply(bdy3suby4);
        var yPart2 = bdy1suby2.multiply(bdx3muly4.subtract(bdy3mulx4));

        var px = xPart1.subtract(xPart2).divide(BigDecimal.valueOf(denominator), new MathContext(20)).doubleValue();
        var py = yPart1.subtract(yPart2).divide(BigDecimal.valueOf(denominator), new MathContext(20)).doubleValue();


        if (isCollisionInPast(px, hailStone1) | isCollisionInPast(px, hailStone2)) {
            return false;
        }

        if (px < minIntersectionPoint || py < minIntersectionPoint ||
            px > maxIntersectionPoint || py > maxIntersectionPoint) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isCollisionInPast(double px, HailStone hailStone) {
        var t = (px - hailStone.x()) / hailStone.vx();
        return t < 0;
    }

    private HailStone mapHailStone(String line) {
        var firstSplit = line.split(" @ ");
        var pointSplit = firstSplit[0].split(", ");
        var velocitySplit = firstSplit[1].split(", ");

        return new HailStone(Long.parseLong(pointSplit[0]),
                             Long.parseLong(pointSplit[1]),
                             Long.parseLong(pointSplit[2]),
                             Long.parseLong(velocitySplit[0]),
                             Long.parseLong(velocitySplit[1]),
                             Long.parseLong(velocitySplit[2]));
    }

    private record HailStone(long x, long y, long z, long vx, long vy, long vz) {}
}
