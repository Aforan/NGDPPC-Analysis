package com.foran.graph.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.foran.graph.Edge;
import com.foran.graph.Node;
import com.foran.graph.Graph;

public class Dijkstra {
	public static final int INFINITY = 999999999;
	public static final int MAX_NODES = 50;
	
	public static void DijkstraSearch(Graph graph, Node start, Node end) {
		ArrayList<Node> s = new ArrayList<Node>();
		PriorityQueue<Node> q = new PriorityQueue<Node>(MAX_NODES, new Comparator<Node>() {
			@Override
			public int compare(Node a, Node b) {
				if(a.distance > b.distance) return 1;
				if(a.distance < b.distance) return -1;
				else return 0;
			}
		});
		
		initSingleSource(graph, start, q);

		while(!q.isEmpty()) {
			Node u = q.poll();

			s.add(u);
			for(Node n : u.adjacencies) {
				relax(u, n, graph, q);
			}
		}

	}
	
	public static void initSingleSource(Graph g, Node start, PriorityQueue<Node> q) {
		for(Node n : g.nodes) {
			n.distance = INFINITY;
			n.parent = null;
			
			if(n.equals(start)) n.distance = 0;

			q.offer(n);
		}
	}

	public static void relax(Node u, Node v, Graph g, PriorityQueue<Node> q) {
		Edge t = g.getEdge(u, v);

		if(v.distance > u.distance + t.weight) {
			v.distance = u.distance + t.weight;
			v.parent = u;
			
			//Replace the Node in the Queue (Or else it will not be in the proper order!)
			q.remove(v);
			q.offer(v);
		}
	}

	public static void DijkstraSearchDouble(Graph graph, Node start, Node end) {
		ArrayList<Node> s = new ArrayList<Node>();
		PriorityQueue<Node> q = new PriorityQueue<Node>(MAX_NODES, new Comparator<Node>() {
			@Override
			public int compare(Node a, Node b) {
				if(a.dDistance > b.dDistance) return 1;
				if(a.dDistance < b.dDistance) return -1;
				else return 0;
			}
		});
		
		initSingleSourceDouble(graph, start, q);

		while(!q.isEmpty()) {
			Node u = q.poll();

			s.add(u);
			for(Node n : u.adjacencies) {
				relaxDouble(u, n, graph, q);
			}
		}

	}
	
	public static void initSingleSourceDouble(Graph g, Node start, PriorityQueue<Node> q) {
		for(Node n : g.nodes) {
			n.dDistance = INFINITY * 1.0;
			n.parent = null ;
			
			if(n.equals(start)) n.dDistance = 1.0;

			q.offer(n);

		}
	}

	public static void relaxDouble(Node u, Node v, Graph g, PriorityQueue<Node> q) {
		Edge t = g.getEdge(u, v);

		if(v.dDistance > u.dDistance * t.dweight) {
			v.dDistance = u.dDistance * t.dweight;
			v.parent = u;
			
			//Replace the Node in the Queue (Or else it will not be in the proper order!)
			q.remove(v);
			q.offer(v);
		}
	}
}
