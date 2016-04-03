package crackingTheCoding;

import java.util.LinkedList;

public class GraphSearch_BFS_DFS {

	// total number of vertices
	private static int numOfVertices;
	// this array of linkedlist will store the neighbors
	private static LinkedList<Integer> adj[];

	// passing the number of vertices will initialize the graph
	public GraphSearch_BFS_DFS(int v) {
		numOfVertices = v;
		adj = new LinkedList[v];

		// set linkedlist for every vertice
		for (int i = 0; i < numOfVertices; i++) {
			adj[i] = new LinkedList<Integer>();
		}
	}

	void addEdge(int u, int w) {
		adj[u].add(w);
	}

	static boolean searchBFS(GraphSearch_BFS_DFS graph, int start, int end) {
		// initialize visited array keeping track of the visited nodes
		boolean[] visited = new boolean[numOfVertices];

		// mark the source node as visited
		visited[start] = true;

		// init queue that will save the nodes to be addressed at each level
		LinkedList<Integer> queue = new LinkedList<Integer>();
		LinkedList<Integer> adjacentNodes;

		// mark the source of the graph to the queue
		queue.add(start);

		while (queue.size() != 0) {
			// deques the first element in the queue(head of the linked list)
			int val = queue.poll();

			// this will have the neighbors of first element in the queue
			adjacentNodes = adj[val];

			// we need to iterate all the values of linked list
			for (int i = 0; i < adjacentNodes.size(); i++) {

				// searching logic
				if (end == adjacentNodes.get(i)) {
					return true;
				}

				// queueing up all the neighbors of current node
				queue.add(adjacentNodes.get(i));
				// marking all the visited nodes
				visited[adjacentNodes.get(i)] = true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		GraphSearch_BFS_DFS g = new GraphSearch_BFS_DFS(4);
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 3);
		System.out.println(searchBFS(g, 0, 3));
	}
}
