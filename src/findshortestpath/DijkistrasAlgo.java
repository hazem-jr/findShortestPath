
package findshortestpath;
import java.util.*;
public class DijkistrasAlgo {
     int dist[];
    Set<Integer> visited;
    PriorityQueue<Node> pqueue;
    int V; // Number of vertices 
    List<List<Node>> adj_list;

    // initializations 
    public DijkistrasAlgo(int V) {
        this.V = V;
        dist = new int[V];
        visited = new HashSet<Integer>();
        pqueue = new PriorityQueue<Node>(V, new Node());

    }

    // algorithm implementation
    public void algo_dijkstra(List<List<Node>> adj_list, int src_vertex) {
        this.adj_list = adj_list;

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // first add source vertex to PriorityQueue 
        pqueue.add(new Node(src_vertex, 0));

        // Distance to the source from itself is 0 
        dist[src_vertex] = 0;
        while (visited.size() != V) {

            // u is removed from PriorityQueue and has min distance  
            int u = pqueue.remove().node;

            // add node to finalized list (visited)
            visited.add(u);
            graph_adjacentNodes(u);
        }
    }

    private void graph_adjacentNodes(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        // process all neighbouring nodes of u 
        for (int i = 0; i < adj_list.get(u).size(); i++) {
            Node v = adj_list.get(u).get(i);

            //  proceed only if current node is not in 'visited'
            if (!visited.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // compare distances 
                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance;
                }

                // Add the current vertex to the PriorityQueue 
                pqueue.add(new Node(v.node, dist[v.node]));
            }
        }
    }
    
    // Node class  
// to compare the costs of nodes 
class Node implements Comparator<Node> {

    public int node;
    public int cost;

    public Node() {
    }

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost) {
            return -1;
        }
        if (node1.cost > node2.cost) {
            return 1;
        }
        return 0;
    }
}
}
