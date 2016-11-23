//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		example5.java
//an example for tracing out a square using DifferentialPilot in LeJOS NXJ
//***********************************************************************************

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Stopwatch;

public class example5 {

	// The vehicle's rate of travel forward in centimeters per second
	private static final double FW_SPEED = 30.666666667f;

	// The vehicle's clockwise rotation speed in degrees per second
	private static final double ROTATION_SPEED = 248.297752809f;

	// The vehicle's wheel-width.
	private static final double WHEEL_DIAMETER = 3.1;

	// The vehicle's wheel-bas
	private static final double TRACK_WIDTH = 17.5;

	// Reference to left motor
	private static NXTRegulatedMotor LEFT_MOTOR = Motor.A;

	// Reference to right motor
	private static NXTRegulatedMotor RIGHT_MOTOR = Motor.C;

	/**
	 * The main entry point for the program.
	 */
	public static void main(String[] args) {

		// construct the pilot using the static variables
		DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER,
				TRACK_WIDTH, LEFT_MOTOR, RIGHT_MOTOR, false);

		// tracing out a square
		drawSquare(pilot, 100);

		LCD.drawString("tracing is done, press button to exit.", 0, 0);
		Button.waitForPress();
	}

	/**
	 * Draws a square of the specified side length
	 * 
	 * @param pilot
	 *            The pilot to use when drawing the square
	 * @param sideLength
	 *            The length of each leg of the square
	 */
	public static void drawSquare(DifferentialPilot pilot, double sideLength) {

		for (int i = 0; i < 4; i++) {

			// delay 500ms to allow the motors to stabilize
			sleep(500);

			// move the vehicle forward
			forward(pilot, sideLength);

			// delay 500ms to allow the motors to stabilize
			sleep(500);

			// rotate the vehicle by 90 degrees to prepare for
			// the next side of the square
			rotate(pilot, 90);
		}
	}

	/**
	 * Move the vehicle forward the specified distance
	 * 
	 * @param pilot
	 *            The pilot to use for moving forward
	 * @param distance
	 *            The distance to travel
	 */
	public static void forward(DifferentialPilot pilot, double distance) {
		// get the number of milliseconds the vehicle should travel based on the
		// vehicle's speed
		int travelTime = getMillisForTravel(distance);

		// begin the pilot forward
		pilot.forward();

		// wait for the traveling to finish
		sleep(travelTime);

		// reached the destination - stop
		pilot.stop();
	}

	/**
	 * Rotate the vehicle by the specified angle. Positive angles will result in
	 * a clockwise rotation. Negative angles will result in a counterclockwise
	 * rotation.
	 * 
	 * @param pilot
	 * @param angle
	 *            The angle (in degrees) by which to rotate the vehicle
	 */
	public static void rotate(DifferentialPilot pilot, double angle) {

		// determine the number of milliseconds to rotate based on the vehicle
		// speed
		int travelTime = getMillisForRotate(angle);

		// for negative angles, rotate counterclockwise
		if (angle < 0)
			pilot.rotateLeft();
		else
			// for positive angles, rotate clockwise
			pilot.rotateRight();

		// block the thread until the motion is complete
		sleep(travelTime);
		pilot.stop();
	}

	/**
	 * Gets the milliseconds to travel for a given travel distance
	 * 
	 * @param distance
	 *            The distance in centimeters to travel
	 * @return Returns the number of milliseconds to travel
	 */
	public static int getMillisForTravel(double distance) {
		return (int) ((distance / FW_SPEED) * 1000);
	}

	/**
	 * Gets the milliseconds to rotate for the specified number of degrees
	 * 
	 * @param rotateDegree
	 * @return the number of milliseconds to rotate
	 */
	public static int getMillisForRotate(double rotateDegree) {
		return (int) ((rotateDegree / ROTATION_SPEED) * 1000);
	}

	/**
	 * Sleep function using Thread.yield rather than thread.sleep
	 * 
	 * @param millis
	 *            The number of milliseconds to block the executing thread
	 */
	public static void sleep(long millis) {

		// create the stopwatch
		Stopwatch sw = new Stopwatch();

		// continue waiting while the elapsed time is less than the time
		// specified
		while (sw.elapsed() < millis)
			Thread.yield();
	}
}
