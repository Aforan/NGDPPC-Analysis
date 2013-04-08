package com.foran.graph;

/***
 * Edge class, this class defines the attributes of an undirected edge to be used by the 
 * Graph and Node classes as well as search algorithms.  Some extra variables are added 
 * for convenience when implementing search algorithms (explored, label etc.)
 * @author Andrew Foran
 * ***/

public class Edge {
	public int weight;
	public double dweight;
	public Node n0, n1;
	
	//some helper variables for searching algorithms
	public boolean explored;
	public int label;

	
	/***
	* Class constructor sets the endpoints to the given nodes, and some initial values for weight, 
	* explored and label.  Weight is set to 1 by default.
	*
	* @param n0 One endpoint of the Edge
	* @param n1 One endpoint of the Edge
	***/


	public Edge(Node n0, Node n1) {
		this.n0 = n0;
		this.n1 = n1;
		weight = 1;
		explored = false;
		label = -1;
	}
	
	
	/***
	* Overloaded Class constructor calls the class constructor on the given nodes and sets weight 
	* to the given weight
	*
	* @param n0 One endpoint of the Edge
	* @param n1 One endpoint of the Edge
	* @param weight The desired weight of the edge
	***/


	public Edge(Node n0, Node n1, int weight) {
		this(n0, n1);
		this.weight = weight;
		dweight = 0.0;
	}
	
	public Edge(Node n0, Node n1, double dweight) {
		this(n0, n1);
		this.dweight = dweight;
		weight = 1;
	}

	/***
	* Getter method for weight
	*
	* @return the Weight of the edge
	***/

	public int getWeight() {
		return weight;
	}


	/***
	* Helper method, given an endpoint of this edge returns the opposite end.
	*
	*@param n The node to be examined
	* @return The opposite endpoint of the given node, null if the node is not on this edge
	***/


	public Node opposite(Node n) {
		if(n0 == n) return n1;
		else if(n1 == n) return n0;
		else return null;
	}
	

	/***
	* Helper method, returns a string with some formating around the data contained within this Edge
	* 
	* @return A string with data from this Edge
	***/
	public String toString() {
		String r = "" + n0.name + " -> " + n1.name + " label: " + label + " explored: " + explored + " dw: " + dweight + " w: " + weight;
		return r;
	}
	

	/***
	* Heper method, determines whether a node is contained in this Edge
	*
	*@param n The Node to be examined
	*@return True if thoe node is contained in the edge false otherwise
	***/
	public boolean contains(Node n) {
		if(n0.equals(n) || n1.equals(n)) return true;
		else return false;
	}
}
