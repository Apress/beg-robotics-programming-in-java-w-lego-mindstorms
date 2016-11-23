//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch8p2_main.java
//Driver class to set up map using ch8p2_edge, find the shortest path from 
//starting node A to all the other nodes in a given graph.
//and then robots will follow the path to move from start node A
//to the given destination node I
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
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

import java.util.ArrayList;

public class ch8p2_main {
	static ArrayList<ch8p2_edge> graph = null;
	static ch8p2_edge[] parents = null;

	static ArrayList<String> unsolvedConn = null;
	static ArrayList<String> solvedConn = null;
	private static ch8p2_minpath finalPath;

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.C);

	static Waypoint[] coordinates = { new Waypoint(0, 0),
			new Waypoint(-10, 20), new Waypoint(0, 20), new Waypoint(-15, 20),
			new Waypoint(-15, 30), new Waypoint(0, 30), new Waypoint(10, 10),
			new Waypoint(10, 20), new Waypoint(10, 30),

	};

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

		// initialize the nodes set
		String[] nodes = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

		// initialize the map with the nodes
		graph = new ArrayList<ch8p2_edge>();

		graph.add(new ch8p2_edge("A", "B", 22));
		graph.add(new ch8p2_edge("A", "C", 20));
		graph.add(new ch8p2_edge("A", "D", 25));

		graph.add(new ch8p2_edge("B", "C", 10));

		graph.add(new ch8p2_edge("C", "E", 18));
		graph.add(new ch8p2_edge("C", "F", 10));
		graph.add(new ch8p2_edge("C", "G", 14));
		graph.add(new ch8p2_edge("C", "H", 10));
		graph.add(new ch8p2_edge("C", "I", 18));

		graph.add(new ch8p2_edge("D", "B", 5));
		graph.add(new ch8p2_edge("D", "E", 10));
		graph.add(new ch8p2_edge("D", "F", 18));

		graph.add(new ch8p2_edge("E", "F", 15));

		graph.add(new ch8p2_edge("F", "I", 10));

		graph.add(new ch8p2_edge("G", "H", 10));

		graph.add(new ch8p2_edge("H", "F", 14));
		graph.add(new ch8p2_edge("H", "I", 10));

		// initialize the unsolved nodes
		unsolvedConn = new ArrayList<String>();

		// sets the parent node in the unsolved connection ArrayList to A
		unsolvedConn.add(nodes[0]);

		// initialize the solved nodes
		solvedConn = new ArrayList<String>();

		// Add all nodes to the solved connection tree
		for (int i = 1; i < nodes.length; i++)
			solvedConn.add(nodes[i]);

		// create a parent array that will store all the edges
		parents = new ch8p2_edge[nodes.length];

		// Set the initial node to A and make its parent null with a weight cost
		// of zero
		parents[0] = new ch8p2_edge(null, nodes[0], 0);

		for (int i = 0; i < solvedConn.size(); i++) {
			// get all of the String node names that could be attached the
			// root
			String n = solvedConn.get(i);

			// Check the weights of all the nodes that are attached to the root
			// A node
			// If they are attached will return positive weight if not will
			// return -1
			parents[i + 1] = new ch8p2_edge(nodes[0], n, getEdgeLength(
					nodes[0], n));
		}

		finalPath = null;
		// while the solved nodes ArrayList is greater than zero
		while (solvedConn.size() > 0) {
			// Create a minimum shortest path object to find the shortest path
			// to all connected points
			ch8p2_minpath msp = getMinSideNode();
			finalPath = msp;

			String node = msp.getLastNode();
			unsolvedConn.add(node);
			setEdgeLength(node);
		}
		Path directions = finalPath.buildPath(coordinates);
		navbot.setPath(directions);
		navbot.singleStep(true);
		while (navbot.getWaypoint() != null) {
			if (navbot.getWaypoint() != null) {
				System.out.println("Next destination" + navbot.getWaypoint());

			}
			System.out.println("Press Enter Key to Continue");
			// block the thread until a button is pressed
			buttons.waitForAnyPress();

			navbot.followPath();
			while (navbot.isMoving())
				;
		}
		// block the thread until a button is pressed
		buttons.waitForAnyPress();

	}

	public static String getParent(ch8p2_edge[] parents, String node) {
		if (parents != null) {
			for (ch8p2_edge nd : parents) {
				if (nd.getChildNode() == node) {
					return nd.getParentNode();
				}
			}
		}
		return null;
	}

	public static void setEdgeLength(String parentNode) {
		if (graph != null && parents != null && solvedConn != null) {
			for (String node : solvedConn) {
				ch8p2_minpath msp = getMinPath(node);
				int w1 = msp.getEdgeLength();
				if (w1 == -1)
					continue;
				for (ch8p2_edge n : parents) {
					if (n.getChildNode() == node) {
						if (n.getEdgeLength() == -1 || n.getEdgeLength() > w1) {
							n.setEdgeLength(w1);
							n.setParentNode(parentNode);
							break;
						}
					}
				}
			}
		}
	}

	public static int getEdgeLength(String parentNode, String childNode) {
		if (graph != null) {
			for (ch8p2_edge s : graph) {
				if (s.getParentNode() == parentNode
						&& s.getChildNode() == childNode)
					return s.getEdgeLength();
			}
		}
		return -1;
	}

	public static ch8p2_minpath getMinSideNode() {
		// Create a minimum shortest path object
		ch8p2_minpath minMsp = null;
		// while the solved node ArrayList is greater than zero
		if (solvedConn.size() > 0) {
			// Create an index value set to zero
			int index = 0;
			// for each value in the solved nodes ArrayList
			for (int j = 0; j < solvedConn.size(); j++) {
				// Create a shortest path map get the MinPath of the solved node
				ch8p2_minpath msp = getMinPath(solvedConn.get(j));
				// if there is no minimum shortest path, if the minimum shortest
				// path
				// does not equal -1
				// AND the minimum shortest path get weight is less than the
				// minimum shortest path get weight
				if (minMsp == null || msp.getEdgeLength() != -1
						&& msp.getEdgeLength() < minMsp.getEdgeLength()) {
					// set the minimum shortest path to the minimum shortest
					// path
					minMsp = msp;
					// set the index value equal it the node value j
					index = j;
				}
			}
			// remove the index that you checked in the solved nodes
			solvedConn.remove(index);

		}
		// return the MinShortPath object
		return minMsp;
	}

	public static ch8p2_minpath getMinPath(String node) {
		// Create a Minshort Path object that is an ArrayList and set the take
		// in node as the base
		// in this case will always be zero
		ch8p2_minpath msp = new ch8p2_minpath(node);
		// if the parents array does not equal null and the unsolved nodes does
		// not equal null
		if (parents != null && unsolvedConn != null) {
			for (int i = 0; i < unsolvedConn.size(); i++) {
				ch8p2_minpath tempMsp = new ch8p2_minpath(node);
				String parent = unsolvedConn.get(i);
				String curNode = node;
				while (parent != null) {
					int weight = getEdgeLength(parent, curNode);
					if (weight > -1) {
						tempMsp.addNode(parent);
						tempMsp.addEdgeLength(weight);
						curNode = parent;
						parent = getParent(parents, parent);
					} else
						break;
				}

				if (msp.getEdgeLength() == -1 || tempMsp.getEdgeLength() != -1
						&& msp.getEdgeLength() > tempMsp.getEdgeLength())
					msp = tempMsp;
			}
		}
		return msp;
	}
}
