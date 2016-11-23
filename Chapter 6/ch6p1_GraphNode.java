//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch6p1_GraphNode.java
//Represents name of a graph
//***********************************************************************************

public class ch6p1_GraphNode {

	String startCity;
	String endCity;

	// to determine if the city has been visited or not
	boolean visited;

	ch6p1_GraphNode(String s, String d) {
		startCity = s;
		endCity = d;
		visited = false;
	}
}
