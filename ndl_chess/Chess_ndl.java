/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ndl;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Chess_ndl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board b = new Board();
        Scanner sc = new Scanner(System.in);
        boolean humanColor = true;
        char ans = 0;
        do {
            System.out.println("Play White or Black? (W/B)");
            ans = sc.nextLine().charAt(0);
            if (ans == 'B') {
                humanColor = false;
            }
        } while (ans != 'B' && ans != 'W');
        UserInterface ui = new UserInterface(humanColor);
    }

}
