//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch8p1_edge.java
//edge class including the parent node and child node 
//and the edge length between two nodes 
//***********************************************************************************

public class ch8p1_edge {
	private String parentNode;
	private String childNode;
	private int edgeLength;

	public ch8p1_edge(String parentNode, String childNode, int edgeLength) {
		this.parentNode = parentNode;
		this.childNode = childNode;
		this.edgeLength = edgeLength;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getChildNode() {
		return childNode;
	}

	public void setChildNode(String childNode) {
		this.childNode = childNode;
	}

	public int getEdgeLength() {
		return edgeLength;
	}

	public void setEdgeLength(int edgeLength) {
		this.edgeLength = edgeLength;
	}

}
