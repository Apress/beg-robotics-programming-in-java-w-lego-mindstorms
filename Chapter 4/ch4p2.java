//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch4p2.java
//An example to test LCD displaying methods
//***********************************************************************************

// import EV3 hardware packages for EV brick finding, activating keys and LCD
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class ch4p2 {

	public static void main(String[] args) throws InterruptedException {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();

		// drawing text on the LCD screen based on coordinates
		lcddisplay.drawString("Free RAM:", 0, 0);

		// displaying free memory on the LCD screen
		lcddisplay.drawInt((int) Runtime.getRuntime().freeMemory(), 0, 1);

		// exit program after any button pressed
		buttons.waitForAnyPress();
	}

}
