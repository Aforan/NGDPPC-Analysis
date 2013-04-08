package com.foran.graph;

import java.util.ArrayList;


/***
 * Node class to be used with an adjacency list representation of a Graph.  Each Node has an ArrayList of Nodes
 * which represents its' respective adjacencies as well as an ArrayList of Edges to represent the Edges connecting
 * to the Node.  The node class is meant to be used with various Graph search and shortest path algorithms and 
 * therefore contains a variety of helper variables to be used with such algorithms.  When simply constructing the 
 * nodes the helper variables are set to arbitrary, useless values and should be initialized when using algorithms 
 * for analysis of the Graph. 
 * 
 * @author Andrew Foran
 * ***/


public class Node {
	
	public String name;
	public ArrayList<Node> adjacencies;
	public ArrayList<Edge> edgeAdjacencies;
	
	//Some variables for DFS
	public int start, finish;
	public boolean explored;
	
	//Some variables to be used in Dijkstras algorithm
	public int distance;
	public double dDistance;
	public Node parent;
	
	
	/***
	 * Class constructor initializes variables to proper values, variables to be used later in searching and general
	 * Graph analysis algorithms are set to arbitrary useless values and should be changed later.
	 * ***/
	
	
	public Node(String name) {
		this.name = name;
		
		explored = false;
		start = -1;
		finish = -1;
		
		adjacencies = new ArrayList<Node>();
		edgeAdjacencies = new ArrayList<Edge>();
		
		distance = -1;
		dDistance = - 1.0;
		parent = null;
	}

	
	/***
	 * Inserts a given Node into the adjacency list of this Node.
	 * 
	 * NOTE: Node sanitization should occur before calling this method!  This will allow for multiple node entries!
	 * 
	 * @param n The Node to be inserted into the Adjacency list
	 * @return True if the node was successfully inserted and false otherwise
	 * ***/
	
	
	public boolean insertNode(Node n) {
		return adjacencies.add(n);
	}
	
	
	/***
	 * Inserts an Edge into this Nodes edgeAdjecency list.  First makes sure that one of the edges endpoints is this.
	 * If not returns false and does not insert the edge.  Else the edge is inserted into the edge Adjacency list.
	 * 
	 * @param e The Edge to be inserted into the edgeAdjacency List.
	 * @return True if the Edge was inserted successfully and false otherwise.
	 * ***/
	
	
	public boolean addEdge(Edge e) {
		if(e.n0 != this && e.n1 != this) return false;
		else {
			edgeAdjacencies.add(e);
			return true;
		}
	}
	
	
	/***
	 * Helper method to determine whether this Nodes adjacency list contains a given node.  If so returns true and false otherwise.
	 * 
	 * @param node The Node to look for in the adjacency list
	 * @return True if the Node was found and False otherwise.
	 * ***/
	
	
	public boolean contains(Node node) {
		for (Node n : adjacencies) {
			if (n == node)
				return true;
		}
		return false;
	}

	
	/***
	 * Removes a given Node from this Nodes' adjacency list, first directly removes the node and then searched the Edge Adjacencies for 
	 * the edge with an endpoint that is the given node.  When found the removeEdge is called to remove the edge from the list and the 
	 * and the value returned by the removeEdge call is returned.  If no such edge is found, false is returned.
	 * 
	 * @param n The Node to be removed.
	 * @return True if the Node is removed successfully and False otherwise.
	 * ***/
	
	
	public boolean remove(Node n) {
		adjacencies.remove(n);
		
		for(Edge e : edgeAdjacencies) {
			if(e.n0 == n || e.n1 == n) {
				 return removeEdge(e);
			}
		}
		
		return false;
	}

	
	/***
	 * 	Removes a given Edge from the Edge Adjacency list.  First determines if the list contains the Edge and if not, prints an error 
	 * message and returns.  If the edge is found, the edge is removed from the list.
	 * 
	 * @param e The Edge to be removed.
	 * @return True if the edge was removed successfully and False otherwise.
	 * ***/
	
	
	public boolean removeEdge(Edge e) {
		if(!edgeAdjacencies.contains(e)) {
			System.out.println(name + " did not contain " + e.toString());
			return false;
		}
		else {
			edgeAdjacencies.remove(e);
			return true;		
		}
	}
	
	
	/***
	 * Gets the Edge from the edgeAdjacency ArrayList with this Node as one endpoint and the 
	 * Parameter Node as the other.  returns null if no such node is found.
	 *
	 * @param n The other endpoint of the Edge to be found.
	 * @return The Edge with this Node as an endpoint and Node n as the other.  null if no such node is found.
	 * ***/


	public Edge getEdgeTo(Node n) {
		for (Edge e : edgeAdjacencies) {
			if(e.opposite(this) == n) return e;
		}

		return null;
	}
	
	/***
	 * Helper method to return a String containing a list of this Nodes adjacent seperated by Commas
	 * 
	 * @return A String containing the names of all Nodes adjacent to current node.
	 * ***/
	
	
	public String adjToString() {
		String r = "";

		for (int i = 0; i < adjacencies.size(); i++) {
			r += adjacencies.get(i).name;
			
			if (i+1 < adjacencies.size()) r += ", ";
		}
		
		return r;
	}
	
	
	/***
	 * Helper Method for searching algorithms, checks to see if this node has been explored, if so return false.  
	 * If this node has yet to be explored, set explored to True and return True.
	 * 
	 * @return True if the node was successfully explored (explored set to True) and false if this node has already been explored
	 * ***/
	
	
	public boolean explore() {
		if(explored) return false;
		else{
			explored = true;
			return true;
		}
	}
	
	
	/***
	 * Helper method for searching algorithms, sets start to given time.
	 * 
	 * @param time the value to set start to
	 ***/
	
	
	public void start(int time) {
		start = time;
	}
	
	
	/***
	 * Helper method for searching algorithms, sets finish to given time.
	 * 
	 * @param time the value to set finish to
	 ***/
	
	
	public void finish(int time) {
		finish = time;
	}
}
