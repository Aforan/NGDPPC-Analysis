package com.foran.graph;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.PrintStream;

import com.foran.graph.tools.BFS;
import com.foran.graph.tools.DFS;
import com.foran.graph.tools.Dijkstra;


/***
 * Graph class, provides an adjacency list representation of an undirected graph
 * the adjacency list is contained in an ArrayList called adjacencies and a number
 * of methods are given to work with the adjacency list to do simple operations 
 * such as removing and creating nodes and edges.
 * 
 * @author Andrew Foran
 * ***/


public class Graph {
	public static final int MAX_NODES = 36692;

	public ArrayList<Node> nodes;
	public ArrayList<Edge> edges;
	
	public int numNodes, numEdges;
	public boolean directed;
	public boolean mode;
	
	/***
	 * Class constructor, initializes some variables and the nodes and edges ArrayLists
	 * ***/
	
	
	public Graph(boolean mode) {
		numNodes = 0;
		numEdges = 0;
		directed = false;
		edges = new ArrayList<Edge>();

		this.mode = mode;

		if(mode) {
			nodes = new ArrayList<Node>();

			for(int i = 0; i < MAX_NODES; i++) {
				nodes.add(i, null);
			}

		}
		else nodes = new ArrayList<Node>();
	}
	
	
	/***
	 * Searches the adjacency list for a node with the same name as the name parameter
	 * and returns it if found, and null if it is not found.
	 *  
	 * @param name The name of the Node to be found.
	 * @return The Node with a matching name variable and null if it is not found in the adjacency list.
	 * ***/
	
	
	public Node getNode(String name) {
		if(mode) {
			int n = Integer.parseInt(name);
			return nodes.get(n);
		}

		for(Node n : nodes) {
			if(n.name.equals(name)) return n;
		}
		
		return null;
	}
	
	
	/***
	 * Searches the edge adjacency list for an edge with the same end points as the parameters
	 * and returns it if found, and null if it is not found.
	 *  
	 * @param s one endpoint of the Edge
	 * @param n one endpoint of the Edge
	 * @return The Edge with the endpoints equal to the parameters, or null if no such edge is found
	 * ***/
	
	
	public Edge getEdge(Node s, Node n) {
		/*if(mode) {
			return s.getEdgeTo(n);
		}

		for(Edge e : edges) {
			if(e.contains(s) && e.contains(n)) return e;
		}*/
		
		return s.getEdgeTo(n);
	}
	
	
	/***
	 * Inserts a given Node into the adjacency list of the Graph object if another node with the same 
	 * name is not already in the list.
	 * 
	 * @param n The Node to be inserted into the Adjacency list
	 * @return True if the node was successfully inserted and false otherwise
	 * ***/
	
	
	public boolean insertNode(Node n) {
		if(mode) {
			int num = Integer.parseInt(n.name);
			if(nodes.get(num) == null) {
				numNodes++;
				nodes.set(num, n);
				return true;
			} else return false;
		} else if(getNode(n.name) == null) {
			numNodes++;
			return nodes.add(n);
		}
		else return false;
	}
	
	
	
