//******************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 	example2.java
//an example for making basic movement using DifferentialPilot in LeJOS NXJ
//******************************************************************

import lejos.nxt.*;
import lejos.robotics.navigation.*;

public class example2 {

	public static void main(String[] args) {

		DifferentialPilot pilot = new DifferentialPilot(3.1, 17.5, Motor.A,
				Motor.C);
		pilot.travel(10, true); // 10 centimeters
		pilot.rotate(90.0);
		while (pilot.isMoving()) {

			if (Button.ESCAPE.isPressed())
				pilot.stop();
			Button.waitForPress();

		}
	}
}
