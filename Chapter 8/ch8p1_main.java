//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch8p1_main.java
//Driver class to set up map using ch8p1_edge, find the shortest path from 
//starting node A to all the other nodes in a given graph.
//***********************************************************************************

import java.util.ArrayList;

public class ch8p1_main {
	static ArrayList<ch8p1_edge> graph = null;
	static ch8p1_edge[] parents = null;

	static ArrayList<String> unsolvedConn = null;
	static ArrayList<String> solvedConn = null;
	private static ch8p1_minpath finalPath;

	public static void main(String[] args) {

		// initialize the nodes set
		String[] nodes = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

		// initialize the map with the nodes
		graph = new ArrayList<ch8p1_edge>();

		graph.add(new ch8p1_edge("A", "B", 7));
		graph.add(new ch8p1_edge("A", "C", 2));
		graph.add(new ch8p1_edge("A", "D", 3));
		graph.add(new ch8p1_edge("A", "E", 17));
		graph.add(new ch8p1_edge("B", "F", 4));
		graph.add(new ch8p1_edge("C", "B", 3));
		graph.add(new ch8p1_edge("C", "G", 29));
		graph.add(new ch8p1_edge("C", "J", 7));
		graph.add(new ch8p1_edge("D", "J", 8));
		graph.add(new ch8p1_edge("D", "I", 13));
		graph.add(new ch8p1_edge("D", "E", 5));
		graph.add(new ch8p1_edge("E", "I", 19));
		graph.add(new ch8p1_edge("F", "G", 10));
		graph.add(new ch8p1_edge("G", "H", 17));
		graph.add(new ch8p1_edge("J", "H", 18));
		graph.add(new ch8p1_edge("I", "H", 6));

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
		parents = new ch8p1_edge[nodes.length];

		// Set the initial node to A and make its parent null with a weight cost
		// of zero
		parents[0] = new ch8p1_edge(null, nodes[0], 0);

		for (int i = 0; i < solvedConn.size(); i++) {
			// get all of the String node names that could be attached the
			// root
			String n = solvedConn.get(i);

			// Check the weights of all the nodes that are attached to the root
			// A node
			// If they are attached will return positive weight if not will
			// return -1
			parents[i + 1] = new ch8p1_edge(nodes[0], n, getEdgeLength(
					nodes[0], n));
		}

		finalPath = null;
		// while the solved nodes ArrayList is greater than zero
		while (solvedConn.size() > 0) {
			// Create a minimum shortest path object to find the shortest path
			// to all connected points
			ch8p1_minpath msp = getMinSideNode();
			finalPath = msp;

			if (msp.getEdgeLength() == -1)
				msp.outputPath(nodes[0]);
			else
				msp.outputPath();

			String node = msp.getLastNode();
			unsolvedConn.add(node);
			setEdgeLength(node);
		}
	}

	public static String getParent(ch8p1_edge[] parents, String node) {
		if (parents != null) {
			for (ch8p1_edge nd : parents) {
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
				ch8p1_minpath msp = getMinPath(node);
				int w1 = msp.getEdgeLength();
				if (w1 == -1)
					continue;
				for (ch8p1_edge n : parents) {
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
			for (ch8p1_edge s : graph) {
				if (s.getParentNode() == parentNode
						&& s.getChildNode() == childNode)
					return s.getEdgeLength();
			}
		}
		return -1;
	}

	public static ch8p1_minpath getMinSideNode() {
		// Create a minimum shortest path object
		ch8p1_minpath minMsp = null;
		// while the solved node ArrayList is greater than zero
		if (solvedConn.size() > 0) {
			// Create an index value set to zero
			int index = 0;
			// for each value in the solved nodes ArrayList
			for (int j = 0; j < solvedConn.size(); j++) {
				// Create a shortest path map get the MinPath of the solved node
				ch8p1_minpath msp = getMinPath(solvedConn.get(j));
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

	public static ch8p1_minpath getMinPath(String node) {
		// Create a Minshort Path object that is an ArrayList and set the take
		// in node as the base
		// in this case will always be zero
		ch8p1_minpath msp = new ch8p1_minpath(node);
		// if the parents array does not equal null and the unsolved nodes does
		// not equal null
		if (parents != null && unsolvedConn != null) {
			for (int i = 0; i < unsolvedConn.size(); i++) {
				ch8p1_minpath tempMsp = new ch8p1_minpath(node);
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
