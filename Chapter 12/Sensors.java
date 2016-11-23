//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		Sensors.java
//Class to store sensor objects to make them available in all behavior classes
//*****************************************************************************
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

public class Sensors {
	ColorSensor colorSense;
	TouchSensor touch;
	UltrasonicSensor sonic;
	boolean touchPressed = false; // used for take control method in followBlue
									// behavior

	public Sensors() {
		// set up color, touch, and sonic sensors
		colorSense = new ColorSensor(SensorPort.S1);
		touch = new TouchSensor(SensorPort.S2);
		sonic = new UltrasonicSensor(SensorPort.S4);

	}
}
