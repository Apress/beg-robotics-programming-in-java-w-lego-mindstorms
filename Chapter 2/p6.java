//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		p6.java
//Write a program to run the robot forward for some predetermined
//amount of time (say 10000 ms) and measure how far the robot went. 
//***********************************************************************************

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

public class p6 {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);

	public static void main(String[] args) {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();

		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		
		// instantized a stopwatch class for setting up the timer
		Stopwatch watch = new Stopwatch();

		// Begin running both motors
		LEFT_MOTOR.forward();
		RIGHT_MOTOR.forward();

		// Clear the screen
		LCD.clear(); 
		
		// Reset the time on the watch
		watch.reset(); 

		// Display the elapsed time on the LCD until 10000ms
		while (watch.elapsed() < 10000) {
			Thread.yield();
			LCD.drawString("" + watch.elapsed(), 0, 0);
		}

		// Stop the motors after 5 seconds
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();

		buttons.waitForAnyPress();
	}
}