	/***
	 * Removes a given Edge from the adjacency list by calling the over loaded removeEdge method 
	 * on the end points of the edge.
	 * 
	 * @param e The Edge to be Removed
	 * @return true if the edge was removed successfully and false otherwise
	 * @see Graph.removeEdge(Node n0, Node n1)
	 * ***/
	
	
	public boolean removeEdge(Edge e) {
		numEdges--;
		return removeEdge(e.n0, e.n1);
	}
	
	
	/***
	 * Removes the given nodes from the other nodes adjacency list by successively calling Node's remove 
	 * method on the opposite nodes.
	 * 
	 * @param n0 The first end point of the edge to be removed.
	 * @param n1 The second end point of the edge to be removed.
	 * @return True if the edge was removed successfully and False otherwise. 
	 * ***/
	
	
	public boolean removeEdge(Node n0, Node n1) {
		if(n0.contains(n1) && n1.contains(n0)) {
			for(Edge e : edges) {
				if((e.n0 == n0 || e.n1 == n0) && (e.n0 == n1 || e.n1 == n1)) {
					n0.removeEdge(e);
					n1.removeEdge(e);
					
					edges.remove(e);
					break;
				}
			}
			n0.remove(n1);
			n1.remove(n0);
			numEdges--;
			return true;
		}
		else return false;
	}
	
	
	/***
	 * Inserts an edge into the Edge adjacency list and into the respective Node adjecency lists.
	 * First checks to make sure that no such edge exists and if it doesn't, adds the edge and
	 * modifies the parameters' adjacency lists.
	 * 
	 * @param n1 An endpoint of the edge to be added.
	 * @param n2 An endpoint of the edge to be added.
	 * @return True if the edge was added successfully and False otherwise. 
	 * ***/
	
	
	public boolean insertEdge(Node n1, Node n2) {
		if(!n1.contains(n2) && !n2.contains(n1)) {
			n1.insertNode(n2);
			n2.insertNode(n1);
			
			Edge e = new Edge(n1, n2);
			edges.add(e);
			
			n1.addEdge(e);
			n2.addEdge(e);
			
			numEdges++;
			return true;
		} else return false;
	}

	public boolean insertEdge(Node n1, Node n2, double weight) {
		if(!n1.contains(n2) && !n2.contains(n1)) {
			n1.insertNode(n2);
			n2.insertNode(n1);
			
			Edge e = new Edge(n1, n2, weight);
			edges.add(e);
			
			n1.addEdge(e);
			n2.addEdge(e);
			
			numEdges++;
			return true;
		} else return false;
	}
	
	
	/***
	 * Removes the given node from the Graphs' adjacency list.  First looks through all nodes in the 
	 * adjacency list and if a node contains n in its adjacency list removes it.  Then looks through
	 * all edges in the edge list and if the edge contains n it is removed from the list.  Finally
	 * removes n from the Graph's adjacency list after the rest of the Graph has been modified accordingly.
	 * 
	 * @param n The Node to be removed.
	 * ***/
	
	
	public void removeNode(Node n) {
		//look through the list of nodes, if a node contains n in its' adjacency list and 
		//is not the node to be deleted, delete n from the adjacency list
		for(Node a : nodes) {
			if(a != n && a.contains(n)) {
				a.remove(n);
			}
		}
		
		//look through the list of edges, if an edge contains n, remove it
		for(int i = 0; i < edges.size() && i >= 0; i++) {
			Edge e = edges.get(i);
			
			if(e.n0 == n|| e.n1 == n) {
				System.out.println("Removing Edge: " + e.toString());
				edges.remove(e);
				i--;
			}
		}
		
		//remove n from the list of nodes
		nodes.remove(n);
		numNodes--;
	}
	
	
	/***
	  * Helper method to return the adjacency list of a given Node
	  * 
	  * @param n the Node whose adjacency list is to be returned
	  * @return n's adjacency list
	  * ***/
	
	
	public ArrayList<Node> incidentEdges(Node n) {
		return n.adjacencies;
	}
	
	
	/***
	 * Method to determine whenther two nodes are adjacent or not.  Checks to see whether the nodes
	 * are adjacent by calling Node's contains() method on the parameters.  If the node parameters are 
	 * found in each others adjacency lists returns true and false otherwise.
	 * 
	 * @param n1 One Node to determine the adjacency for.
	 * @param n2 One Node to determine the adjacency for.
	 * @return True if the nodes are adjacent and False otherwise.
	 * ***/
	
	
	public boolean areAdjacent(Node n1, Node n2) {
		if(n1.contains(n2) && n2.contains(n1)) return true;
		else return false;
	}
	
	
	/***
	* Helper method that determines whethere or not a Node is contained within the Graph, if so return true, else return false
	*
	* @param name The name of the Node to search for
	* @return True if the node is found in the Graph and False otherwise
	* ***/


	public boolean contains(String name) {
		if(mode) {
			int n = Integer.parseInt(name);
			return (nodes.get(n)!=null);
		}


		if(getNode(name) == null) return false;
		else return true;
	}

