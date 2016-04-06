/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author newdeviceslab_2
 */
public class Board {

    private final Piece[][] board;
    private Point epLocation;

    public Board() {
        board = new Piece[8][8];
        placePieces();
    }

    public Board(Piece[][] pieces, Point epLocation) {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = pieces[i][j];
            }
        }
        this.epLocation = epLocation;
    }

    /**
     * places all the black and white pieces.
     */
    private void placePieces() {
        placements(true);
        placements(false);
    }

    /**
     * Places all the pieces given their color
     *
     * @param b, the color of the piece.
     */
    private void placements(boolean b) {
        int temp = (b ? 7 : 0);
        for (int i = 0; i < 8; i++) {
            board[b ? 6 : 1][i] = new Pawn(b);
        }
        board[temp][0] = board[temp][7] = new Rook(b);
        board[temp][1] = board[temp][6] = new Knight(b);
        board[temp][2] = board[temp][5] = new Bishop(b);
        board[temp][3] = new Queen(b);
        board[temp][4] = new King(b);
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Empty();
            }
        }
    }

    /**
     * Translates the inputstring to a point, e.g. "a1"=(0,7).
     *
     * @param s, inputstring s
     * @return
     */
    private Point translateToPoint(String s) {
        char letter = s.charAt(0);
        int digit = s.charAt(1) - '0';
        return new Point(8 - digit, letter - 'a');
    }

    private String translateToString(Point p) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" ---------------------------------\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(" | ").append(board[i][j]);
            }
            sb.append(" |\n ---------------------------------\n");
        }
        return sb.toString();
    }

    /**
     * Checks which moves are legal. Filters the illegal ones for a piece.
     *
     * @param color, the color of the piece
     * @param point, the position of the piece
     * @return the filtered ArrayList
     */
    private ArrayList<Point> filterMoves(boolean color, Point point, boolean castles) {
        Piece piece = getPiece(point);
        if (piece.equals(new Pawn(color)) || piece.equals(new Pawn(color, 1))) {
            return filterPawnMoves(color, point);
        } else {
            return filterPieceMoves(color, point, castles);
        }
    }

    private ArrayList<Point> filterPawnMoves(boolean color, Point point) {
        Piece piece = getPiece(point);
        ArrayList<Point> moves = piece.getMoves();
        if (!pointExists(point.add(moves.get(3)))
                || !getPiece(point.add(moves.get(3))).equals(new Empty()) && getPiece(point.add(moves.get(3))).color == color
                || getPiece(point.add(moves.get(3))).equals(new Empty()) && (epLocation == null || !epLocation.equals(point.add(moves.get(3))))) {
            moves.remove(3);
        }
        if (!pointExists(point.add(moves.get(2)))
                || !getPiece(point.add(moves.get(2))).equals(new Empty()) && getPiece(point.add(moves.get(2))).color == color
                || getPiece(point.add(moves.get(2))).equals(new Empty()) && (epLocation == null || !epLocation.equals(point.add(moves.get(2))))) {
            moves.remove(2);
        }
        if (!pointExists(point.add(moves.get(0))) || !getPiece(point.add(moves.get(0))).equals(new Empty())) {
            moves.remove(0);
            moves.remove(0);
        } else if (!pointExists(point.add(moves.get(1))) || !getPiece(point.add(moves.get(1))).equals(new Empty()) || piece.nrOfMoves > 0) {
            moves.remove(1);
        }
        return moves;
    }

    private ArrayList<Point> filterPieceMoves(boolean color, Point point, boolean castles) {
        Piece piece = getPiece(point);
        ArrayList<Point> moves = piece.getMoves();
        ArrayList<Point> cloggedDirections = new ArrayList();
        int i = 0;
        boolean flag = false;
        while (i < moves.size()) {
            Point newPoint = point.add(moves.get(i));
            for (Point p : cloggedDirections) {
                if (piece.equals(new Knight(color))) {
                    break;
                }
                if (moves.get(i).sameDir(p)) {
                    moves.remove(i);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                flag = false;
                continue;
            }
            if (!pointExists(newPoint)) {
                moves.remove(i);
                continue;
            }
            if (!getPiece(newPoint).equals(new Empty())) {
                cloggedDirections.add(moves.get(i));
                if (getPiece(newPoint).color != color) {
                    i++;
                    continue;
                }
            }
            if (!getPiece(newPoint).equals(new Empty()) && getPiece(newPoint).color == color) {
                moves.remove(i);
                continue;
            }
            i++;
        }
        if (!castles) {
            return moves;
        }
        if (color && point.getX() == 7 && point.getY() == 4) {
            if (!getPiece(point).equals(new King(true, 0))) {
                return moves;
            }
            if (getPiece(new Point(7, 7)).equals(new Rook(true, 0)) && getPiece(new Point(7, 6)).equals(new Empty()) && getPiece(new Point(7, 5)).equals(new Empty())) {
                Board newBoard = copyBoard();
                newBoard.movePiece(new Move(point, new Point(7, 5)), false);
                Player check = new Player(true,1,1,1);
                if (!check.inCheck(this)) {
                    moves.add(new Point(0, 2));
                }
            }
            if (getPiece(new Point(7, 7)).equals(new Rook(true, 0)) && getPiece(new Point(7, 3)).equals(new Empty()) && getPiece(new Point(7, 2)).equals(new Empty()) && getPiece(new Point(7, 1)).equals(new Empty())) {
                Board newBoard = copyBoard();
                newBoard.movePiece(new Move(point, new Point(7, 3)), false);
                Player check = new Player(true,1,1,1);
                if (!check.inCheck(this)) {
                    moves.add(new Point(0, -2));
                }
            }
        }
        if (!color && point.getX() == 0 && point.getY() == 4) {
            if (!getPiece(point).equals(new King(false, 0))) {
                return moves;
            }
            if (getPiece(new Point(0, 7)).equals(new Rook(false, 0)) && getPiece(new Point(0, 6)).equals(new Empty()) && getPiece(new Point(0, 5)).equals(new Empty())) {
                Board newBoard = copyBoard();
                newBoard.movePiece(new Move(point, new Point(0, 5)), false);
                Player check = new Player(false,1,1,1);
                if (!check.inCheck(this)) {
                    moves.add(new Point(0, 2));
                }
            }
            if (getPiece(new Point(0, 7)).equals(new Rook(false, 0)) && getPiece(new Point(0, 3)).equals(new Empty()) && getPiece(new Point(0, 2)).equals(new Empty()) && getPiece(new Point(0, 1)).equals(new Empty())) {
                Board newBoard = copyBoard();
                newBoard.movePiece(new Move(point, new Point(0, 3)), false);
                Player check = new Player(false,1,1,1);
                if (!check.inCheck(this)) {
                    moves.add(new Point(0, -2));
                }
            }
        }
        return moves;
    }

    public boolean legalMove(Move move, boolean color) {
        if (getPiece(move.getFrom()).equals(new Empty())) {
            return false;
        }
        if (getPiece(move.getFrom()).color != color) {
            return false;
        }
        ArrayList<Point> moves = filterMoves(color, move.getFrom(), true);
        Point diff = move.getTo().subtract(move.getFrom());
        for (Point p : moves) {
            if (p.getX() == diff.getX() && p.getY() == diff.getY()) {
                return true;
            }
        }
        return false;
    }

    public Piece getPiece(Point p) {
        return board[p.getX()][p.getY()];
    }

    public void setPiece(Point point, Piece p) {
        board[point.getX()][point.getY()] = p;
    }

    /**
     * Checks if a point is legal on this board
     *
     * @param p, the point
     * @return true if it is legal
     */
    private boolean pointExists(Point p) {
        return !(p.getX() < 0 || p.getX() > 7 || p.getY() < 0 || p.getY() > 7);
    }

    public Point findPiece(Piece p) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getPiece(new Point(i, j)).equals(p)) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public void movePiece(Move move, boolean trueMove) {
        Piece piece = getPiece(move.getFrom());
        if (getPiece(move.getTo()).equals(new Empty()) && (piece.equals(new Pawn(true, 1)) || piece.equals(new Pawn(false, 1))) && move.getTo().getY() != move.getFrom().getY()) {
            board[move.getFrom().getX()][move.getTo().getY()] = new Empty();
        }
        if (piece.equals(new Pawn(true)) && move.getFrom().getY() == move.getTo().getY()) {
            epLocation = new Point(move.getFrom().getX() - 1, move.getFrom().getY());
        } else if (piece.equals(new Pawn(false)) && move.getFrom().getY() == move.getTo().getY()) {
            epLocation = new Point(move.getFrom().getX() + 1, move.getFrom().getY());
        }
        if (!piece.equals(new Pawn(true)) && !piece.equals(new Pawn(false))) {
            epLocation = null;
        }
        if ((piece.equals(new King(true)) || piece.equals(new King(false))) && move.getTo().getY() - move.getFrom().getY() == 2) {
            piece.nrOfMoves++;
            board[move.getTo().getX()][move.getTo().getY()] = piece;
            board[move.getFrom().getX()][move.getFrom().getY()] = new Empty();
            board[move.getTo().getX()][move.getTo().getY() - 1] = getPiece(move.getFrom().add(new Point(0, 3)));
            board[move.getFrom().getX()][move.getFrom().getY() + 3] = new Empty();
        } else if ((piece.equals(new King(true)) || piece.equals(new King(false))) && move.getTo().getY() - move.getFrom().getY() == -2) {
            piece.nrOfMoves++;
            board[move.getTo().getX()][move.getTo().getY()] = piece;
            board[move.getFrom().getX()][move.getFrom().getY()] = new Empty();
            board[move.getTo().getX()][move.getTo().getY() + 1] = getPiece(move.getFrom().add(new Point(0, 3)));
            board[move.getFrom().getX()][move.getFrom().getY() - 4] = new Empty();
        } else {
            piece.nrOfMoves++;
            if (trueMove) {
                if (piece.equals(new Pawn(true, 1)) && move.getTo().getX() == 0) {
                    Scanner sc = new Scanner(System.in);
                    char promPiece;
                    Piece newPiece = new Piece();
                    boolean flag = false;
                    do {
                        System.out.println("Promote to? (Q/R/B/N)");
                        String input = sc.nextLine();
                        promPiece = input.charAt(0);
                        switch (promPiece) {
                            case 'Q':
                                newPiece = new Queen(true);
                                break;
                            case 'R':
                                newPiece = new Rook(true);
                                break;
                            case 'B':
                                newPiece = new Bishop(true);
                                break;
                            case 'N':
                                newPiece = new Knight(true);
                                break;
                            default:
                                flag = true;
                        }
                    } while (!flag);
                    piece = newPiece;
                }
                if (piece.equals(new Pawn(false, 1)) && move.getTo().getX() == 7) {
                    Scanner sc = new Scanner(System.in);
                    char promPiece;
                    Piece newPiece = new Piece();
                    boolean flag = false;
                    do {
                        System.out.println("Promote to? (Q/R/B/N)");
                        String input = sc.nextLine();
                        promPiece = input.charAt(0);
                        switch (promPiece) {
                            case 'Q':
                                newPiece = new Queen(true);
                                break;
                            case 'R':
                                newPiece = new Rook(true);
                                break;
                            case 'B':
                                newPiece = new Bishop(true);
                                break;
                            case 'N':
                                newPiece = new Knight(true);
                                break;
                            default:
                                flag = true;
                        }
                    } while (!flag);
                    piece = newPiece;
                }
            }
            board[move.getTo().getX()][move.getTo().getY()] = piece;
            board[move.getFrom().getX()][move.getFrom().getY()] = new Empty();
        }
    }

    public ArrayList<Move> getLegalMoves(boolean color, boolean castles) {
        ArrayList<Move> moves = new ArrayList();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Point point = new Point(i, j);
                if (!getPiece(point).equals(new Empty()) && getPiece(point).color == color) {
                    ArrayList<Point> temp = filterMoves(color, new Point(i, j), castles);
                    for (Point to : temp) {
                        moves.add(new Move(point, point.add(to)));
                    }
                }
            }
        }
        return moves;
    }

    public int getMovedPieces(boolean color) {
        int ans = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].equals(new Empty()) && board[i][j].nrOfMoves > 0) {
                    if (board[i][j].getColor() == color) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    public Board copyBoard() {
        Piece[][] temp = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[i][j] = board[i][j].copyPiece();
            }
        }
        return new Board(temp, epLocation);
    }
}
