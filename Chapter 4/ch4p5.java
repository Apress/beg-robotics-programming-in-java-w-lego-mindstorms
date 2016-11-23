//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch4p5.java
//A programming practice example to display various buttons when they are pressed  
//***********************************************************************************

// import EV3 hardware packages for EV brick finding, activating keys and LCD
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class ch4p5 {

	public static void main(String[] args) throws InterruptedException {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();

		// continue waiting for button pressed while the user has not pressed
		// escape button
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			// display a message for enter
			if (buttons.getButtons() == Keys.ID_ENTER) {
				lcddisplay.clear();
				lcddisplay.drawString("ENTER", 0, 0);
			}
			// display a message for left button
			else if (buttons.getButtons() == Keys.ID_LEFT) {
				lcddisplay.clear();
				lcddisplay.drawString("LEFT", 0, 0);
			}
			// display a message for right button
			else if (buttons.getButtons() == Keys.ID_RIGHT) {
				lcddisplay.clear();
				lcddisplay.drawString("RIGHT", 0, 0);
			}
			// display a message for up button
			else if (buttons.getButtons() == Keys.ID_UP) {
				lcddisplay.clear();
				lcddisplay.drawString("UP", 0, 0);
			}
			// display a message for down button
			else if (buttons.getButtons() == Keys.ID_DOWN) {
				lcddisplay.clear();
				lcddisplay.drawString("DOWN", 0, 0);
			}
		}
	}
}
