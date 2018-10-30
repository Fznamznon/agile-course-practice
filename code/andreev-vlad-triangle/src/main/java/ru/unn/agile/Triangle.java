package ru.unn.agile;

import ru.unn.agile.primitives.Line;
import ru.unn.agile.primitives.LineImpl;
import ru.unn.agile.primitives.Point;

import static java.lang.Math.*;

public class Triangle {
    private Line lineAB;
    private Line lineBC;
    private Line lineCA;

    public Triangle(final Point pointA,
                    final Point pointB,
                    final Point pointC) {
        if (!validate(pointA, pointB, pointC)) {
            throw new IllegalArgumentException();
        }
        this.lineAB = new LineImpl(pointA, pointB);
        this.lineBC = new LineImpl(pointB, pointC);
        this.lineCA = new LineImpl(pointC, pointA);
    }

    protected static boolean validate(final Point pointA,
                                      final Point pointB,
                                      final Point pointC) {
        if (pointA == null
                || pointB == null
                || pointC == null) {
            return false;
        }
        return ((pointC.getX() - pointA.getX()) * (pointB.getY() - pointA.getY())
                != (pointC.getY() - pointA.getY()) * (pointB.getX() - pointA.getX()));
    }

    protected double angleBetweenTwoLines(final Line line1,
                                          final Line line2,
                                          final Line oppositeLine) {
        return acos(
                (pow(line1.getLength(), 2)
                        + pow(line2.getLength(), 2)
                        - pow(oppositeLine.getLength(), 2))
                        / (2 * line1.getLength() * line2.getLength())
        );
    }

    public double getLengthA() {
        return lineAB.getLength();
    }

    public double getLengthB() {
        return lineBC.getLength();
    }

    public double getLengthC() {
        return lineCA.getLength();
    }

    public double getAngleAB() {
        return angleBetweenTwoLines(lineAB, lineBC, lineCA);
    }

    public double getAngleBC() {
        return angleBetweenTwoLines(lineBC, lineCA, lineAB);
    }

    public double getAngleCA() {
        return angleBetweenTwoLines(lineCA, lineAB, lineBC);
    }

    public double getPerimeter() {
        return lineAB.getLength() + lineBC.getLength() + lineCA.getLength();
    }

    public double getSquare() {
        return (lineAB.getLength() * lineBC.getLength() * sin(getAngleAB())) / 2;
    }
    @Override
    public String toString() {
        return "Triangele{"
                + "lineAB=" + lineAB
                + ", lineBC=" + lineBC
                + ", lineCA=" + lineCA
                + '}';
    }
}
