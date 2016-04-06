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
public class Move {

    private Point from;
    private Point to;

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getTo() {
        return to;
    }

    public Point getFrom() {
        return from;
    }
    
    @Override
    public String toString() {
        return from.toString() + to; //via toString() van Point
    }
    
    public Move reverseMove() {
        return new Move (to, from);
    }

}
