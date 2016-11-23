//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch11p3.java
//This program allows your robot to follow a line.
//If line is lost the robot will rotate left and right at increasing angles
//until the line is found again.
//****************************************************************************
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class ch11p3
{
	public static void main(String[] args) 
	{
		//set up differential pilot and nav path controller to use for navigation
		DifferentialPilot pilot = new DifferentialPilot(4.32f, 12.2f, Motor.A, Motor.C);
		pilot.setTravelSpeed(4);
				
		//set up color sensor
		ColorSensor colorSense = new ColorSensor(SensorPort.S1);
		colorSense.setFloodlight(false);
		
		//used to store values returned by color sensor
		//follow is color robot is to follow, search is value returned by sensor when searching
		int follow, search;
		
		//degrees robot will rotate when searching for line
		int rotation;

		//calibrate sensor
		LCD.drawString("Place color sensor\nabove color to follow", 0, 0);
		Button.waitForPress();
		follow = colorSense.getColorID();
		
		//place robot on start and wait for button press to begin main loop
		LCD.clear();
		LCD.drawString("Place robot", 0, 0);
		Button.waitForPress();
		
		//main loop
		//follow line.  if line is lost turn left and right to search for it
		while(!Button.ESCAPE.isPressed())
		{
			rotation = 5;
			search = colorSense.getColorID(); //make sure we are still on the line
			
			//line is found continue forward
			while(search == follow)
			{
				pilot.forward();
				search = colorSense.getColorID();
			}
			
			//line lost
			while(search != follow)
			{
				pilot.rotate(rotation); //rotate right
				search = colorSense.getColorID();
				if(search == follow)
					break; //found line again exit loop
				else
				{
					pilot.rotate(-rotation * 2); //rotate left back to start then to left position
					search = colorSense.getColorID();
					if(search == follow)
						break;	//found line again exit loop
					
					pilot.rotate(rotation); //rotate back to center
				}
				rotation+=5; //increase angle of rotation and continue search
			}//end search
		}//end main loop
	}//end main()
}//end ch11p3
