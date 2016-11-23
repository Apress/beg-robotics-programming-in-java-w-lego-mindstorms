//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch12p1.java
//This program implements the leJOS behavior capabilities.
//The robot will begin following a green line.  When the touch
//sensor is activated it will move around an object and search for a
//blue line.  Then when it travels within 15cm of an object it will avoid it
//and return to the blue line again.
//***************************************************************************
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class ch12p1 {
	public static void main(String[] args) {
		// create a sensors object to send to the behavior classes
		Sensors sensors = new Sensors();

		// prompt to begin
		LCD.clear();
		LCD.drawString("Press to begin", 0, 0);
		Button.waitForPress();

		// set up behavior classes
		Behavior b1 = new FollowGreen(sensors);
		Behavior b2 = new TouchAvoid(sensors);
		Behavior b3 = new FollowBlue(sensors);
		Behavior b4 = new SonicAvoid(sensors);

		// create behavior array
		Behavior[] bArray = { b1, b3, b2, b4 };

		// send array to arbitrator and begin
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}
}
