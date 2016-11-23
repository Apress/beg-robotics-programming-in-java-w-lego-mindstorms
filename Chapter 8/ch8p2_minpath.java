//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch8p2_minpath.java
//the minimum path from starting node to all the other nodes, including each node
//and the corresponding minimum distance as well
//***********************************************************************************

import java.util.ArrayList;

import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class ch8p2_minpath {
	private ArrayList<String> nodeList;
	private int edgeLength;

	public ch8p2_minpath(String node) {
		nodeList = new ArrayList<String>();
		nodeList.add(node);
		edgeLength = -1;
	}

	public ArrayList<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<String> nodeList) {
		this.nodeList = nodeList;
	}

	public void addNode(String node) {
		if (nodeList == null)
			nodeList = new ArrayList<String>();
		nodeList.add(0, node);
	}

	public String getLastNode() {
		int size = nodeList.size();
		return nodeList.get(size - 1);

	}

	public int getEdgeLength() {
		return edgeLength;
	}

	public void setEdgeLength(int edgeLength) {
		this.edgeLength = edgeLength;
	}

	public void outputPath() {
		outputPath(null);
	}

	public Path buildPath(Waypoint[] coordinates) {
		Path result = new Path();
		int p = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).equals("A"))
				p = 0;
			else if (nodeList.get(i).equals("A"))
				p = 0;
			else if (nodeList.get(i).equals("B"))
				p = 1;
			else if (nodeList.get(i).equals("C"))
				p = 2;
			else if (nodeList.get(i).equals("D"))
				p = 3;
			else if (nodeList.get(i).equals("E"))
				p = 4;
			else if (nodeList.get(i).equals("F"))
				p = 5;
			else if (nodeList.get(i).equals("G"))
				p = 6;
			else if (nodeList.get(i).equals("H"))
				p = 7;
			else if (nodeList.get(i).equals("I"))
				p = 8;
			result.add(coordinates[p]);
		}

		return result;

	}

	public void outputPath(String pathNode) {
		String result = "the munimum path of ";
		if (pathNode != null)
			nodeList.add(pathNode);
		for (int i = 0; i < nodeList.size(); i++) {
			result += "" + nodeList.get(i);
			if (i < nodeList.size() - 1)
				result += "->";
		}
		result += " is:" + edgeLength;
		System.out.println(result);
	}

	public void addEdgeLength(int e) {
		if (edgeLength == -1)
			edgeLength = e;
		else
			edgeLength += e;
	}

}
