//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch7p2_main.java
//Driver class to set up map using ch7p2_GraphNode, ch7p2_Link, and ch7p2_Graph classes.
//Calls a hill climbing search in ch7p2_Graph class to create navigation path from a start
//and end node and then robots will follow the path to move from start node
//to destination node
//***********************************************************************************

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;

public class ch7p2_main {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.C);

	public static void main(String[] args) {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();

		// setup the wheel diameter of left (and right) motor in centimeters,
		// i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to
		// the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-9);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(9);

		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 },
				WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot ev3robot = new MovePilot(chassis);

		Navigator navbot = new Navigator(ev3robot);

		// These objects used to define what your graph looks like
		ch7p2_Graph searchGraph = new ch7p2_Graph();
		ch7p2_GraphNode A, B, C, D, E, F, G, H;
		ch7p2_Link AB, AC, AD, BD, CB, CE, CF, DE, DH, FE, GE, HG;

		// define each node
		A = new ch7p2_GraphNode("A", 0, 0);
		B = new ch7p2_GraphNode("B", -10, 20);
		C = new ch7p2_GraphNode("C", -15, 20);
		D = new ch7p2_GraphNode("D", 0, 20);
		E = new ch7p2_GraphNode("E", 0, 30);
		F = new ch7p2_GraphNode("F", -15, 30);
		G = new ch7p2_GraphNode("G", 10, 20);
		H = new ch7p2_GraphNode("H", 10, 10);

		// define which GraphNodes are connected
		AB = new ch7p2_Link(A, B, dist(A.xLocation, A.yLocation, B.xLocation,
				B.yLocation));
		AC = new ch7p2_Link(A, C, dist(A.xLocation, A.yLocation, C.xLocation,
				C.yLocation));
		AD = new ch7p2_Link(A, D, dist(A.xLocation, A.yLocation, D.xLocation,
				D.yLocation));
		BD = new ch7p2_Link(B, D, dist(B.xLocation, B.yLocation, D.xLocation,
				D.yLocation));
		CB = new ch7p2_Link(C, B, dist(C.xLocation, C.yLocation, B.xLocation,
				B.yLocation));
		CE = new ch7p2_Link(C, E, dist(C.xLocation, C.yLocation, E.xLocation,
				E.yLocation));
		CF = new ch7p2_Link(C, F, dist(C.xLocation, C.yLocation, F.xLocation,
				F.yLocation));
		DE = new ch7p2_Link(D, E, dist(D.xLocation, D.yLocation, E.xLocation,
				E.yLocation));
		DH = new ch7p2_Link(D, H, dist(D.xLocation, D.yLocation, H.xLocation,
				H.yLocation));
		FE = new ch7p2_Link(F, E, dist(F.xLocation, F.yLocation, E.xLocation,
				E.yLocation));
		GE = new ch7p2_Link(G, E, dist(G.xLocation, G.yLocation, E.xLocation,
				E.yLocation));
		HG = new ch7p2_Link(H, G, dist(H.xLocation, H.yLocation, G.xLocation,
				G.yLocation));

		// add all nodes and links to your graph object
		searchGraph.addNode(A);
		searchGraph.addNode(B);
		searchGraph.addNode(C);
		searchGraph.addNode(D);
		searchGraph.addNode(E);
		searchGraph.addNode(F);
		searchGraph.addNode(G);
		searchGraph.addNode(H);

		searchGraph.addLink(AB);
		searchGraph.addLink(AC);
		searchGraph.addLink(AD);
		searchGraph.addLink(BD);
		searchGraph.addLink(CB);
		searchGraph.addLink(CE);
		searchGraph.addLink(CF);
		searchGraph.addLink(DE);
		searchGraph.addLink(DH);
		searchGraph.addLink(FE);
		searchGraph.addLink(GE);
		searchGraph.addLink(HG);

		// run hill climbing search to get from start to destination
		searchGraph.hillTraverse(
				searchGraph.nodes.get(searchGraph.nodes.indexOf(A)),
				searchGraph.nodes.get(searchGraph.nodes.indexOf(E)));

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// Robot moves through path from start to destination by using
		// hillTraverse

		for (int i = searchGraph.hillPath.size() - 1; i >= 0; i--) {
			int count = 0;
			for (int j = searchGraph.hillPath.size() - 1; j >= 0; j--) {
				if (searchGraph.hillPath.get(i).cityName == searchGraph.hillPath
						.get(j).cityName)
					count++;

			}
			if (count == 1) {
				// go to node
				navbot.goTo(searchGraph.hillPath.get(i).xLocation,
						searchGraph.hillPath.get(i).yLocation);

				LCD.clear();

				// display current location
				LCD.drawString("At location "
						+ searchGraph.hillPath.get(i).cityName + ", ", 0, 0);

				LCD.drawString(searchGraph.hillPath.get(i).xLocation + ", "
						+ searchGraph.hillPath.get(i).yLocation, 0, 1);

				LCD.drawString("Press ENTER key", 0, 2);

				// block the thread until a button is pressed
				buttons.waitForAnyPress();

			}

		}

	}

	static int dist(int x1, int y1, int x2, int y2) {
		int distance = 0;

		distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		return distance;
	}
}
