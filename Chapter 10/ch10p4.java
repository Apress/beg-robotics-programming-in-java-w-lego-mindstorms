//********************************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch10p4.java
//This program will move the robot forward until the ultrasonic sensor detects an
//object within 25cm.  When the object is detected the robot will back up to 30cm, 
//rotate 90 degrees, then continue moving.
//********************************************************************************************

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class ch10p4 {

	public static void main(String[] args) {

		int distance;

		// set up differential pilot and nav path controller to use for
		// navigation
		DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f, Motor.A,
				Motor.C);

		// rotate speed set slower to prevent slipping
		pilot.setRotateSpeed(180);

		// set up ultrasonic sensor
		UltrasonicSensor ultraSonic = new UltrasonicSensor(SensorPort.S1);

		// wait to begin
		Button.waitForPress();

		// move forward until distance from object is 25 cm
		while (!Button.ESCAPE.isPressed()) {
			distance = ultraSonic.getDistance();
			while (distance > 25) {
				pilot.forward();
				distance = ultraSonic.getDistance();
			}

			// if object is closer than 25 cm backup to 30 cm
			if (distance <= 25) {
				while (distance <= 30) {
					pilot.backward();
					distance = ultraSonic.getDistance();
				}

				// rotate 90 degrees and continue loop
				pilot.rotate(90);
			}
		}
	}
}
