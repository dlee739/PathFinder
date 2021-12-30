
public class Edge {
	// The two nodes connected by the edge
	private Node firstEndpoint; // first end point
	private Node secondEndpoint; // second end point
	private int type; // the type of an edge
	/* Type:	
	  	– 1: corridor
		– 2: brick wall
		– 3: rock wall
		– 4: metal wall
	 **/
	
	// Constructor
	// Creates and edge of the given type connecting nodes u
	// and v. For example let edge (u, v) represent a corridor of the labyrinth. 
	// The first endpoint of this edge is node u and the second endpoint is node v; 
	// the type of the edge is 1.
	Edge(Node u, Node v, int edgeType) {
		firstEndpoint = u;
		secondEndpoint = v;
		type = edgeType;
	}
	
	// firstEndpoint(): returns the first endpoint of the edge
	public Node firstEndpoint() {
		return firstEndpoint;
	}
	
	// secondEndpoint(): returns the second endpoint of the edge
	public Node secondEndpoint() {
		return secondEndpoint;
	}
	
	// getType(): returns the type of the edge
	public int getType() {
		return type;
	}
	
	// setType(newType): sets the type of the edge to the specified value
	public void setType(int newtype) {
		type = newtype;
	}
	
	// equals(otherEdge): returns true if this Edge object connects the same two nodes as
	// otherEdge
	public boolean equals(Edge otherEdge) {
		return (firstEndpoint.equals(otherEdge.firstEndpoint) &&
				secondEndpoint.equals(otherEdge.secondEndpoint)) ||
				(firstEndpoint.equals(otherEdge.secondEndpoint) &&
				secondEndpoint.equals(otherEdge.firstEndpoint));
	}
}
