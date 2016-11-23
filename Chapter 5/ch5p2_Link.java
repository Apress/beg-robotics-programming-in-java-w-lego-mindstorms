//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch5p2_Link.java
//Represents a link between two GraphNodes
//***********************************************************************************

public class ch5p2_Link {

	ch5p2_GraphNode from;
	ch5p2_GraphNode to;

	// boolean skip is used for traversal to determine if the path has already
	// been visited or not
	boolean skip;

	public ch5p2_Link(ch5p2_GraphNode from, ch5p2_GraphNode to) {
		this.from = from;
		this.to = to;
		skip = false;
	}
}
