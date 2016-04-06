/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
public class Piece {

    /**
     *
     */
    protected ArrayList<Point> moves;
    protected boolean color;
    protected int nrOfMoves;

    public boolean getColor() {
        return color;
    }

    public ArrayList<Point> getMoves() {
        ArrayList<Point> candMoves = new ArrayList<>();
        for (Point move : moves) {
            candMoves.add(new Point(move.getX(), move.getY()));
        }
        return candMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece p = (Piece) o;
        return color == p.color;
    }
    
    public Piece copyPiece() {
        return null;
    }
}
