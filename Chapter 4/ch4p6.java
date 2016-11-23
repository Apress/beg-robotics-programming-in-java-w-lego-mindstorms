//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch4p6.java
//A programming practice example to display ESCAPE button pressed and released   
//***********************************************************************************

// import EV3 hardware packages for EV brick finding, activating keys and LCD
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Stopwatch;

public class ch4p6 {

	public static void main(String[] args) throws InterruptedException {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();

		// print instructions for the user
		lcddisplay.clear();
		lcddisplay.drawString("Prs ENTER to cont", 0, 0);
		lcddisplay.refresh();

		// wait until ENTER key is pressed
		while (buttons.waitForAnyPress() != Keys.ID_ENTER)
			Thread.yield();

		// wait until ESCAPE key is pressed, then wait for it to be released
		while (buttons.getButtons() != Keys.ID_ESCAPE)
			Thread.yield();

		// once escape is released, indicate that it was and pause for a moment
		// (i.e. 5 seconds) before exiting
		lcddisplay.drawString("Escape was pressed", 0, 1);
		Stopwatch sw = new Stopwatch();
		while (sw.elapsed() < 5000)
			Thread.yield();
	}
}
