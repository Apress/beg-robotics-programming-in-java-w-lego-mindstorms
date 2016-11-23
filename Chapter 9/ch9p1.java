//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch9p1.java
//simple line map and grid using A* search build into LeJOS
//*******************************************************

import lejos.geom.*; //used for rectangle
import lejos.robotics.RegulatedMotor; //motor controller
import lejos.robotics.localization.*; //numbers
import lejos.robotics.mapping.LineMap; //mapping
import lejos.robotics.navigation.*; //navigation used for the waypoints
import lejos.robotics.pathfinding.*; //A* search algorithm
import lejos.util.PilotProps; //not used really
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

public class ch9p1 {

	private static final short[] note = { 2349, (115 / 3), 0, (5 / 3), 1760,
			(165 / 3), 0, (35 / 3), 1760, (28 / 3), 0, (13 / 3), 1976,
			(23 / 3), 0, (18 / 3), 1760, (18 / 3), 0, (23 / 3), 1568, (15 / 3),
			0, (25 / 3), 1480, (103 / 3), 0, (18 / 3), 1175, (180 / 3), 0,
			(20 / 3), 1760, (18 / 3), 0, (23 / 3), 1976, (20 / 3), 0, (20 / 3),
			1760, (15 / 3), 0, (25 / 3), 1568, (15 / 3), 0, (25 / 3), 2217,
			(98 / 3), 0, (23 / 3), 1760, (88 / 3), 0, (33 / 3), 1760, (75 / 3),
			0, (5 / 3), 1760, (20 / 3), 0, (20 / 3), 1760, (20 / 3), 0,
			(20 / 3), 1976, (18 / 3), 0, (23 / 3), 1760, (18 / 3), 0, (23 / 3),
			2217, (225 / 3), 0, (15 / 3), 2217, (218 / 3) };

	static RegulatedMotor leftMotor; // motors
	static RegulatedMotor rightMotor;

	public static void main(String[] args) {

		// set up the robot
		PilotProps pp = new PilotProps();
		float wheelDiameter = Float.parseFloat(pp.getProperty(
				PilotProps.KEY_WHEELDIAMETER, "2.11"));
		float trackWidth = Float.parseFloat(pp.getProperty(
				PilotProps.KEY_TRACKWIDTH, "5.45"));

		RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(
				PilotProps.KEY_LEFTMOTOR, "C"));

		RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(
				PilotProps.KEY_RIGHTMOTOR, "A"));
		leftMotor.setSpeed(750);
		rightMotor.setSpeed(750);
		leftMotor.setAcceleration(1000);
		rightMotor.setAcceleration(1000);
		boolean reverse = Boolean.parseBoolean(pp.getProperty(
				PilotProps.KEY_REVERSE, "false"));

		// new robot object using the setup
		DifferentialPilot robot = new DifferentialPilot(wheelDiameter,
				trackWidth, leftMotor, rightMotor, reverse);

		// make the robot move faster this is over max
		// robot.setTravelSpeed(500);
		robot.setRotateSpeed(600);

		// Create final map:
		Line[] lines = new Line[8]; // six lines inside the map
		lines[0] = new Line(-2.5f, -2.5f, -2.5f, 67.5f); // line AG
		lines[1] = new Line(-5.0f, -2.5f, -5.0f, 67.5f);
		lines[2] = new Line(-2.5f, 67.5f, 47.5f, 67.5f); // line GF
		lines[3] = new Line(-2.5f, 7.5f, 47.5f, 7.5f); // line BC
		lines[4] = new Line(47.5f, 7.5f, 47.5f, 27.5f); // line cd
		lines[5] = new Line(44f, 7.5f, 44f, 27.5f); // cd broader
		lines[6] = new Line(27.5f, 27.5f, 27.5f, 47.5f); // line je
		lines[7] = new Line(27.5f, 47.5f, 67.5f, 47.5f); // line ek

		lejos.geom.Rectangle bounds = new Rectangle(-22.5f, -2.5f, 90f, 90f);
		LineMap myMap = new LineMap(lines, bounds); // add the bounds to the map

		// Use a regular grid of node points. Grid space = 20. Clearance = 15:
		FourWayGridMesh grid = new FourWayGridMesh(myMap, 10, 2f);

		// Use A* search:
		AstarSearchAlgorithm alg = new AstarSearchAlgorithm();

		// Give the A* search alg and grid to the PathFinder:
		PathFinder pf = new NodePathFinder(alg, grid);

		// store the location of the robot at a given time
		PoseProvider posep = new OdometryPoseProvider(robot);

		// new navigator loaded with the robot position, and path
		NavPathController nav = new NavPathController(robot, posep, pf);

		System.out.println("Planning path..."); // displays as the path is
												// calculated
		nav.goTo(-12, 0); // goto the end location.

		for (int i = 0; i < note.length; i += 2) {
			final short w = note[i + 1];
			final int n = note[i];
			if (n != 0)
				Sound.playTone(n, w * 10);
			try {
				Thread.sleep(w * 10);
			} catch (InterruptedException e) {
			}
		}

	}
}
