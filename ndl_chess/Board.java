package ndl_chess;

import java.util.ArrayList;

/**
 *
 * @author newdeviceslab_2
 */
public class Board {

    private final Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        placePieces();
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean whiteInCheck() {
        Point p = findPiece(new King(true));
        if (pointExists(p.add(new Point(-1, 1))) && getPiece(p.add(new Point(-1, 1))).equals(new Pawn(false))) {
            return true;
        }
        if (pointExists(p.add(new Point(-1, -1))) && getPiece(p.add(new Point(-1, -1))).equals(new Pawn(false))) {
            return true;
        }
        for (Point diff : new Knight(false).getMoves()) {
            if (pointExists(p.add(diff)) && getPiece(p.add(diff)).equals(new Knight(false))) {
                return true;
            }
        }
        ArrayList<Point> cloggedDirections = new ArrayList();
        ArrayList<Point> moves = new Queen(true).getMoves();
        boolean flag = false;
        for (int i = 0; i < moves.size(); i++) {
            for (Point point : new King(true).getMoves()) {
                if (pointExists(p.add(point)) && getPiece(p.add(point)).equals(new King(false))) {
                    return true;
                }
            }
            for (Point point : cloggedDirections) {
                if (moves.get(i).sameDir(point)) {
                    flag = true;
                }
            }
            if (flag) {
                flag = false;
                continue;
            }
            Point newPoint = p.add(moves.get(i));
            if (!pointExists(newPoint)) {
                continue;
            }
            if (getPiece(newPoint).equals(new Empty())) {
                continue;
            }
            if (getPiece(newPoint).color == true) {
                cloggedDirections.add(moves.get(i));
                continue;
            }
            if (getPiece(newPoint).equals(new Queen(false))) {
                return true;
            }
            if (getPiece(newPoint).equals(new Rook(false))) {
                return i % 2 == 0;
            }
            if (getPiece(newPoint).equals(new Bishop(false))) {
                return i % 2 == 1;
            }
        }
        return false;
    }

    private boolean blackInCheck() {
        Point p = findPiece(new King(false));
        if (pointExists(p.add(new Point(1, 1))) && getPiece(p.add(new Point(1, 1))).equals(new Pawn(true))) {
            return true;
        }
        if (pointExists(p.add(new Point(1, -1))) && getPiece(p.add(new Point(1, -1))).equals(new Pawn(true))) {
            return true;
        }
        for (Point diff : new Knight(true).getMoves()) {
            if (pointExists(p.add(diff)) && getPiece(p.add(diff)).equals(new Knight(true))) {
                return true;
            }
        }
        ArrayList<Point> cloggedDirections = new ArrayList();
        ArrayList<Point> moves = new Queen(false).getMoves();
        boolean flag = false;
        for (int i = 0; i < moves.size(); i++) {
            for (Point point : new King(false).getMoves()) {
                if (pointExists(p.add(point)) && getPiece(p.add(point)).equals(new King(true))) {
                    return true;
                }
            }
            for (Point point : cloggedDirections) {
                if (moves.get(i).sameDir(point)) {
                    flag = true;
                }
            }
            if (flag) {
                continue;
            }
            Point newPoint = p.add(moves.get(i));
            if (!pointExists(newPoint)) {
                continue;
            }
            if (getPiece(newPoint).equals(new Empty())) {
                continue;
            }
            if (getPiece(newPoint).color == false) {
                cloggedDirections.add(moves.get(i));
                continue;
            }
            if (getPiece(newPoint).equals(new Queen(true))) {
                return true;
            }
            if (getPiece(newPoint).equals(new Rook(true))) {
                return i % 2 == 0;
            }
            if (getPiece(newPoint).equals(new Bishop(true))) {
                return i % 2 == 1;
            }
        }
        return false;
    }

    /**
     * Checks which moves are legal. Filters the illegal ones for a piece.
     *
     * @param color, the color of the piece
     * @param point, the position of the piece
     * @return the filtered ArrayList
     */
    private ArrayList<Point> filterMoves(boolean color, Point point) {
        Piece piece = getPiece(point);
        if (piece.equals(new Pawn(color))) {
            return filterPawnMoves(color, point);
        } else {
            return filterPieceMoves(color, point);
        }
    }

    private ArrayList<Point> filterPawnMoves(boolean color, Point point) {
        Piece piece = getPiece(point);
        ArrayList<Point> moves = piece.getMoves();
        if (!pointExists(point.add(moves.get(3)))
                || !getPiece(point.add(moves.get(3))).equals(new Empty()) && getPiece(point.add(moves.get(3))).color == color
                || getPiece(point.add(moves.get(3))).equals(new Empty())) {
            moves.remove(3);
        }
        if (!pointExists(point.add(moves.get(2)))
                || !getPiece(point.add(moves.get(2))).equals(new Empty()) && getPiece(point.add(moves.get(2))).color == color
                || getPiece(point.add(moves.get(2))).equals(new Empty())) {
            moves.remove(2);
        }
        if (!getPiece(point.add(moves.get(0))).equals(new Empty())) {
            moves.remove(0);
            moves.remove(0);
        } else if (!getPiece(point.add(moves.get(1))).equals(new Empty()) || piece.isMoved()) {
            moves.remove(1);
        }
        int i = 0;
        while (i < moves.size()) {
            Point newPoint = point.add(moves.get(i));
            movePiece(new Move(point, newPoint));
            if (color == true && whiteInCheck() || color == false && blackInCheck()) {
                moves.remove(i);
                movePiece(new Move(newPoint, point));
                continue;
            }
            movePiece(new Move(newPoint, point));
            i++;
        }
        return moves;
    }

    private ArrayList<Point> filterPieceMoves(boolean color, Point point) {
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
            movePiece(new Move(point, newPoint));
            if (color == true && whiteInCheck() || color == false && blackInCheck()) {
                moves.remove(i);
                movePiece(new Move(newPoint, point));
                continue;
            }
            movePiece(new Move(newPoint, point));
            i++;
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
        ArrayList<Point> moves = filterMoves(color, move.getFrom());
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

    public void movePiece(Move move) {
        Piece piece = getPiece(move.getFrom());
        board[move.getTo().getX()][move.getTo().getY()] = piece;
        board[move.getFrom().getX()][move.getFrom().getY()] = new Empty();
    }

    public ArrayList<Move> getLegalMoves(boolean color) {
        ArrayList<Move> moves = new ArrayList();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Point point = new Point(i, j);
                if (!getPiece(point).equals(new Empty()) && getPiece(point).color == color) {
                    ArrayList<Point> temp = filterMoves(color, new Point(i, j));
                    for (Point to : temp) {
                        moves.add(new Move(point, point.add(to)));
                    }
                }
            }
        }
        return moves;
    }

    public Board copyBoard() {
        Board temp = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp.board[i][j] = board[i][j];
            }
        }
        return temp;
    }
}
