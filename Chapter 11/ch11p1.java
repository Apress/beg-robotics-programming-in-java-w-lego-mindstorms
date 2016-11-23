//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch11p1.java
//an example for light sensor testing
//***********************************************************************************

import lejos.nxt.*;
import lejos.robotics.Color;

public class ch11p1 {
	public static void main(String[] args) throws Exception {

		// Get an instance of the color/light sensor of NXT 2.0
		ColorSensor lightsensor = new ColorSensor(SensorPort.S1);

		// turn off the color detection, detecting light only
		lightsensor.setFloodlight(Color.NONE);

		LCD.clear();

		// keep receiving light value until an Escape button is pressed
		while (!Button.ESCAPE.isPressed()) {
			LCD.drawInt(lightsensor.getLightValue(), 4, 0, 0);
		}

		// clean out the LCD screen
		LCD.clear();
	}
}
