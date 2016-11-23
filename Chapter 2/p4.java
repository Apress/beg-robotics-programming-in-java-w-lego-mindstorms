//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		p4.java
//interrupting motors using buttons
//***********************************************************************************

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class p4 {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();

		// sound two beeps before starting program
		Sound.twoBeeps();

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// rotate 7200 degree during which the method has returned value all the time
		LEFT_MOTOR.rotate(7200, true);

		// return to true if the motor is always rotating
		while (LEFT_MOTOR.isMoving()) {

			// display and refresh the tachometer reading all the time
			LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 0);

			// determining if there is any button pressed, if yes then stop the motor
			if (buttons.readButtons() > 0)
				LEFT_MOTOR.stop();
		}

		// wait until the motor fully stopped
		while (LEFT_MOTOR.getRotationSpeed() > 0)
			;

		// display the tachometer reading after motor fully stopped
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 1);

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

	}
}
