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
class Queen extends Piece {

    public Queen(boolean b) {
        color = b;
        nrOfMoves = 0;
        moves = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            moves.add(new Point(0, i));
            moves.add(new Point(i, i));
            moves.add(new Point(i, 0));
            moves.add(new Point(i, -1*i));
            moves.add(new Point(0, -1*i));
            moves.add(new Point(-1*i, -1*i));
            moves.add(new Point(-1*i, 0));
            moves.add(new Point(-1*i, i));
        }
    }

    @Override
    public String toString() {
        if(color)
            return "Q";
        return "q";
    }
    
    @Override
    public Queen copyPiece() {
        return new Queen(color);
    }

}
