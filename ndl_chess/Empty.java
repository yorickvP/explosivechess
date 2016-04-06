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
public class Empty extends Piece {

    public Empty() {
        color = true;
        nrOfMoves = 0;
        moves = new ArrayList<Point>();
    }
    
    @Override
    public String toString() {
        return " ";
    }
    
    @Override
    public Empty copyPiece() {
        return new Empty();
    }
}
