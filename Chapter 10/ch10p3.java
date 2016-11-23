//********************************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch10p3.java
//This program has the robot move forward until the touch sensor is activated.
//When the sensor is activated the robot will back up, rotate 90 degrees and continue moving.
//********************************************************************************************

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class ch10p3 {
	public static void main(String[] args) {
		// set up differential pilot and nav path controller to use for
		// navigation
		DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f, Motor.A,
				Motor.C);
		pilot.setRotateSpeed(60); // rotate speed set slower to prevent slipping

		// set up touch sensor
		TouchSensor touch = new TouchSensor(SensorPort.S1);

		// wait to begin
		Button.waitForPress();

		// move forward until touch sensor is pressed
		while (!Button.ESCAPE.isPressed()) {
			pilot.forward();

			// if sensor is pressed, stop, rotate 90 degrees then continue
			if (touch.isPressed()) {
				pilot.stop();
				pilot.travel(-10);
				pilot.rotate(90);
			}
		}
	}

}
