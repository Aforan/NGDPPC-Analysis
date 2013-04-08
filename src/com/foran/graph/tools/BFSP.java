/*public class BFSP {
	public static final int INFINITY = 999999999.0;

	public static void BFSearch(Graph graph, Node start) {
		for(Node n : graph.nodes) {
			n.explored = false;
			n.distance = INFINITY;
		}
		
		for(Edge e: graph.edges) {
			e.explored = false;
			e.label = UNEXPLORED;
		}
		
		start.dDistance = 1.0;

		BFSVisit(start);
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

					if(w.dDistance > t.dDistance * e.dweight) {
						w.dDistance = t.dDistance * e.dweight;
					}
				}

				t.explore();
			}
			i++;
		}
	}
}*/