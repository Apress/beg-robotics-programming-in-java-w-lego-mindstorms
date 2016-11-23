//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch6p2_main.java
//Driver class to set up map using ch6p2_GraphNode, ch6p2_Link, and ch6p2_Graph classes.
//Calls a depth first search in ch6p2_Graph class to create navigation path from a start
//and end node and then robots will follow the path to move from start node
//to destination node
//***********************************************************************************

// import EV3 hardware packages for EV brick finding, activating keys and LCD
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

public class ch6p2_main {

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
		ch6p2_Graph searchGraph = new ch6p2_Graph();
		ch6p2_GraphNode A, B, C, D, E, F, G, H, I, J, K, L, M;
		ch6p2_Link AB, AC, BD, BE, EF, EG, FH, FI, HJ, HK, KL, KM;

		// define each node
		A = new ch6p2_GraphNode("A", 0, 0);
		B = new ch6p2_GraphNode("B", -5, 5);
		C = new ch6p2_GraphNode("C", 5, 5);
		D = new ch6p2_GraphNode("D", -10, 10);
		E = new ch6p2_GraphNode("E", 0, 10);
		F = new ch6p2_GraphNode("F", -5, 15);
		G = new ch6p2_GraphNode("G", 5, 15);
		H = new ch6p2_GraphNode("H", -10, 20);
		I = new ch6p2_GraphNode("I", 0, 20);
		J = new ch6p2_GraphNode("J", -15, 25);
		K = new ch6p2_GraphNode("K", -5, 25);
		L = new ch6p2_GraphNode("L", -10, 30);
		M = new ch6p2_GraphNode("M", 0, 30);

		// define which GraphNodes are connected
		AB = new ch6p2_Link(A, B);
		AC = new ch6p2_Link(A, C);
		BD = new ch6p2_Link(B, D);
		BE = new ch6p2_Link(B, E);
		EF = new ch6p2_Link(E, F);
		EG = new ch6p2_Link(E, G);
		FH = new ch6p2_Link(F, H);
		FI = new ch6p2_Link(F, I);
		HJ = new ch6p2_Link(H, J);
		HK = new ch6p2_Link(H, K);
		KL = new ch6p2_Link(K, L);
		KM = new ch6p2_Link(K, M);

		// add all nodes and links to your graph object
		searchGraph.addNode(A);
		searchGraph.addNode(B);
		searchGraph.addNode(C);
		searchGraph.addNode(D);
		searchGraph.addNode(E);
		searchGraph.addNode(F);
		searchGraph.addNode(G);
		searchGraph.addNode(H);
		searchGraph.addNode(I);
		searchGraph.addNode(J);
		searchGraph.addNode(K);
		searchGraph.addNode(L);
		searchGraph.addNode(M);

		searchGraph.addLink(AB);
		searchGraph.addLink(AC);
		searchGraph.addLink(BD);
		searchGraph.addLink(BE);
		searchGraph.addLink(EF);
		searchGraph.addLink(EG);
		searchGraph.addLink(FH);
		searchGraph.addLink(FI);
		searchGraph.addLink(HJ);
		searchGraph.addLink(HK);
		searchGraph.addLink(KL);
		searchGraph.addLink(KM);

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// run breadth-first search to get from start to destination
		searchGraph.bfsTraverse(
				searchGraph.nodes.get(searchGraph.nodes.indexOf(A)),
				searchGraph.nodes.get(searchGraph.nodes.indexOf(C)));

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// Robot moves through path from start to destination by using
		// bfsTraverse
		for (int i = 0; i < searchGraph.bfsTraverse.size(); i++) {

			// go to node
			navbot.goTo(searchGraph.bfsPath.get(i).xLocation,
					searchGraph.bfsPath.get(i).yLocation);

			LCD.clear();

			// display current location
			LCD.drawString("At location " + searchGraph.bfsPath.get(i).cityName
					+ ", ", 0, 0);

			LCD.drawString(searchGraph.bfsPath.get(i).xLocation + ", "
					+ searchGraph.bfsPath.get(i).yLocation, 0, 1);

			LCD.drawString("Press ENTER key", 0, 2);

			// block the thread until a button is pressed
			buttons.waitForAnyPress();

			if (i == searchGraph.bfsTraverse.size() - 1) {
				navbot.goTo(searchGraph.bfsTraverse.get(i).to.yLocation,
						searchGraph.bfsTraverse.get(i).to.xLocation);
				LCD.drawString("At location "
						+ searchGraph.bfsTraverse.get(i).to.cityName, 0, 0);
				// block the thread until a button is pressed
				buttons.waitForAnyPress();
			}
		}
	}
}
