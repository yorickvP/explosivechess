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
public class Knight extends Piece {
    
    
    public Knight(boolean c) {
        color = c;
        nrOfMoves = 0;
        moves = new ArrayList<>();
        moves.add(new Point(1,2));
        moves.add(new Point(1,-2));
        moves.add(new Point(-1,2));
        moves.add(new Point(-1,-2));
        moves.add(new Point(2,1));
        moves.add(new Point(2,-1));
        moves.add(new Point(-2,1));
        moves.add(new Point(-2,-1));
    }
    
    
    @Override
    public String toString() {
        if(color)
            return "N";
        return "n";
    }
    
    @Override
    public Knight copyPiece() {
        return new Knight(color);
    }
    
}