	/***
	 * Helper method to return a String containing all the nodes followed by their adjacencies.  Each node
	 * is seperated by a newline.
	 * 
	 * @return A String containing all the Nodes followed by their adjacencies and seperated by newlines.
	 * ***/
	
	
	private String nodesToString() {
		for(int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);

			System.out.println(n.name + ": " + n.adjToString() + "\n");
		}

		return "";
	}

	private String nodesToString(PrintStream p) {
		PrintStream stdout = System.out;
		System.setOut(p);
		for(int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);

			System.out.println(n.name + ": " + n.adjToString() + "\n");
		}

		System.setOut(stdout);
		return "";
	}
	
	
	/***
	 * Helper method to return a String containing all the Edges and their endpoints.  Returns a string seperated by 
	 * newlines in which each edge is represented as endpointa -> endpointb
	 * 
	 *  @return A String containing all the Edges converted to Strings and seperated by newlines.
	 * ***/
	
	
	public String edgesToString() {
		String r = "Edges:\n";
		
		for(Edge e : edges) {
			r += e.toString() + "\n";
		}
		
		return r;
	}
	
	
	public void printGraph() {
		nodesToString();
	}

	public void printEdges(PrintStream p) {
		PrintStream stdout = System.out;

		System.setOut(p);

		for(Edge e : edges) {
			System.out.println(e.toString());
		}
		System.setOut(stdout);
	}

	public void printGraph(PrintStream p) {
		nodesToString(p);
	}


	public static void main(String[] args) {
		Graph g = new Graph(false);
		boolean running = true;
		Scanner in = new Scanner(System.in);
		
		while(running) {
			System.out.println("1. Add Node");
			System.out.println("2. Add edge");
			System.out.println("3. Remove Node");
			System.out.println("4. Remove Edge");
			System.out.println("5. Print Adjacency List");
			
			String s = in.next();
			
			switch(s) {
			case "1": 
				System.out.println("Enter the name of the node you wish to insert:");
				String t = in.next();
				g.insertNode(new Node(t));
				break;
			case "2": 
				String n1, n2;
				
				System.out.println("Enter the name of Node 1:");
				n1 = in.next();
				
				System.out.println("Enter the name of Node 2:");
				n2 = in.next();
				
				Node node0 = g.getNode(n1);
				Node node1 = g.getNode(n2);
				
				if(node0 != null && node1 != null) 
					g.insertEdge(g.getNode(n1), g.getNode(n2));
				else 
					System.out.println("One or more nodes not found, did not insert an edge!");
				break;
			case "3": 
				System.out.println("Enter the name of the node you wish to delte:");
				String n = in.next();
				Node n0 = g.getNode(n);
				
				if(n0 != null)
					g.removeNode(n0);
				else 
					System.out.println("Node " + n + " not found, did Not delete anything");
				break;
			case "4": 
				System.out.println("Enter the name of Node 1:");
				n1 = in.next();
				
				System.out.println("Enter the name of Node 2:");
				n2 = in.next();
				
				node0 = g.getNode(n1);
				node1 = g.getNode(n2);
				
				if(node0 != null && node1 != null) 
					g.removeEdge(g.getNode(n1), g.getNode(n2));
				else 
					System.out.println("One or more nodes not found, did not remove an edge!");
				break;
			case "5": 
				System.out.println(g.nodesToString());
				System.out.println(g.edgesToString());
				break;
			case "6":
				DFS.DFSearch(g);
				System.out.println(g.nodesToString());
				System.out.println(g.edgesToString());
				break;
			case "7":
				BFS.BFSearch(g);
				System.out.println(g.nodesToString());
				System.out.println(g.edgesToString());
				break;
			case "8":
				System.out.println("Enter the name of Node 1:");
				n1 = in.next();
				
				System.out.println("Enter the name of Node 2:");
				n2 = in.next();
				
				node0 = g.getNode(n1);
				node1 = g.getNode(n2);
				
				Dijkstra.DijkstraSearch(g, node0, node1);
				
				System.out.println("Shortest Path to " + n2 + " from " + n1 + " is " + node1.distance);
				break;
			case "debug":
				for(Node f : g.nodes) {
					System.out.println(f.name + ": " + f.distance);
				}
				break;
			case "stop":
				running = false;
				break;
			default: break;	
			}
		}
	}
}
