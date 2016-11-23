//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch11p2.java
//an example for color sensor testing
//***********************************************************************************

import java.util.ArrayList;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.robotics.Color;
import lejos.util.Stopwatch;
import sensors.ColorStruct;
import sensors.SensorControl;
import sensors.UnsupportedSensorException;

/**
 * Demonstrates the use of color sensors
 */

public class ch11p2 {
	/** The port to use for color sensing */
	private static SensorPort _colorPort = SensorPort.S1;

	/** Stores the number of characters that fit on one line of the LCD screen. */
	private static int LCD_DISP_WIDTH = 16;

	public static void main(String[] args) {
		try {
			runColorSenseDemo();
		} catch (UnsupportedOperationException e) {
			handleError(e, true);
		} catch (Throwable e) {
			handleError(e, false);
		}
	}

	/**
	 * Demonstrate color sensing
	 * 
	 * @throws UnsupportedSensorException
	 *             Thrown if _colorPort does not specify a valid color sending
	 *             port
	 */
	private static void runColorSenseDemo() throws UnsupportedSensorException {
		LCD.drawString("Press to begin...", 0, 0);
		Button.waitForPress();
		SensorControl sControl = new SensorControl(null, null, _colorPort, null);
		while (!Button.ESCAPE.isPressed()) {
			ColorStruct cvalue = sControl.getSensedColor();
			Color cv = sControl.getRGBColor();
			LCD.clear();
			LCD.drawString("Color: " + cvalue, 0, 0);
			LCD.drawString("Sensed:", 0, 1);
			LCD.drawString(
					cv.getRed() + " " + cv.getGreen() + " " + cv.getBlue(), 0,
					2);

		}
	}

	/**
	 * Prints an error on the LCD screen so that it does not run off the screen,
	 * then waits for escape to be pressed to continue.
	 * 
	 * @param message
	 *            The message to display on the LCD screen.
	 * @param expected
	 *            Flag indicating if the exception was expected or caught as
	 *            part of a blanket-catch.
	 */
	private static void handleError(Throwable ex, boolean expected) {
		String message;
		if (expected) {
			message = "ERROR: " + ex.getClass().toString() + ex.getMessage();
		} else {
			message = "UNEXPECTED ERR: " + ex.getClass().toString()
					+ ex.getMessage();
		}

		LCD.clear();
		ArrayList<String> messageSplit = new ArrayList<String>();

		// split the message in 16-character segments
		while (message.length() > LCD_DISP_WIDTH) {
			messageSplit.add(new String(message.substring(0, LCD_DISP_WIDTH)));
			message = message.substring(LCD_DISP_WIDTH, message.length());
		}

		int printRow = 0;
		Stopwatch sw = new Stopwatch();
		// print all of the messages
		for (String msg : messageSplit) {
			LCD.drawString("Here", 0, 4);
			LCD.drawString("CLS:" + ex.getClass().toString(), 0, 5);
			sw.reset();
			while (sw.elapsed() < 1000)
				Thread.yield();
			LCD.drawString(msg, 0, printRow);
			printRow++;
		}
		LCD.refresh();
		LCD.drawString("Press to exit...", 0, printRow);
		Button.waitForPress();
	}
}
