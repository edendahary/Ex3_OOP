package gameClient;

import dataStructure.graph;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_node_data;

import java.util.*;

public class oop_GraphAlgo {
    private OOP_DGraph dgraph;

    public oop_GraphAlgo(){

    }

    public oop_GraphAlgo(OOP_DGraph graph){

        this.dgraph = graph;
    }

    public void init(OOP_DGraph g) {
        this.dgraph = g;
    }


    public static class DijkstraResult {
        private final double[] dist;
        private final oop_node_data[] prev;

        public DijkstraResult(double[] dist, oop_node_data[] prev) {
            this.dist = dist;
            this.prev = prev;
        }

        public double[] getDist() {
            return dist;
        }

        public oop_node_data[] getPrev() {
            return prev;
        }

    }
    private  DijkstraResult dijkstra(int src) {
        double[] dist = new double[this.dgraph.nodeSize()];
        oop_node_data[] prev = new oop_node_data[dist.length];

        Queue<oop_node_data> queue = new PriorityQueue<>(new Comparator<oop_node_data>() {
            @Override
            public int compare(oop_node_data n1, oop_node_data n2) {
                if(dist[n1.getTag()] < dist[n2.getTag()]) {
                    return - 1;
                } else if(dist[n1.getTag()] > dist[n2.getTag()]) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int i =  0;
        for(oop_node_data nodeData : this.dgraph.getV()) {
            nodeData.setTag(i);
            nodeData.setInfo("in_queue");

            if (nodeData.getKey() == src) dist[i] = 0;
            else dist[i] = Double.MAX_VALUE;
            queue.add(nodeData);
            i++;
        }

        while(!queue.isEmpty()) {
            oop_node_data u = queue.poll();
            u.setInfo("not_in_queue");

            for (oop_edge_data edgeData : this.dgraph.getE(u.getKey())) {
                oop_node_data v = this.dgraph.getNode(edgeData.getDest());

                if(v.getInfo().equals("in_queue")) {
                    double alt = dist[u.getTag()] + edgeData.getWeight();

                    if (alt < dist[v.getTag()]) {
                        dist[v.getTag()] = alt;
                        prev[v.getTag()] = u;

                        // re-order v in queue
                        queue.remove(v);
                        queue.add(v);
                    }
                }
            }
        }

        return new DijkstraResult(dist, prev);
    }
    public List<oop_node_data> shortestPath(int src, int dest) {
        oop_node_data[] prev = dijkstra(src).getPrev();

        if(prev[this.dgraph.getNode(dest).getTag()] == null) {
            return null;
        } else {
            List<oop_node_data> path = new ArrayList<>();

            oop_node_data curNode = this.dgraph.getNode(dest);

            while(curNode != this.dgraph.getNode(src)) {
                path.add(0, curNode);
                curNode = prev[curNode.getTag()];
            }

            path.add(0, curNode);

            return path;
        }
    }

}
