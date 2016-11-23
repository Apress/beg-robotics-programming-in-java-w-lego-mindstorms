//***********************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3 		ch5p1_main.java
//Driver class to set up map using ch5p1_GraphNode, ch5p1_Link, and ch5p1_Graph classes.
//Calls a depth first search in ch5p1_Graph class to create navigation path from a start
//and end node. 
//***********************************************************************************

public class ch5p1_main {

	public static void main(String[] args) {

		// These objects used to define what your graph looks like
		ch5p1_Graph searchGraph = new ch5p1_Graph();
		ch5p1_GraphNode A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, R, S;
		ch5p1_Link AB, AC, AD, AE, BF, BM, BG, CH, CI, DJ, EK, EL, MN, MO, IP, PR, PS;

		// define each node
		A = new ch5p1_GraphNode("A");
		B = new ch5p1_GraphNode("B");
		C = new ch5p1_GraphNode("C");
		D = new ch5p1_GraphNode("D");
		E = new ch5p1_GraphNode("E");
		F = new ch5p1_GraphNode("F");
		G = new ch5p1_GraphNode("G");
		H = new ch5p1_GraphNode("H");
		I = new ch5p1_GraphNode("I");
		J = new ch5p1_GraphNode("J");
		K = new ch5p1_GraphNode("K");
		L = new ch5p1_GraphNode("L");
		M = new ch5p1_GraphNode("M");
		N = new ch5p1_GraphNode("N");
		O = new ch5p1_GraphNode("O");
		P = new ch5p1_GraphNode("P");
		R = new ch5p1_GraphNode("R");
		S = new ch5p1_GraphNode("S");

		// define which GraphNodes are connected
		AB = new ch5p1_Link(A, B);
		AC = new ch5p1_Link(A, C);
		AD = new ch5p1_Link(A, D);
		AE = new ch5p1_Link(A, E);
		BF = new ch5p1_Link(B, F);
		BM = new ch5p1_Link(B, M);
		BG = new ch5p1_Link(B, G);
		CH = new ch5p1_Link(C, H);
		CI = new ch5p1_Link(C, I);
		DJ = new ch5p1_Link(D, J);
		EK = new ch5p1_Link(E, K);
		EL = new ch5p1_Link(E, L);
		MN = new ch5p1_Link(M, N);
		MO = new ch5p1_Link(M, O);
		IP = new ch5p1_Link(I, P);
		PR = new ch5p1_Link(P, R);
		PS = new ch5p1_Link(P, S);

		// add all nodes and links to your graph object
		searchGraph.addNode(A);
		searchGraph.addNode(B);
		searchGraph.addNode(C);
		searchGraph.addNode(D);
		searchGraph.addNode(E);
		searchGraph.addNode(F);
		searchGraph.addNode(G);
		searchGraph.addNode(H);
		searchGraph.addNode(I);
		searchGraph.addNode(J);
		searchGraph.addNode(K);
		searchGraph.addNode(L);
		searchGraph.addNode(M);
		searchGraph.addNode(N);
		searchGraph.addNode(O);
		searchGraph.addNode(P);
		searchGraph.addNode(R);
		searchGraph.addNode(S);

		searchGraph.addLink(AB);
		searchGraph.addLink(AC);
		searchGraph.addLink(AD);
		searchGraph.addLink(AE);
		searchGraph.addLink(BF);
		searchGraph.addLink(BM);
		searchGraph.addLink(BG);
		searchGraph.addLink(CH);
		searchGraph.addLink(CI);
		searchGraph.addLink(DJ);
		searchGraph.addLink(EK);
		searchGraph.addLink(EL);
		searchGraph.addLink(MN);
		searchGraph.addLink(MO);
		searchGraph.addLink(IP);
		searchGraph.addLink(PR);
		searchGraph.addLink(PS);

		// run depth-first search to get from start to destination
		searchGraph.dfsTraverse(
				searchGraph.nodes.get(searchGraph.nodes.indexOf(A)),
				searchGraph.nodes.get(searchGraph.nodes.indexOf(S)));

		// display path created using dfsTraverse
		// This will be display the path from start to destination

		System.out.println("the path from your current city to the destination city is: ");
		for (int i = searchGraph.dfsPath.size() - 1; i >= 0; i--) {
			if(i!=0)
				System.out.print(searchGraph.dfsPath.get(i).cityName + "->");
			else
				System.out.print(searchGraph.dfsPath.get(i).cityName);
		}
	}
}
