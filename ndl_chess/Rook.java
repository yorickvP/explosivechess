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
class Rook extends Piece {


    public Rook(boolean c) {
        color = c;
        nrOfMoves = 0;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(0, i));
            moves.add(new Point(i, 0));
            moves.add(new Point(0, -1 * i));
            moves.add(new Point(-1 * i, 0));
        }
    }
    
    public Rook(boolean c, int nrOfMoves) {
        color = c;
        this.nrOfMoves = nrOfMoves;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(0, i));
            moves.add(new Point(i, 0));
            moves.add(new Point(0, -1 * i));
            moves.add(new Point(-1 * i, 0));
        }
    }

    @Override
    public String toString() {
        if(color)
            return "R";
        return "r";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rook p = (Rook) o;
        return color == p.color && nrOfMoves > 0 == p.nrOfMoves > 0;
    }

    @Override
    public Rook copyPiece() {
        return new Rook(color, nrOfMoves);
    }
}
