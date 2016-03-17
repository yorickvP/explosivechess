package ndl_chess;

import java.util.Scanner;

/**
 *
 * @author newdeviceslab_2
 */
public class UserInterface {

    private final boolean humanColor;
    private final boolean engineColor;
    private final Board board;
    private final Player engine;

    public UserInterface(boolean player) {
        humanColor = player;
        engineColor = !player;
        board = new Board();
        engine = new Player(engineColor);
        while(true) {
            playWhite();
            playBlack();
        }
    }

    private void playWhite() {
        board.movePiece(enterMove(true));
        System.out.println(board);
    }

    private void playBlack() {
        board.movePiece(engine.decideMove(board));
        System.out.println(board);
    }

    private Move enterMove(boolean color) {
        Scanner scanner = new Scanner(System.in);
        Move cand;
        do {
            System.out.println("Enter move.");
            String s = scanner.nextLine();
            StringBuilder from = new StringBuilder();
            from.append(s.charAt(0));
            from.append(s.charAt(1));
            StringBuilder to = new StringBuilder();
            to.append(s.charAt(2));
            to.append(s.charAt(3));
            cand = new Move(translateToPoint(from.toString()), translateToPoint(to.toString()));
        } while (!board.legalMove(cand,color));
        return cand;
    }

    private Point translateToPoint(String s) {
        char letter = s.charAt(0);
        int digit = s.charAt(1) - '0';
        return new Point(8 - digit, letter - 'a');
    }

    private String translateToString(Point p) {
        return null;
    }
}
