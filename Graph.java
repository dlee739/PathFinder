import java.util.ArrayList;

public class Graph implements GraphADT {
	private Node V[]; // Array of vertices
	private Edge E[][]; // 2d array (matrix) of Edges
	private int numV; // number of vertices
	
	// Constructor
	// Graph(n): creates an empty graph with n nodes and no edges. This is the constructor for the class.
	// The names of the nodes are 0, 1, . . . , n−1
	Graph(int n) {
		V = new Node[n];
		for (int i = 0; i < n; i++) { // each and every nodes initialized
			V[i] = new Node(i);
		}
		E = new Edge[n][n];
		numV = n;
	}
	
	// insertEdge(u, v, edgeType): adds to the graph an edge connecting nodes u and v. 
	// The type for this new edge is as indicated by the last parameters. 
	// This method throws a GraphException if either node does not exist or if there
	// is already an edge connecting the given nodes
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		if (u == null || v == null) {
			throw new GraphException("u or v doesn't exist.");
		}
		int nu = u.getName(); // name of u
		int nv = v.getName(); // name of v
		
		// either node does not exist or there is already an edge
		if ((nu < 0 || nu >= numV) || (nv < 0 || nv >= numV) || (E[nu][nv] != null)) {
			throw new GraphException("Either node DNE, or there is already an edge.");
		}
		
		E[nu][nv] = new Edge(u, v, edgeType); // add edge from u to v
		E[nv][nu] = new Edge(v, u, edgeType); // add edge from v to u
	}
	
	// getNode(name): returns the node with the specified name. If no node with this name
	// exists, the method should throw a GraphException
	public Node getNode(int name) throws GraphException {
		if (name < 0 || name >= numV) { // name out of range
			throw new GraphException("No node with this name exists.");
		}
		return V[name];
	}
	
	// incidentEdges(u): returns a list storing all the edges incident on node u. It
	// returns null if u does not have any edges incident on it
	public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
		int nu = u.getName(); // name of u
		if (nu < 0 || nu >= numV) { // u is not a node of this graph
			throw new GraphException("No node with this name exists.");
		}
		ArrayList<Edge> list = new ArrayList<Edge>(); // array list to store all edges
		for (int i = 0; i < numV; i++) {
			if (E[nu][i] != null) { // edge found
				list.add(E[nu][i]); // edge added to the list
			}
		}
		if (list.isEmpty()) { // u doesn't have any edges incident on it
			return null;
		}
		return list;
	}
	
	// Edge getEdge(u, v): returns the edge connecting nodes u and v. This method throws
	// a GraphException if there is no edge between u and v
	public Edge getEdge(Node u, Node v) throws GraphException {
		int nu = u.getName(); // name of u
		int nv = v.getName(); // name of v
		if ((nu < 0 || nu >= numV) || (nv < 0 || nv >= numV) ) { 
			// u or v is not a node of this graph
			throw new GraphException("No node with this name exists.");
		}
		if (E[nu][nv] == null) { // no edge found between u and v
			throw new GraphException("No edge between two nodes");
		}
		return E[nu][nv];
	}
	
	// areAdjacent(u, v): returns true if and only if nodes u and v are adjacent
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		int nu = u.getName(); // name of u
		int nv = v.getName(); // name of v
		if ((nu < 0 || nu >= numV) || (nv < 0 || nv >= numV) ) { 
			// u or v is not a node of this graph
			throw new GraphException("No node with this name exists.");
		}
		return E[nu][nv] != null;
	}
}

