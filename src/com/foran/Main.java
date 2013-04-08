package com.foran;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.PrintStream;
import com.foran.util.Pearson;
import com.foran.graph.*;
import com.foran.graph.tools.*;


/*

	Using Density Threshold .95

	Need to find a much lower density threshold ... Distances are much too large, somethings wrong here.....
	Density Threshold 0.88 Produces around .5 density, which is pretty good need to implement hashing 
	in graph to improve speeds.

	Using THRESHOLD = 0.8 for now(Density of 0.49247...)

	HUFFMAN DUE NOV 28
	EXTRA CREDIT DUE DEC 3

	
*/
public class Main {
	private static final int START_YEAR = 2006;
	private static final int NUM_COL = 48;
	private static final int END_YEAR = 2011;
	private static final double THRESHOLD = 0.8;

	public static void main(String[] args) {
		Main m = new Main();
		m.run(args);
	}

	public void run(String[] args) {
		if(args.length < 1) {
			System.out.println("Need some arguments");
			System.exit(0);
		} else if(args[0].equals("-b")) {
			//between countries
			if(args.length < 3) {
				System.out.println("use : -b country1 country2");
				System.exit(0);
			} else {
				between(args[1], args[2]);
			}
		} else if(args[0].equals("-a")) {
			if(args.length < 2) {
				System.out.println("use : -a country");
				System.exit(0);				
			} else {
				all(args[1]);
			}
		} else if(args[0].equals("-c")) {
			connected();
		} else {
			System.out.println("improper usage");
			System.exit(0);
		}

		//g.printGraph();
		
	}

	public void all(String country) {
		Graph g = makeGraph();
		/***	Look for Countries with 3 or more Edges between source and itself and print to File   ***/
		Dijkstra.DijkstraSearch(g, g.getNode(country), null);

		for(Node node : g.nodes){
			Node tempNode = node;

			//if(tempNode.dDistance <= 0.5) System.out.println(tempNode.name + ": " + tempNode.dDistance);	

			if(node.distance >= 3 && node.distance != Dijkstra.INFINITY) {
				System.out.println(node.name + ": " + node.distance);
				Node tn = node;

				ArrayList<Node> tempList = new ArrayList<Node>();
				while(tn != null) {
					tempList.add(tn);
					tn = tn.parent;
				}

				double r = 1.0;

				for(int j = tempList.size() - 1; j > 0; j--) {
					r *= g.getEdge(tempList.get(j), tempList.get(j-1)).dweight;
					System.out.print(tempList.get(j).name + " - " + g.getEdge(tempList.get(j), tempList.get(j-1)).dweight + " -> ");
				}

				System.out.println(tempList.get(0).name + "\n" + r + "\n");
			}
		}
	}

	public void between(String s0, String s1) {
		Graph g = makeGraph();

		Node start = g.getNode(s0);
		Node end = g.getNode(s1);

		Dijkstra.DijkstraSearch(g, start, null);
		ArrayList<Node> tempList = new ArrayList<Node>();

		Node tempNode = end;

		while(tempNode != null) {
			tempList.add(tempNode);
			tempNode = tempNode.parent;
		}

		Double r = 1.0;

		if(tempList.size() <= 1) {
			System.out.println("There is no path from " + s0 + " to " + s1);
		} else {
			for(int i = tempList.size() - 1; i > 0; i--) {
				r *= g.getEdge(tempList.get(i), tempList.get(i-1)).dweight;
				System.out.print(tempList.get(i).name + " - " + g.getEdge(tempList.get(i), tempList.get(i-1)).dweight + " -> ");
			}
			System.out.println(tempList.get(0).name + "\n" + r + "\n");
		}
	}


	public static void connected(Graph g) {
		int cc = ConnectedComponents.search(g);
		System.out.println(cc);
	}

	public static void connected(Graph g, ArrayList<Node> componentHeads) {
		int cc = ConnectedComponents.search(g, componentHeads);
		System.out.println(cc);

	}

	private Graph makeGraph() {
		Scanner scanner = null;
		ArrayList<Country> countries = new ArrayList<Country>();

		try {
			scanner = new Scanner(new File("../WEOOct2012all.csv"));
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		String line = scanner.nextLine();
		String[] split = line.split("\\t");
		String[] colNames = new String[NUM_COL];

		int start = 0;
		int end = 0;
		int i = 0;
		String startYear = Integer.toString(START_YEAR);

		for(String s : split) {
			colNames[i] = s;
			if(s.equals(Integer.toString(START_YEAR))) start = i;
			if(s.equals(Integer.toString(END_YEAR))) end = i+1;
			i++;
		}

		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			split = line.split("\\t");
			double[] tempData = new double[end - start];


			if(split.length > 2 && split[2].equals("NGDPDPC")) {
				if(split.length > start) {
					for(int j = start; j < end; j++) {
						if(split.length > j && !split[j].equals("n/a")) {
							tempData[j - start] = Double.parseDouble(split[j]);
						}
					}
				} 

				countries.add(new Country(split[3], tempData));
			}
		}

		Graph g = new Graph(false);

		for(int j = 0; j < countries.size(); j++) {
			
			Country c0 = countries.get(j);
			Node c0Node = g.getNode(c0.getName());

			if(c0Node == null) {
				c0Node = new Node(c0.getName());
				g.insertNode(c0Node);
			} 
			
			for(int k = 0; k < countries.size(); k++) {
				Country c1 = countries.get(k);
				Node c1Node = g.getNode(c1.getName());

				if(c1Node == null) {
					c1Node = new Node(c1.getName());
					g.insertNode(c1Node);
				} 

				if(!c0.equals(c1)) {
					try{
						double r = Pearson.pearson(c0.getData(), c1.getData());
						if(Math.abs(r) > THRESHOLD) {
							g.insertEdge(c0Node, c1Node, Math.abs(r));
						}
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
				} 
			}
				
		}

		return g;
	}

	public void connected() {
		Graph g = makeGraph();
		System.out.println("numnodes = " + g.numNodes);
		/***	Print Density and Graph ***/
		ArrayList<Node> componentHeads = new ArrayList<Node>();
		connected(g, componentHeads);
		double density = (2.0 * g.numEdges) / (1.0 * g.numNodes * (g.numNodes - 1.0));

		System.out.println("Treshold: " + THRESHOLD);
		System.out.println("Density: " + density + "\n");
	
		for(Node n : componentHeads) {
			System.out.print(n.name + " : ");
			
			DFS.DFSFromNode(g, n);

			for(Node no : g.nodes) {
				if(no.explored == true && !no.equals(n)) {
					System.out.print(no.name + ", ");
				}
			}
			System.out.println("\n");
		}
	}

	private class Country {
		private String name;
		private double[] data;

		public Country(String name, double[] data) {
			this.name = name;
			this.data = data;
		}

		public double[] getData() {
			return data;
		}

		public String getName() {
			return name;
		} 
	}
}