//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch5p2_Graph.java
//Represents GraphNodes connected through Links, including method for doing a depth-first 
//search traversal of the graph.
//the method dfsTraverse creates a dfsPath list which the robot will follow to demonstrate 
//how the depth-first search works.
//***********************************************************************************

import java.util.ArrayList;
import java.util.Stack;

public class ch5p2_Graph {

	// nodes and links define the physical creation of your Graph
	ArrayList<ch5p2_GraphNode> nodes;
	ArrayList<ch5p2_Link> links;

	// Two lists used for traversing
	ArrayList<ch5p2_GraphNode> dfsTraverse;
	Stack<ch5p2_Link> travelStack = new Stack<ch5p2_Link>();

	// List used to define where you would like to move
	ArrayList<ch5p2_GraphNode> dfsPath = new ArrayList<ch5p2_GraphNode>();

	// Constructor of ch5p2_Graph class
	public ch5p2_Graph() {
		nodes = new ArrayList<ch5p2_GraphNode>();
		links = new ArrayList<ch5p2_Link>();
		dfsTraverse = new ArrayList<ch5p2_GraphNode>();
	}// end constructor

	// addNode()
	// Add a city node to the graph

	public void addNode(ch5p2_GraphNode node) {
		nodes.add(node);
	}// end addNode

	// addLink
	// Add link to the graph

	public void addLink(ch5p2_Link link) {
		links.add(link);
	}// end addLink

	// dfsTraverse()
	// perform depth-first search on the graph

	public void dfsTraverse(ch5p2_GraphNode from, ch5p2_GraphNode to) {
		boolean matched;
		ch5p2_Link found;

		// determine if there is a link between from and to
		// if there is a match then add the link to the travelStack and
		// add the nodes to dfsPath
		// This will ultimately repeated by the end of the search

		matched = match(from, to);
		if (matched) {
			travelStack.push(new ch5p2_Link(from, to));
			dfsPath.add(new ch5p2_GraphNode(to.cityName,to.xLocation, to.yLocation));
			dfsPath.add(new ch5p2_GraphNode(from.cityName,from.xLocation, from.yLocation));
			return;
		}

		// if there is no match found you could another path findings
		found = find(from);

		// if you find a new connection then you could add it to the travelStack
		// and
		// and the start node to dfsPath
		// recursively call dfsTraverse with the link's to as start and our
		// destination as the end

		if (found != null) {
			travelStack.push(new ch5p2_Link(from, to));
			dfsTraverse(found.to, to);
			dfsPath.add(new ch5p2_GraphNode(from.cityName,from.xLocation, from.yLocation));
		}

		// backtrack if you cannot find a new connection
		else if (travelStack.size() > 0) {
			found = travelStack.pop();
			dfsTraverse(found.from, found.to);
			dfsPath.add(new ch5p2_GraphNode(from.cityName,from.xLocation, from.yLocation));
		}
	}// end dfsTraverse()

	// find() method is used to
	// find the next link to try exploring

	public ch5p2_Link find(ch5p2_GraphNode from) {

		// iterate through the list of links
		for (int a = 0; a < links.size(); a++) {
			// link found
			if (links.get(a).from.equals(from) && !links.get(a).skip) {
				ch5p2_Link foundList = new ch5p2_Link(links.get(a).from,
						links.get(a).to);
				// mark this link as used so we don't match it again
				links.get(a).skip = true;
				return foundList;
			}
		}
		return null; // not found
	}// end find()

	// match() method is used to determine if there is a link between a starting
	// node and an ending node

	public boolean match(ch5p2_GraphNode from, ch5p2_GraphNode to) {

		// iterate through list of links
		for (int a = links.size() - 1; a >= 0; a--) {
			if (links.get(a).from.equals(from) && links.get(a).to.equals(to)
					&& !links.get(a).skip) {
				links.get(a).skip = true;
				return true;
			}
		}
		return false;
	}// end match()
}// end Graph.java
