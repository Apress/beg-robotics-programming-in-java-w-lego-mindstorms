//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch11p4.java
//Program that allows your robot to follow a line.  It uses the ultrasonic
//sensor to detect if an object is in its path while following the line.
//If an object is detected the robot will leave the line and travel around
//the object.  It will then search for the line and continue traveling.
//***************************************************************************
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class ch11p4 {

	public static void main(String[] args) {
		// set up differential pilot and nav path controller to use for
		// navigation
		DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f, Motor.A,
				Motor.C);
		pilot.setTravelSpeed(5);

		UltrasonicSensor ultra = new UltrasonicSensor(SensorPort.S4);

		// set up color sensor
		ColorSensor colorSense = new ColorSensor(SensorPort.S1);
		colorSense.setFloodlight(false);

		// used to store values returned by color sensor
		// follow is color robot is to follow, search is value returned by
		// sensor when searching
		int follow, search;

		// degrees robot will rotate when searching for line
		int rotation;

		// calibrate sensor
		LCD.drawString("Place color sensor\nabove color to follow", 0, 0);
		Button.waitForPress();
		follow = colorSense.getColorID();

		// place robot on start and wait for button press to begin main loop
		LCD.clear();
		LCD.drawString("Place robot", 0, 0);
		Button.waitForPress();

		// main loop
		// follow line. if line is lost turn left and right to search for it
		while (!Button.ESCAPE.isPressed()) {
			rotation = 5;
			search = colorSense.getColorID(); // make sure we are still on the
												// line

			// line is found continue forward
			while (search == follow) {
				// object detected
				if (ultra.getDistance() <= 10) {
					// execute 90 degree turn to navigate around object
					pilot.rotate(90);
					pilot.travel(10);
					pilot.rotate(-90);
					pilot.travel(30);
					pilot.rotate(-90);
					search = colorSense.getColorID();

					// find line again
					while (search != follow) {
						pilot.forward();
						search = colorSense.getColorID();

						// line found, rotate until following again
						if (search == follow) {
							pilot.rotate(90);
							break;
						}
					}
				}

				// continue following line
				pilot.forward();
				search = colorSense.getColorID();
			}

			// line lost
			while (search != follow) {
				pilot.rotate(rotation); // rotate right
				search = colorSense.getColorID();
				if (search == follow)
					break; // found line again exit loop
				else {
					pilot.rotate(-rotation * 2); // rotate left back to start
													// then to left position
					search = colorSense.getColorID();
					if (search == follow)
						break; // found line again exit loop

					pilot.rotate(rotation); // rotate back to center
				}
				rotation += 5; // increase angle of rotation and continue search
			}// end search
		}// end main loop
	}// end main()
}
