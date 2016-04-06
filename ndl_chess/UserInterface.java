/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

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
        engine = new Player(engineColor, 50, 5, 1); //parameters voor evaluatie-algoritme
        while (true) {
            playWhite();
            playBlack();
        }
    }

    private void playWhite() {
        if (humanColor) {
            board.movePiece(enterMove(humanColor), true);
        } else {
            Move move = engine.decideMove(board);
            System.out.println(move); //output van engine zit in de toString() van klasse Move
            board.movePiece(move, true);
        }
        System.out.println(board); //voor menselijke gebruiker van dit programma: overbodig voor ndl
    }

    private void playBlack() {
        if (!humanColor) {
            board.movePiece(enterMove(humanColor), true);
        } else {
            Move move = engine.decideMove(board);
            System.out.println(move);
            board.movePiece(move, true);
        }
        System.out.println(board);
    }
    /**
     * input van gewenste zet wordt hier geformatteerd;
     * nu in format [startveld][eindveld], bij rokade start- en eindveld van de koning
     * @param color
     * @return 
     */
    private Move enterMove(boolean color) {
        Scanner scanner = new Scanner(System.in);
        Move cand;
        do {
            String s = scanner.nextLine();
            StringBuilder from = new StringBuilder();
            from.append(s.charAt(0));
            from.append(s.charAt(1));
            StringBuilder to = new StringBuilder();
            to.append(s.charAt(2));
            to.append(s.charAt(3));
            cand = new Move(translateToPoint(from.toString()), translateToPoint(to.toString()));
        } while (!board.legalMove(cand, color));
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
