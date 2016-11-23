//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch7p1_main.java
//Driver class to set up map using ch7p1_GraphNode, ch7p1_Link, and ch7p1_Graph classes.
//Calls a hill climbing search in ch7p1_Graph class to create navigation path from a start
//and end node 
//***********************************************************************************

public class ch7p1_main {

	public static void main(String[] args) {

		// These objects used to define what your graph looks like
		ch7p1_Graph searchGraph = new ch7p1_Graph();
		ch7p1_GraphNode A, B, C, D, E, F, G, H;
		ch7p1_Link AB, AC, AD, BD, CB, CE, CF, DE, DH, FE, GE, HG;

		// define each node
		A = new ch7p1_GraphNode("A", 0, 0);
		B = new ch7p1_GraphNode("B", -10, 20);
		C = new ch7p1_GraphNode("C", -15, 20);
		D = new ch7p1_GraphNode("D", 0, 20);
		E = new ch7p1_GraphNode("E", 0, 30);
		F = new ch7p1_GraphNode("F", -15, 30);
		G = new ch7p1_GraphNode("G", 10, 20);
		H = new ch7p1_GraphNode("H", 10, 10);

		// define which GraphNodes are connected
		AB = new ch7p1_Link(A, B, dist(A.xLocation, A.yLocation, B.xLocation,
				B.yLocation));
		AC = new ch7p1_Link(A, C, dist(A.xLocation, A.yLocation, C.xLocation,
				C.yLocation));
		AD = new ch7p1_Link(A, D, dist(A.xLocation, A.yLocation, D.xLocation,
				D.yLocation));
		BD = new ch7p1_Link(B, D, dist(B.xLocation, B.yLocation, D.xLocation,
				D.yLocation));
		CB = new ch7p1_Link(C, B, dist(C.xLocation, C.yLocation, B.xLocation,
				B.yLocation));
		CE = new ch7p1_Link(C, E, dist(C.xLocation, C.yLocation, E.xLocation,
				E.yLocation));
		CF = new ch7p1_Link(C, F, dist(C.xLocation, C.yLocation, F.xLocation,
				F.yLocation));
		DE = new ch7p1_Link(D, E, dist(D.xLocation, D.yLocation, E.xLocation,
				E.yLocation));
		DH = new ch7p1_Link(D, H, dist(D.xLocation, D.yLocation, H.xLocation,
				H.yLocation));
		FE = new ch7p1_Link(F, E, dist(F.xLocation, F.yLocation, E.xLocation,
				E.yLocation));
		GE = new ch7p1_Link(G, E, dist(G.xLocation, G.yLocation, E.xLocation,
				E.yLocation));
		HG = new ch7p1_Link(H, G, dist(H.xLocation, H.yLocation, G.xLocation,
				G.yLocation));

		// add all nodes and links to your graph object
		searchGraph.addNode(A);
		searchGraph.addNode(B);
		searchGraph.addNode(C);
		searchGraph.addNode(D);
		searchGraph.addNode(E);
		searchGraph.addNode(F);
		searchGraph.addNode(G);
		searchGraph.addNode(H);

		searchGraph.addLink(AB);
		searchGraph.addLink(AC);
		searchGraph.addLink(AD);
		searchGraph.addLink(BD);
		searchGraph.addLink(CB);
		searchGraph.addLink(CE);
		searchGraph.addLink(CF);
		searchGraph.addLink(DE);
		searchGraph.addLink(DH);
		searchGraph.addLink(FE);
		searchGraph.addLink(GE);
		searchGraph.addLink(HG);

		// run hill climbing search to get from start to destination
		searchGraph.hillTraverse(
				searchGraph.nodes.get(searchGraph.nodes.indexOf(A)),
				searchGraph.nodes.get(searchGraph.nodes.indexOf(E)));

		// display path created using hillTraverse
		// This will be display the path from start to destination

		System.out
				.println("the path from your current city to the destination city is: ");
		for (int i = searchGraph.hillPath.size() - 1; i >= 0; i--) {
			int count = 0;
			if (i != 0) {
				for (int j = searchGraph.hillPath.size() - 1; j >= 0; j--) {
					if (searchGraph.hillPath.get(i).cityName == searchGraph.hillPath
							.get(j).cityName)
						count++;

				}
				if (count == 1)
					System.out.print(searchGraph.hillPath.get(i).cityName
							+ "->");
			} else
				System.out.print(searchGraph.hillPath.get(i).cityName);
		}

	}

	static int dist(int x1, int y1, int x2, int y2) {
		int distance = 0;

		distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		return distance;
	}
}
