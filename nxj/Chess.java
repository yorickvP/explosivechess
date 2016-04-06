import lejos.nxt.Sound;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import java.util.Scanner;


public class Chess {

	public static final int RANGE_X = 1550;
	public static final int RANGE_Y = 1530;

	public int x_nul;
	public int y_nul;

	public Chess() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Position: " + Motor.A.getTachoCount() + "," + Motor.B.getTachoCount());
		x_nul = Motor.B.getTachoCount();
		y_nul = Motor.A.getTachoCount();

		//Motor.B.smoothAcceleration(true);
		Motor.B.setSpeed(350);
		Motor.A.setSpeed(350);
		while(true) {
			System.out.println("Posx:");
			int x = sc.nextInt();
			System.out.println("Posy:");
			int y = sc.nextInt();
			moveTo(x, y);
			System.out.println("Done");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Chess();
	}

	public void moveTo(int x, int y) {
		Motor.B.rotateTo(x_nul + (-x * RANGE_X) / 70, true);
		Motor.A.rotateTo(y_nul + (-y * RANGE_Y) / 70, false);
		do
		try { Thread.sleep(100); }
		catch(InterruptedException e) {}
		while(Motor.B.isMoving());
	}

}
