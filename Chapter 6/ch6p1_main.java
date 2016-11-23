//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch6p1_main.java
//Driver class to call a breadth first search algorithm 
//to create navigation path from a start to an end node. 
//***********************************************************************************

import java.util.*;

class ch6p1_main {

	// This array holds the connection information between two cities.
	ch6p1_GraphNode cityLinks[] = new ch6p1_GraphNode[200];

	// number of path connections on the route graph
	int numConnections = 0;

	// backtrack stack to store the city nodes
	Stack closedList = new Stack();

	public static void main(String args[]) {

		// define the start city name
		String from = "A";

		// define the destination city name
		String to = "S";

		ch6p1_main bfsTraveral = new ch6p1_main();

		// set up the route graph
		bfsTraveral.graph();

		bfsTraveral.isNode(from, to);

		if (bfsTraveral.closedList.size() != 0) {
			System.out
					.println("the path from your current city to the destination city is: ");
			bfsTraveral.BFSroute(to);
		}

	}

	// Add link between two cities

	void addLink(String parent, String child) {
		if (numConnections < 200) {
			cityLinks[numConnections] = new ch6p1_GraphNode(parent, child);
			numConnections++;
		} else
			System.out.println("error to add link\n");
	}

	// Initialize the path link to construct the graph.

	void graph() {

		addLink("A", "B");
		addLink("A", "C");
		addLink("A", "D");
		addLink("A", "E");
		addLink("B", "F");
		addLink("B", "M");
		addLink("B", "G");
		addLink("C", "H");
		addLink("C", "I");
		addLink("D", "J");
		addLink("E", "K");
		addLink("E", "L");
		addLink("M", "N");
		addLink("M", "O");
		addLink("I", "P");
		addLink("P", "R");
		addLink("P", "S");
	}

	// determine to see if there is a link matched between startCity and endCity
	// if match found, return true, otherwise return false

	int matched(String from, String to) {
		for (int i = numConnections - 1; i > -1; i--) {
			if (cityLinks[i].startCity.equals(from)
					&& cityLinks[i].endCity.equals(to) && !cityLinks[i].visited) {

				// set up visited to true to prevent re-visit
				cityLinks[i].visited = true;

				// match found
				return 1;
			}
		}
		// match not found
		return 0;
	}

	// Given parent to find any child connected with this parent

	ch6p1_GraphNode find(String parent) {
		for (int i = 0; i < numConnections; i++) {
			if (cityLinks[i].startCity.equals(parent) && !cityLinks[i].visited) {

				ch6p1_GraphNode f = new ch6p1_GraphNode(cityLinks[i].startCity,
						cityLinks[i].endCity);

				// set up visited to true to prevent re-visit
				cityLinks[i].visited = true;

				// child (or leaf) returned
				return f;
			}
		}

		// if parent has no child return nothing
		return null;
	}

	// using breadth first search and determining if there is any route existing
	// in between startCity and endCity

	void isNode(String from, String to) {

		int directconn;
		ch6p1_GraphNode citynode;

		Stack resetList = new Stack();

		// determine if there is any direct link between from and to
		// if yes push the link of the two cities into closedList stack
		directconn = matched(from, to);
		if (directconn != 0) {
			closedList.push(new ch6p1_GraphNode(from, to));
			return;
		}

		// find all the children cities connected with the specified parent node

		while ((citynode = find(from)) != null) {
			resetList.push(citynode);

			// check further if there is any direct connection between the child
			// and grandchild

			if ((directconn = matched(citynode.endCity, to)) != 0) {
				resetList.push(citynode.endCity);
				closedList.push(new ch6p1_GraphNode(from, citynode.endCity));
				closedList.push(new ch6p1_GraphNode(citynode.endCity, to));
				return;
			}
		}

		// reset the visited boolean to unvisited and do the breadth first
		// search next

		for (int i = resetList.size(); i != 0; i--)
			resetSkip((ch6p1_GraphNode) resetList.pop());

		// then try the next neighboring city nodes
		citynode = find(from);
		if (citynode != null) {
			closedList.push(new ch6p1_GraphNode(from, to));
			isNode(citynode.endCity, to);
		} else if (closedList.size() > 0) {

			// trace back and try another link
			citynode = (ch6p1_GraphNode) closedList.pop();
			isNode(citynode.startCity, citynode.endCity);
		}
	}

	// reset visited field of specified parent city node

	void resetSkip(ch6p1_GraphNode citynode) {
		for (int i = 0; i < numConnections; i++)
			if (cityLinks[i].startCity.equals(citynode.startCity)
					&& cityLinks[i].endCity.equals(citynode.endCity))
				cityLinks[i].visited = false;
	}

	// Show the route obtained by the BFS algorithm

	void BFSroute(String to) {

		int num = closedList.size();

		Stack reverseList = new Stack();

		ch6p1_GraphNode citynode;

		// Reverse the stack to show the path

		for (int i = 0; i < num; i++)
			reverseList.push(closedList.pop());

		for (int i = 0; i < num; i++) {
			citynode = (ch6p1_GraphNode) reverseList.pop();
			System.out.print(citynode.startCity + " -> ");
		}
		System.out.println(to);
	}

}