//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch6p2_Graph.java
//Represents GraphNodes connected through Links, including method for doing a breadth-first 
//search traversal of the graph.
//the method bfsTraverse creates a bfsPath list which the robot will follow to demonstrate 
//how the breadth-first search works.
//***********************************************************************************

import java.util.ArrayList;

public class ch6p2_Graph {
	// nodes and links define the physical creation of your Graph
	ArrayList<ch6p2_GraphNode> nodes;
	ArrayList<ch6p2_Link> links;

	// a list used for traversing
	ArrayList<ch6p2_Link> bfsTraverse;

	// List used to define where you would like to move
	ArrayList<ch6p2_GraphNode> bfsPath = new ArrayList<ch6p2_GraphNode>();

	// Constructor of ch6p2_Graph class
	public ch6p2_Graph() {
		nodes = new ArrayList<ch6p2_GraphNode>();
		links = new ArrayList<ch6p2_Link>();
		bfsTraverse = new ArrayList<ch6p2_Link>();

	}// end constructor

	// addNode()
	// Add a city node to the graph

	public void addNode(ch6p2_GraphNode node) {
		nodes.add(node);
	}// end addNode

	// addLink
	// Add link to the graph

	public void addLink(ch6p2_Link link) {
		links.add(link);
	}// end addLink

	// bfsTraverse()
	// perform breadth-first search on the graph

	public void bfsTraverse(ch6p2_GraphNode from, ch6p2_GraphNode to) {

		ArrayList<ch6p2_Link> resetList = new ArrayList<ch6p2_Link>();
		boolean matched = false;
		ch6p2_Link l;

		// determine if there is a link between from and to
		// if there is a match then add the link to the travelStack and
		// add the nodes to bfsPath
		// This will ultimately repeated by the end of the search

		matched = match(from, to);

		// add to bfsTraverse if match is found
		if (matched) {
			bfsTraverse.add(new ch6p2_Link(from, to));
			return;
		}

		// continue while links exist
		while ((l = find(from)) != null) {
			resetList.add(l);
			if ((matched = match(l.to, to))) {
				resetList.add(new ch6p2_Link(l.from, to));
				bfsTraverse.add(new ch6p2_Link(from, l.to));
				bfsTraverse.add(new ch6p2_Link(l.to, to));
				return;
			}
		}

		for (int i = resetList.size(); i != 0; i--) {
			resetSkip((ch6p2_Link) resetList.remove(i - 1));
		}

		// if you find a new connection then you could add it to the travelStack
		// and
		// and the start node to bfsPath
		// recursively call bfsTraverse with the link's to as start and our
		// destination as the end

		l = find(from);
		if (l != null) {
			bfsTraverse.add(new ch6p2_Link(from, to));
			bfsPath.add(new ch6p2_GraphNode(from.cityName, from.xLocation,
					from.yLocation));
			bfsTraverse(l.to, to);
		}

		// backtrack if you cannot find a new connection
		else if (bfsTraverse.size() > 0) {
			l = (ch6p2_Link) bfsTraverse.remove(bfsTraverse.size() - 1);
			bfsPath.remove(bfsPath.size() - 1);
			bfsTraverse(l.from, l.to);
		}
	}// end bfsTraverse

	// resetSkip
	// when backtracking reset skip flag so we can visit nodes again
	public void resetSkip(ch6p2_Link l) {
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).from.equals(l.from)
					&& links.get(i).to.equals(l.to)) {
				links.get(i).skip = false;
			}
		}
	}

	// match() method is used to determine if there is a link between a starting
	// node and an ending node

	public boolean match(ch6p2_GraphNode from, ch6p2_GraphNode to) {
		// iterate through list of links
		for (int x = links.size() - 1; x >= 0; x--) {
			if (links.get(x).from.equals(from) && links.get(x).to.equals(to)
					&& !links.get(x).skip) {
				links.get(x).skip = true;
				return true;
			}
		}
		return false;
	}// end match

	// find() method is used to
	// find the next link to try exploring

	public ch6p2_Link find(ch6p2_GraphNode from) {

		// iterate through the list of links
		for (int x = 0; x < links.size(); x++) {
			// link found
			if (links.get(x).from.equals(from) && !links.get(x).skip) {
				ch6p2_Link l = new ch6p2_Link(links.get(x).from,
						links.get(x).to);
				links.get(x).skip = true; // mark this link as used so we don't
											// match it again
				return l;
			}
		}
		return null; // not found
	}// end find()
}// end Graph.java
