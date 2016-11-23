//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch7p1_Link.java
//Represents a link between two GraphNodes
//***********************************************************************************

public class ch7p1_Link {

	ch7p1_GraphNode from;
	ch7p1_GraphNode to;
	
	int distance;
	
	// boolean skip is used for traversal to determine if the path has already
	// been visited or not
	boolean skip;

	public ch7p1_Link(ch7p1_GraphNode from, ch7p1_GraphNode to, int d) {
		this.from = from;
		this.to = to;
		distance = d;
		skip = false;
	}
}
