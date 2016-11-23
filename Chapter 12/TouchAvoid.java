//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		TouchAvoid.java
//This class represents a robotic behavior when the touch sensor
//is activated
//****************************************************************
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TouchAvoid implements Behavior {
	// set up differential pilot
	private DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f,
			Motor.A, Motor.C);
	private boolean suppressed;
	private int follow, search;
	Sensors sensors;

	public TouchAvoid(Sensors globalSensors) {
		pilot.setTravelSpeed(4);
		suppressed = false;
		follow = Color.WHITE; // color to follow when relocating line
		sensors = globalSensors;
	}

	@Override
	public boolean takeControl() {
		return sensors.touch.isPressed(); // behavior takes control when touch
											// sensor is pressed
	}

	@Override
	public void action() // avoid the object
	{
		LCD.drawString("BUTTON IS PRESSED", 0, 0);
		sensors.touchPressed = true; // set flag in sensor class so robot knows
										// to follow different color later
		suppressed = false;

		// moves to avoid object
		pilot.stop();
		pilot.travel(-15);
		// execute 90 degree turn to navigate around object
		pilot.rotate(90);
		pilot.travel(10);
		pilot.rotate(-90);
		pilot.travel(30);
		pilot.rotate(-90);

		search = sensors.colorSense.getColorID();

		// find line again
		while (search != follow && !suppressed) {
			// LCD.clear();
			pilot.forward();
			search = sensors.colorSense.getColorID();
			LCD.drawString("search: " + Integer.toString(search), 0, 0);
			LCD.drawString("follow: " + follow, 0, 1);

			// line found, rotate until following again
			if (search == follow) {
				pilot.rotate(90);
				suppressed = true;
				break;
			}
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
