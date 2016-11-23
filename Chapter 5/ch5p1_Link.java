//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch5p1_Link.java
//Represents a link between two GraphNodes
//***********************************************************************************

public class ch5p1_Link {

	ch5p1_GraphNode from;
	ch5p1_GraphNode to;

	// boolean skip is used for traversal to determine if the path has already
	// been visited or not
	boolean skip;

	public ch5p1_Link(ch5p1_GraphNode from, ch5p1_GraphNode to) {
		this.from = from;
		this.to = to;
		skip = false;
	}
}
