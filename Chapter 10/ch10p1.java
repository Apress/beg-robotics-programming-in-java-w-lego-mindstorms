//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch10p1.java
//an example for touch sensor testing
//***********************************************************************************

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class ch10p1 {
	public static void main(String[] args) throws Exception {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// get a port instance
		Port portS1 = ev3brick.getPort("S1");

		// Get an instance of the touch EV3 sensor
		EV3TouchSensor touchSensor = new EV3TouchSensor(portS1);

		// get an instance of this sensor in measurement mode
		SensorMode toucher = touchSensor.getTouchMode();

		// initialise an array of floats for fetching samples
		float[] samplevalue = new float[toucher.sampleSize()];

		lcddisplay.clear();

		while (buttons.getButtons() != Keys.ID_ESCAPE) {

			// fetch a sample
			toucher.fetchSample(samplevalue, 0);

			lcddisplay.drawString("value: " + samplevalue[0], 0, 0);
			Thread.sleep(50);
		}

		lcddisplay.clear();
		System.out.println("EXIT");
		System.exit(0);
	}

}
