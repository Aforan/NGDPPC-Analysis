package com.foran.graph.tools;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

import com.foran.graph.Edge;
import com.foran.graph.Node;
import com.foran.graph.Graph;

public class BFS {
	public static final int BACK = 0;
	public static final int CROSS = 2;
	public static final int DISCOVERY = 1;
	public static final int UNEXPLORED = -1;
	
	public static void BFSearch(Graph graph) {
		for(Node n : graph.nodes) {
			n.explored = false;
		}
		
		for(Edge e: graph.edges) {
			e.explored = false;
			e.label = UNEXPLORED;
		}
		
		for(Node n : graph.nodes) {
			if(n.explore() == true) {
				BFSVisit(n);
			}
		}
	}
	
	public static void BFSVisit(Node n) {
		ArrayList<Queue<Node>> q = new ArrayList<Queue<Node>>();
		q.add(new LinkedList<Node>());
		
		int i = 0;
		q.get(i).offer(n);
		
		while(!q.get(i).isEmpty()) {
			q.add(new LinkedList<Node>());
			while(!q.get(i).isEmpty()) {
				Node t = q.get(i).poll();
				for(Edge e : t.edgeAdjacencies) {
					Node w = e.opposite(t);
					if(w.explored == false) {
						w.explore();
						e.label = DISCOVERY;
						e.explored = true;
						q.get(i+1).offer(w);
					} else if(e.label == UNEXPLORED && w.explored == true){
						e.label = CROSS;
					}
				}
			}
			i++;
		}
	}
}
