/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

/**
 *
 * @author newdeviceslab_2
 */
public class Point {

    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point subtract(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "" + (char) (y + 'a') + (8 - x);
    }

    public Point times(int i) {
        return new Point(x * i, y * i);
    }

    public boolean sameDir(Point p) {
        return sign(x) == sign(p.getX()) && sign(y) == sign(p.getY());
    }

    private int sign(int x) {
        if (x > 0) {
            return 1;
        }
        if (x < 0) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass())
            return false;
        Point point = (Point) o;
        return point.getX() == getX() && point.getY() == getY();
    }

}
