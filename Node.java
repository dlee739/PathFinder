
public class Node {
	private int name; // the name of the node
	private boolean marked; // true if the node is marked
	
	// Constructor
	// Creates an unmarked node with the given name
	Node(int nodeName) {
		this.name = nodeName;
		this.marked = false;
	}
	
	// setMark(mark): marks the node with the specified value
	public void setMark(boolean mark) {
		this.marked = mark;
	}
	
	// getMark(): returns the value with which the node has been marked
	public boolean getMark() {
		return this.marked;
	}
	
	// getName(): returns the name of the node.
	public int getName() {
		return this.name;
	}
	
	// equals(otherNode): returns true of this node has the same name as otherNode;
	// 	 returns false otherwise
	public boolean equals(Node otherNode) {
		return this.name == otherNode.name;
	}
}
