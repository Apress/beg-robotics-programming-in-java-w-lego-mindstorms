//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch13p1.java
//This program executes the thread HelloWorldThread to print the message
//"Hello World" and the value of a counter to count how 
//many messages printed on the screen.
//****************************************************************************

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class ch13p1 {
	private static HelloWorldThread hwt;

	public static void main(String[] args) {
		int i = 0;

		hwt = new HelloWorldThread();
		hwt.start();
		try {
			while (!Button.ESCAPE.isPressed()) {
				LCD.drawString("Hello World: " + i, 0, 0);
				++i;
			}
		} catch (Exception ex) {
		} finally {
			System.exit(0);
		}
	}
}