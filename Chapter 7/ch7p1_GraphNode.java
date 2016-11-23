//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch7p1_GraphNode.java
//Represents name and coordinates of a node on a graph
//***********************************************************************************

public class ch7p1_GraphNode {
	String cityName;
	int xLocation, yLocation;
	
	public ch7p1_GraphNode(String cityName, int xLocation, int yLocation) {
		this.cityName = cityName;
		this.xLocation = xLocation;
		this.yLocation = yLocation;

	}

	public String toString() {
		return cityName + " ("+xLocation +"," + yLocation + ")";
	}
}
