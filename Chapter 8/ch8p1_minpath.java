//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch8p1_minpath.java
//the minimum path from starting node to all the other nodes, including each node
//and the corresponding minimum distance as well
//***********************************************************************************

import java.util.ArrayList;

public class ch8p1_minpath {
	private ArrayList<String> nodeList;
	private int edgeLength;

	public ch8p1_minpath(String node) {
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
