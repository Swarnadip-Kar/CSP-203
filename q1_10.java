// Author : Swarnadip Kar
// Id : 12342210
// CSE Btech 2nd Year IIT Bhilai.


// This program implements DFS.

import java.util.*;

public class q1_10 {
	// Class representing an Edge with two vertices
	public static class Edge {
		int u, v;

		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}

		@Override
		public String toString() {
			return "(" + (u + 1) + ", " + (v + 1) + ")";
		}
	}

	// Creating class Graph to hold Graph-like objects
	public static class Graph {
		private int vertices;
		private List<List<Integer>> adjacencyList;
		private int[] parent;
		private int[] inTime;
		private int[] outTime;
		private boolean[] visited;
		private int time;

		private int visitCount;

		private List<Edge> treeEdges;
		private List<Edge> backEdges;
		private List<Edge> forwardEdges;
		private List<Edge> crossEdges;

		// Constructor
		public Graph(int vertices) {
			this.vertices = vertices;
			adjacencyList = new ArrayList<>();
			parent = new int[vertices];
			inTime = new int[vertices];
			outTime = new int[vertices];
			visited = new boolean[vertices];
			time = 0;

			visitCount=0;

			// Initialize adjacency list and edge lists
			for (int i = 0; i < vertices; i++) {
				adjacencyList.add(new ArrayList<>());
			}
			treeEdges = new ArrayList<>();
			backEdges = new ArrayList<>();
			forwardEdges = new ArrayList<>();
			crossEdges = new ArrayList<>();
		}

		// Function to add an edge to the graph
		public void addEdge(int u, int v) {
			adjacencyList.get(u).add(v);
		}

		// Sort adjacency lists to ensure DFS prioritizes lower-numbered nodes
		public void sortAdjacencyList() {
			for (List<Integer> adj : adjacencyList) {
				Collections.sort(adj);
			}
		}

		// Function to perform DFS and classify edges
		public void DFS(int vertex, int parentNode) {
			visited[vertex] = true;
			parent[vertex] = parentNode;
			inTime[vertex] = ++time;

			System.out.println("Visited vertex: " + (vertex + 1));

			for (int adjVertex : adjacencyList.get(vertex)) {
				if (!visited[adjVertex]) {
					treeEdges.add(new Edge(vertex, adjVertex));
					DFS(adjVertex, vertex);
				} else if (adjVertex != parentNode) {
					// Classify the type of edge
					if ((inTime[adjVertex] < inTime[vertex]) && (outTime[vertex] < outTime[adjVertex])) {
						backEdges.add(new Edge(vertex, adjVertex));
					} else if ((inTime[vertex] < inTime[adjVertex]) && (outTime[adjVertex] < outTime[vertex])) {
						forwardEdges.add(new Edge(vertex, adjVertex));
					} else {
						crossEdges.add(new Edge(vertex, adjVertex));
					}
				}
			}
			visitCount++;
			outTime[vertex] = ++time;
		}

		// Function to print DFS traversal information and edge classifications
		public void printDFSInfo() {
			System.out.println("DFS traversal completed. Node details:");
			System.out.println("Node    Parent    In-Time    Out-Time");
			for (int i = 0; i < vertices; i++) {
				System.out.print((i + 1) + "         ");
				if (parent[i] == -1) {
					System.out.print("-        	");
				} else {
					System.out.print((parent[i] + 1) + "       	");
				}
				System.out.print(inTime[i] + "        	" + outTime[i]);
				System.out.println();
			}

			// Print classified edges
			System.out.println("\nEdge Classifications:");
			System.out.println("Tree Edges: " + treeEdges);
			System.out.println("Back Edges: " + backEdges);
			System.out.println("Forward Edges: " + forwardEdges);
			System.out.println("Cross Edges: " + crossEdges);
		}

		// Print adjacency list representation
		public void printAdjacencyList() {
			System.out.println("Adjacency List:");
			for (int i = 0; i < vertices; i++) {
				System.out.print((i + 1) + ": ");
				for (int j : adjacencyList.get(i)) {
					System.out.print((j + 1) + " ");
				}
				System.out.println();
			}
		}
	}

	// Main function to take input from the user and create a graph
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// Take input for the number of vertices
		System.out.print("Enter the number of vertices: ");
		int vertices = sc.nextInt();

		// Create a Graph Object
		Graph graph = new Graph(vertices);

		// Take input for the number of edges
		System.out.print("Enter the number of edges: ");
		int edgesCount = sc.nextInt();

		// Take input for edges
		System.out.println("Enter the edges (u v) one by one: (u,v lies between 1 and " + vertices + " (inclusive).");
		for (int i = 0; i < edgesCount; i++) {
			int u = sc.nextInt();
			while ((u <= 0) || (u > vertices)) {
				System.out.println("Value of Vertex should lie between 1 and " + vertices + " (inclusive).");
				u = sc.nextInt();
			}
			int v = sc.nextInt();
			while ((v <= 0) || (v > vertices)) {
				System.out.println("Value of Vertex should lie between 1 and " + vertices + " (inclusive).");
				v = sc.nextInt();
			}
			graph.addEdge(u - 1, v - 1);
		}

		// Sort adjacency list to prioritize lower-numbered nodes in DFS
		graph.sortAdjacencyList();

		// Print adjacency list
		graph.printAdjacencyList();

		// Take input for the starting vertex for DFS
		System.out.print("Enter the starting vertex for DFS (between 1 and " + vertices + "): ");
		int startVertex = sc.nextInt();
		while (startVertex <= 0 || startVertex > vertices) {
			System.out.println("Starting vertex should lie between 1 and " + vertices + " (inclusive).");
			startVertex = sc.nextInt();
		}

		System.out.println("DFS traversal starting from vertex " + startVertex + ":");
		System.out.println("Printing order of visiting nodes for DFS Tree");
		graph.DFS(startVertex - 1, -1);

		int nextUnvisitedVertex=0;
		while(graph.visitCount!=vertices)
		{
			while((nextUnvisitedVertex<vertices)&&(graph.visited[nextUnvisitedVertex]))
			{
				nextUnvisitedVertex++;
			}
			if(nextUnvisitedVertex>=vertices)
			{
				break;
			}
			graph.DFS(nextUnvisitedVertex, -1);
		}

		// Print DFS information (parent, in-time, out-time, and edge classifications)
		graph.printDFSInfo();

		sc.close();
	}
}
