//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		p2.java
//Motor inertia test
//***********************************************************************************

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class p2 {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// set motor to move 720 degrees per second
		LEFT_MOTOR.setSpeed(720);

		// start forward movement
		LEFT_MOTOR.forward();

		// a counter to count the number of degrees rotated 
		int count = 0;

		// continue moving until motor has rotated 720 degrees
		while (count < 720)
			count = LEFT_MOTOR.getTachoCount();

		// stop the motor
		LEFT_MOTOR.stop();

		// display the tachometer reading
		LCD.drawString("Tacho Read: " + count, 0, 0);

		// wait for motor to actually stop and display tacho count.
		// this number will be higher than previous due to motor inertia
		while (LEFT_MOTOR.getRotationSpeed() > 0);
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 1);

		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		
		LCD.clear();
	}
}
