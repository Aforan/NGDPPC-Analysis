package com.foran.graph.tools;

import java.util.ArrayList;

import com.foran.graph.Edge;
import com.foran.graph.Node;
import com.foran.graph.Graph;
import com.foran.graph.tools.DFS;


/***
 * ConnectedComponents class, meant to be used statically to determine the number of 
 * connected components within a graph.  Does a DFS on the graph and keeps track of 
 * the number of distinct connected components on the fly.
 * 
 * @author Andrew Foran
 * ***/

public class ConnectedComponents{
	public static final int DISCOVERY = 1;
	public static final int BACK = 0;
	public static final int UNEXPLORED = -1;
	
	/***
	 * Searches the Graph using a DFS and every time the algorithm returns to this method increments 
	 * the number of connected components.  Since the Graph is assumed to be nondirectional, any node 
	 * within a connected component will be able to be reached from any other node within the component.
	 * Therefore by using a DFS traversal we will find all nodes that are in the connected component of 
	 * a given node when DFSVisit() is called on that node.  By returning from the DFSVisit we know that
	 * all nodes in that connected component have been found and cc can be incremented.
	 *
	 * @param graph The Graph to study.
	 * @return The number of connected components in the graph.
	 * ***/


	public static int search(Graph graph) {
		int cc = 0;

		for(Node n : graph.nodes) {
			n.explored = false;
		}
		
		for(Edge e: graph.edges) {
			e.explored = false;
			e.label = UNEXPLORED;
		}
		
		for(Node n : graph.nodes) {
			if(n.explore() == true) {
				DFSVisit(n);
				cc++;
			}
		}
		return cc;
	}

	public static int search(Graph graph, ArrayList<Node> componentHeads) {
		int cc = 0;

		for(Node n : graph.nodes) {
			n.explored = false;
		}
		
		for(Edge e: graph.edges) {
			e.explored = false;
			e.label = UNEXPLORED;
		}
		
		for(Node n : graph.nodes) {
			if(n.explore() == true) {
				DFSVisit(n);
				componentHeads.add(n);
				cc++;
			}
		}
		return cc;
	}
	

	/***
	* Searches the edges connected to a node and explores them using recursive DFSVisit() calls
	*
	* @param n The node to be studied	
	***/


	public static void DFSVisit(Node n) {
		for(Edge e : n.edgeAdjacencies) {
			if(e.explored == false) {
				Node w = e.opposite(n);
				if(w != null && w.explore() == true) {
					e.explored= true;
					e.label = DISCOVERY;
					DFSVisit(w);
				} else {
					e.explored = true;
					e.label = BACK;
				}
			}
		}
	}
	
}
