import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.NavPathController;

public class ch13p3 {

	private static data DE;
	private static line LFObj;
	private static object ODObj;
	static double diam = 2.8;
	static double trackwidth = 15.5;
	static DifferentialPilot pilot = new DifferentialPilot(diam / 2.54,
			trackwidth / 2.54, Motor.A, Motor.C);
	static UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
	NavPathController nav = new NavPathController(pilot);// Attaches NavPath

	public static void main(String[] args) {
		DE = new data();
		ODObj = new object(DE, pilot);
		LFObj = new line(DE, pilot);
		ODObj.start();
		LFObj.start();
		while (!Button.ESCAPE.isPressed()) {
		}
		LCD.drawString("Finished", 0, 7);
		LCD.refresh();
	}
}
