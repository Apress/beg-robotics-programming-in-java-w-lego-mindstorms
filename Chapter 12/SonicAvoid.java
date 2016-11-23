//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		SonicAvoid.java
//This behavior avoids objects using the ultra sonic sensor
//**********************************************************
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class SonicAvoid implements Behavior {
	// set up differential pilot
	private DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f,
			Motor.A, Motor.C);
	private boolean suppressed;
	private int follow, search;
	Sensors sensors;

	public SonicAvoid(Sensors globalSensors) {
		pilot.setTravelSpeed(4);
		suppressed = false;
		follow = Color.WHITE;
		sensors = globalSensors;
	}

	// take control when within 15 cm of an object and on a blue line
	@Override
	public boolean takeControl() {
		return (sensors.sonic.getDistance() < 15 && sensors.colorSense
				.getColorID() == Color.WHITE);
	}

	@Override
	public void action() {
		LCD.drawString("Sonic triggered", 0, 0);
		suppressed = false;

		// stop and move around object
		pilot.stop();
		// execute 90 degree turn to navigate around object
		pilot.rotate(90);
		pilot.travel(10);
		pilot.rotate(-90);
		pilot.travel(30);
		pilot.rotate(-90);

		search = sensors.colorSense.getColorID();

		// find line again
		while (search != follow && !suppressed) {
			LCD.drawString("HERE I AM", 0, 0);
			pilot.forward();
			search = sensors.colorSense.getColorID();

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
