/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

import java.util.ArrayList;

/**
 * The computer that is playing will be documented here. This will decide the
 * next move.
 *
 * @author new_devices_lab_4
 */
public class Player {

    private boolean color;
    private final int parm1;
    private final int parm2;
    private final int parm3;

    public Player(boolean c, int parm1, int parm2, int parm3) {
        color = c;
        this.parm1 = parm1;
        this.parm2 = parm2;
        this.parm3 = parm3;
    }

    public Move decideMove(Board board) {
        if (inCheckmate(board)) {
            System.out.println((color ? "White" : "Black") + " checkmated. " + (color ? "Black" : "White") + " wins!");
        } else {
            return playBestMove(3, board);
        }
        return null;
    }

    public boolean inCheck(Board board) {
        if (color) {
            color = false;
            double eval = findBestMove(1, board, false);
            color = true;
            return eval < -800;
        }
        color = true;
        double eval = findBestMove(1, board, false);
        color = false;
        return eval > 800;
    }

    private boolean inCheckmate(Board board) {
        if (!inCheck(board)) {
            return false;
        }
        if (color) {
            return findBestMove(2, board, false) < -800;
        }
        return findBestMove(2, board, false) > 800;
    }

    private Move playBestMove(int plyCount, Board board) {
        ArrayList<Move> moves = board.getLegalMoves(color, true);
        Move bestMove = moves.get(0);
        double best = (color ? -1000 : 1000);
        for (Move move : moves) {
            Board newBoard = board.copyBoard();
            newBoard.movePiece(move, false);
            if (inCheck(newBoard)) {
                continue;
            }
            color = !color;
            double temp = findBestMove(plyCount - 1, newBoard, true);
            color = !color;
            if (color) {
                if (temp > best) {
                    best = temp;
                    bestMove = new Move(move.getFrom(), move.getTo());
                }
            } else if (temp < best) {
                best = temp;
                bestMove = new Move(move.getFrom(), move.getTo());
            }
        }
        return bestMove;
    }

    private double evaluate(Board board) {
        int freedom = board.getLegalMoves(true, false).size() - board.getLegalMoves(false, false).size();
        int moveBalance = board.getMovedPieces(true) - board.getMovedPieces(false);
        int earlyQueenMoves = (earlyQueen(board, true) ? 1 : 0) - (earlyQueen(board, false) ? 1 : 0);
        return materialCount(board, true) - materialCount(board, false) + (double) freedom / parm1 + (double) moveBalance / parm2 + (kingSafety(board, true) - kingSafety(board, false)) / parm3 - (double) earlyQueenMoves;
    }

    private boolean earlyQueen(Board board, boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Point(i, j));
                if (piece.equals(new Queen(color))) {
                    return piece.nrOfMoves > 0 && board.getMovedPieces(color) < 6;
                }
            }
        }
        return false;
    }

    private int materialCount(Board board, boolean color) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPiece(new Point(i, j));
                if (!p.equals(new Empty())) {
                    if ((p.equals(new Pawn(true)) || p.equals(new Pawn(true, 1))) && color) {
                        count++;
                    } else if ((p.equals(new Pawn(false)) || p.equals(new Pawn(false, 1))) && !color) {
                        count++;
                    } else if ((p.equals(new Knight(true)) || p.equals(new Bishop(true))) && color) {
                        count += 3;
                    } else if ((p.equals(new Knight(false)) || p.equals(new Bishop(false))) && !color) {
                        count += 3;
                    } else if ((p.equals(new Rook(true)) || p.equals(new Rook(true, 1))) && color) {
                        count += 5;
                    } else if ((p.equals(new Rook(false)) || p.equals(new Rook(false, 1))) && !color) {
                        count += 5;
                    } else if (p.equals(new Queen(true)) && color) {
                        count += 9;
                    } else if (p.equals(new Queen(false)) && !color) {
                        count += 9;
                    } else if ((p.equals(new King(true)) || p.equals(new King(true, 1))) && color) {
                        count += 1000;
                    } else if ((p.equals(new King(false)) || p.equals(new King(false, 1))) && !color) {
                        count += 1000;
                    }

                }
            }
        }
        return count;
    }

    private double kingSafety(Board board, boolean color) {
        if (materialCount(board, !color) <= 1015) {
            return 1.0;
        }
        return 0;
    }

    public double findBestMove(int plyCount, Board board, boolean castles) {
        if (plyCount == 0) {
            return evaluate(board);
        }
        ArrayList<Move> moves = board.getLegalMoves(color, castles);
        double best = (color ? -1000 : 1000);
        for (Move move : moves) {
            Board newBoard = board.copyBoard();
            newBoard.movePiece(move, false);
            color = !color;
            double temp = findBestMove(plyCount - 1, newBoard, castles);
            color = !color;
            if (color) {
                if (temp > best) {
                    best = temp;
                }
            } else if (temp < best) {
                best = temp;
            }
        }
        return best;
    }
}
