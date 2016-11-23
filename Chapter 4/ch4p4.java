//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch4p4.java
//A programming practice example to display free RAM on LCD 
//***********************************************************************************

// import EV3 hardware packages for EV brick finding, activating keys and LCD
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Stopwatch;

public class ch4p4 {

	public static void main(String[] args) throws InterruptedException {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();

		// for timing dialogs
		Stopwatch sw = new Stopwatch();

		// drawing free RAM on the LCD screen based on coordinates
		lcddisplay.drawString("Here is my RAM:", 0, 0);

		// displaying free memory on the LCD screen
		lcddisplay.drawInt((int) Runtime.getRuntime().freeMemory(), 0, 1);
		sw.reset();

		// wait for 5 seconds, then display a message
		while (sw.elapsed() < 5000)
			Thread.yield();
		sw.reset();
		lcddisplay.drawString("I got it", 0, 2);

		// exit program after any button pressed
		buttons.waitForAnyPress();
	}
}
