//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		FollowGreen.java
//This behavior follows a green line
//*******************************************************

import lejos.nxt.Motor;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowGreen implements Behavior {
	private boolean suppressed;
	// used to store values returned by color sensor
	// follow is color robot is to follow, search is value returned by sensor
	// when searching
	private int follow, search;

	// degrees robot will rotate when searching for line
	int rotation;
	// set up differential pilot and nav path controller to use for navigation
	private DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f,
			Motor.A, Motor.C);

	Sensors sensors;

	public FollowGreen(Sensors globalSensors) {
		sensors = globalSensors;
		suppressed = false;
		pilot.setTravelSpeed(4);
		sensors.colorSense.setFloodlight(false);
		follow = Color.GREEN;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		search = sensors.colorSense.getColorID();

		// move forward while line is green
		while (!suppressed) {
			Thread.yield();
			while (search == follow && !suppressed) {
				pilot.forward();
				search = sensors.colorSense.getColorID();
				Thread.yield();
			}

			// line lost
			while (search != follow && !suppressed) {
				pilot.rotate(rotation); // rotate right
				search = sensors.colorSense.getColorID();
				Thread.yield();
				if (search == follow)
					break; // found line again exit loop
				else {
					pilot.rotate(-rotation * 2); // rotate left back to start
													// then to left position
					search = sensors.colorSense.getColorID();
					Thread.yield();
					if (search == follow)
						break; // found line again exit loop

					pilot.rotate(rotation); // rotate back to center
				}
				rotation += 5; // increase angle of rotation and continue search
				Thread.yield();
			}// end search
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}// end FollowGreen