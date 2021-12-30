import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Solver {

	private Graph g; // graph
	private int entrance; // entrance of the graph
	private int exit; // exit of the graph
	private int num_blast; // number of blast bombs
	private int num_melt; // number of melt bombs
	private Stack<Node> P; // stack of path

	// Constructor
	// Solver(inputFile): constructor for building a labyrinth from the input file.
	// If the input file does not exist, this method should throw a
	// LabyrinthException.
	Solver(String inputFile) throws LabyrinthException {
		BufferedReader in; // Buffered reader
		int W = 0; // Width
		int L = 0; // length
		int mapW = 0; // map width
		int mapL = 0; // map length
		char map[][] = null; // 2d char array of all rooms and edges
		try {
			in = new BufferedReader(new FileReader(inputFile)); // read from input file
			in.readLine(); // S is discarded
			W = Integer.parseInt(in.readLine()); // width of the labyrinth
			L = Integer.parseInt(in.readLine()); // length of the labyrinth
			g = new Graph(W * L); // total number of nodes = W*L
			num_blast = Integer.parseInt(in.readLine());
			num_melt = Integer.parseInt(in.readLine());

			// Store the content of the labyrinth in 2d array of chars
			mapL = 2 * L - 1; // map length
			mapW = 2 * W - 1; // map width
			map = new char[mapL][mapW];
			// temporarily convert the text into 2d char array
			for (int i = 0; i < mapL; i++) {
				String s = in.readLine(); // temporary string line
				for (int j = 0; j < mapW; j++) {
					map[i][j] = s.charAt(j);
					if (map[i][j] == 'e') { // entrance
						entrance = (i / 2) * W + (j / 2);
					} else if (map[i][j] == 'x') { // exit
						exit = (i / 2) * W + (j / 2);
					} 
				}
			}
			in.close(); // close buffered reader
		} catch (FileNotFoundException e) { // file not found
			throw new LabyrinthException(inputFile + " not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Add the edges based on map
		for (int i = 0; i < mapL; i++) {
			for (int j = 0; j < mapW; j++) {
				// current position is a room or an unbreakable edge
				if (map[i][j] == 'e' || map[i][j] == 'x' || map[i][j] == 'o' || map[i][j] == '*') {
					continue;
				}

				// current position is an edge; determining type of the edge
				int type = 0; // type of the edge
				switch (map[i][j]) {
					case '-': case '|': // type 1: corridor
						type = 1;
						break;
					case 'b': case 'B': // type 2: brick wall
						type = 2;
						break;
					case 'r': case 'R': // type 3: rock wall
						type = 3;
						break;
					case 'm': case 'M': // type 4: metal wall
						type = 4;
						break;
					default:
						break;
				}

				// finding u and v (two nodes connected by the current edge
				int nu = 0, nv = 0; // names of node u and v
				switch (map[i][j]) {
					case '-': case 'b': case 'r': case 'm': // case 1: horizontal edges
						nu = (i / 2) * W + ((j - 1) / 2); // node to left
						nv = (i / 2) * W + ((j + 1) / 2); // node to right
						break;
					case '|': case 'B': case 'R': case 'M': // case 2: vertical edges
						nu = ((i - 1) / 2) * W + (j / 2); // node up
						nv = ((i + 1) / 2) * W + (j / 2); // node under
						break;
					default:
						break;
				}
				
				try { // insert the edge in g
					g.insertEdge(g.getNode(nu), g.getNode(nv), type);
				} catch (GraphException e) {
					e.printStackTrace();
				}
			}
		}
		P = new Stack<Node>(); // Stack of path taken 
	}

	// getGraph(): returns a reference to the graph representing the labyrinth.
	// Throws a Labyrinth Exception if the graph is not defined
	public Graph getGraph() throws LabyrinthException {
		if (g == null) {
			throw new LabyrinthException("The graph is not defined.");
		}
		return g;
	}
	
	// path(s, d) true if there is a path from s to d; false otherwise
	private boolean path(Node s, Node d) {
		s.setMark(true); // marked
		P.push(s); // s pushed into stack
		if (s == d) { // destination found
			return true;
		}
		
		ArrayList<Edge> edges = null; // list of edges
		try {
			edges = g.incidentEdges(s); // get edges
		} catch (GraphException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i); // get current edge
			Node u = e.secondEndpoint(); // get the second end point
			if (!u.getMark()) { // u is not marked
				int type = e.getType(); // type of current edge
				if (type == 1) { // case 1: corridor
					if (path(u, d)) 
						return true;
				} else if (type == 2 && num_blast >= 1) { // case 2: brick wall
					num_blast--; // single blast bomb used
					if (path(u, d)) 
						return true;
					num_blast++; // gain back a blast bomb
				} else if (type == 3 && num_blast >= 2) { // case 3: rock wall
					num_blast -= 2; // 2 blast bomb used
					if (path(u, d)) 
						return true;
					num_blast += 2; // gain back 2 blast bomb
				} else if (type == 4 && num_melt >= 1) { // case 4: metal wall
					num_melt--; // single melt bomb used
					if (path(u, d)) 
						return true;
					num_melt++; // gain back a melt bomb
				}
			}
		}		
		P.pop(); // pop stack
		s.setMark(false); // unmark s
		return false;
	}

	// solve(): returns a java Iterator containing the nodes along the path from the
	// entrance
	// to the exit of the labyrinth, if such a path exists. If the path does not
	// exist,
	// this method returns the value null
	public Iterator solve() {
		Node e = null; // entrance node
		Node x = null; // exit node
		try {
			e = g.getNode(entrance);
			x = g.getNode(exit); 
			ArrayList<Edge> edges = g.incidentEdges(e); // get list of initial edges
			if (edges == null) { // no initial edges (i.e. entrance is completely isolated)
				return null;
			}
		} catch (GraphException ge) {
			ge.printStackTrace();
		}
		if (path(e, x)) { // path exists
			return P.iterator(); // iterator returned
		}
		return null;
	}
}
