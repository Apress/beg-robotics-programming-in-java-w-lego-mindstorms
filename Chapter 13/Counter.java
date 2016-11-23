//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		Counter.java
//Continuously write an incrementing integer to the LCD
//****************************************************************************

import lejos.nxt.LCD;

class Counter extends Thread {
	public void run() {
		for (int i = 0; i < 1000; ++i) {
			LCD.drawInt(i, 0, 2);
			LCD.refresh();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}
}
