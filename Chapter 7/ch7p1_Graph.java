//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch7p1_Graph.java
//Represents GraphNodes connected through Links, including method for doing a hill climbing 
//search traversal of the graph.
//the method hillTraverse creates a hillPath list which the robot will follow to demonstrate 
//how the hill climbing search works.
//***********************************************************************************

import java.util.ArrayList;
import java.util.Stack;

public class ch7p1_Graph {

	// nodes and links define the physical creation of your Graph
	ArrayList<ch7p1_GraphNode> nodes;
	ArrayList<ch7p1_Link> links;

	// Two lists used for traversing
	ArrayList<ch7p1_GraphNode> hillTraverse;
	Stack<ch7p1_Link> travelStack = new Stack<ch7p1_Link>();

	// List used to define where you would like to move
	ArrayList<ch7p1_GraphNode> hillPath = new ArrayList<ch7p1_GraphNode>();

	// Constructor of ch7p1_Graph class
	public ch7p1_Graph() {
		nodes = new ArrayList<ch7p1_GraphNode>();
		links = new ArrayList<ch7p1_Link>();
		hillTraverse = new ArrayList<ch7p1_GraphNode>();
	}// end constructor

	// addNode()
	// Add a city node to the graph

	public void addNode(ch7p1_GraphNode node) {
		nodes.add(node);
	}// end addNode

	// addLink
	// Add link to the graph

	public void addLink(ch7p1_Link link) {
		links.add(link);
	}// end addLink

	// hillTraverse()
	// perform hill climbing search on the graph

	public void hillTraverse(ch7p1_GraphNode from, ch7p1_GraphNode to) {
		// boolean matched;
		int distance;
		ch7p1_Link found;

		// determine if there is a link between from and to
		// if there is a match then add the link to the travelStack and
		// add the nodes to hillPath
		// This will ultimately repeated by the end of the search

		distance = match(from, to);
		if (distance != 0) {
			travelStack.push(new ch7p1_Link(from, to, distance));
			hillPath.add(new ch7p1_GraphNode(to.cityName, to.xLocation,
					to.yLocation));
			hillPath.add(new ch7p1_GraphNode(from.cityName, from.xLocation,
					from.yLocation));
			return;
		}

		// if there is no match found you could another path findings
		found = find(from);

		// if you find a new connection then you could add it to the travelStack
		// and
		// and the start node to hillPath
		// recursively call hillTraverse with the link's to as start and our
		// destination as the end

		if (found != null) {
			travelStack.push(new ch7p1_Link(from, to, found.distance));
			hillTraverse(found.to, to);
			hillPath.add(new ch7p1_GraphNode(from.cityName, from.xLocation,
					from.yLocation));
		}

		// backtrack if you cannot find a new connection
		else if (travelStack.size() > 0) {
			found = travelStack.pop();
			hillTraverse(found.from, found.to);
			hillPath.add(new ch7p1_GraphNode(from.cityName, from.xLocation,
					from.yLocation));
		}
	}// end hillTraverse()

	// find() method is used to
	// find the next link to try exploring

	public ch7p1_Link find(ch7p1_GraphNode from) {

		int pos = -1;
		int dist = 0;

		// iterate through the list of links
		for (int a = 0; a < links.size(); a++) {

			if (links.get(a).from.equals(from) && !links.get(a).skip) {

				// Use the longest flight.
				if (links.get(a).distance > dist) {
					pos = a;
					dist = links.get(a).distance;
				}
			}
		}

		// link found
		if (pos != -1) {

			// mark this link as used so we don't match it again
			links.get(pos).skip = true;

			ch7p1_Link foundList = new ch7p1_Link(links.get(pos).from,
					links.get(pos).to, links.get(pos).distance);
			return foundList;
		}

		return null; // not found
	}// end find()

	// match() method is used to determine if there is a link between a starting
	// node and an ending node

	public int match(ch7p1_GraphNode from, ch7p1_GraphNode to) {

		// iterate through list of links
		for (int a = links.size() - 1; a >= 0; a--) {
			if (links.get(a).from.equals(from) && links.get(a).to.equals(to)
					&& !links.get(a).skip) {
				links.get(a).skip = true;
				return links.get(a).distance;
			}
		}
		return 0;
	}// end match()
}// end Graph.java
