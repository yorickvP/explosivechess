import lejos.nxt.Button;
import lejos.nxt.Motor;

public class ExplosiveChess {
  public static void main (String[] args) {
    System.out.println("Hello World");
    Motor.B.setSpeed(600);
    Motor.C.setSpeed(350);
    Motor.B.rotate(2800, true);
    Motor.C.rotate(1200, true);
    Button.waitForAnyPress();
  }
}
