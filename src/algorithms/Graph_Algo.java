package algorithms;

import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms {
	public Graph_Algo(dataStructure.graph graph) {
		this.graph=graph ;
	}
	public Graph_Algo(){
	}

	private static class DijkstraResult {
		private final double[] dist;
		private final node_data[] prev;

		public DijkstraResult(double[] dist, node_data[] prev) {
			this.dist = dist;
			this.prev = prev;
		}

		public double[] getDist() {
			return dist;
		}

		public node_data[] getPrev() {
			return prev;
		}
	}
	private dataStructure.graph graph;

	@Override
	public void init(dataStructure.graph g) {
		this.graph = g;
	}

	@Override
	public void init(String file_name) {
		try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_name))) {
			dataStructure.graph graph = (dataStructure.graph)objectInputStream.readObject();
			init(graph);
		} catch(IOException | ClassNotFoundException e) {

		}
	}

	@Override
	public void save(String file_name) {
		ObjectOutputStream objectOutputStream = null;

		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_name));
			objectOutputStream.writeObject(this.graph);

		} catch (IOException e) {

		} finally {
			if(objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch(IOException e) {

				}
			}
		}
	}

	@Override
	public boolean isConnected() {
		for(node_data nodeData : graph.getV()) {
			node_data[] prevs = dijkstra(nodeData.getKey()).getPrev();

			for(int i = 0; i < prevs.length; i++) {
				if(nodeData.getTag() != i) { // not in cell of current source  - nodeData
					if(prevs[i] == null) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		double[] distances = dijkstra(src).getDist();
		return distances[this.graph.getNode(dest).getTag()];
	}

	// default access - package
	dataStructure.graph getGraph() {
		return graph;
	}

	private DijkstraResult dijkstra(int src) {
		double[] dist = new double[this.graph.nodeSize()];
		node_data[] prev = new node_data[dist.length];

		Queue<node_data> queue = new PriorityQueue<>(new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
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
		for(node_data nodeData : this.graph.getV()) {
            nodeData.setTag(i);
            nodeData.setInfo("in_queue");

            if (nodeData.getKey() == src) dist[i] = 0;
            else dist[i] = Double.MAX_VALUE;
            queue.add(nodeData);
            i++;
        }

		while(!queue.isEmpty()) {
			node_data u = queue.poll();
			u.setInfo("not_in_queue");

			for (edge_data edgeData : this.graph.getE(u.getKey())) {
				node_data v = this.graph.getNode(edgeData.getDest());

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

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data[] prev = dijkstra(src).getPrev();

		if(prev[this.graph.getNode(dest).getTag()] == null) {
			return null;
		} else {
			List<node_data> path = new ArrayList<>();

			node_data curNode = this.graph.getNode(dest);

			while(curNode != this.graph.getNode(src)) {
				path.add(0, curNode);
				curNode = prev[curNode.getTag()];
			}

			path.add(0, curNode);

			return path;
		}
	}

	@Override
	//if there is no path return null
	public List<node_data> TSP(List<Integer> targets) {
		if (targets != null) {
			List<node_data> OurConNodes = new ArrayList<>();

			int subListStartIndex = 0;
			for (int i = 0; i < targets.size() - 1; i++) {
				if (shortestPath(targets.get(i), targets.get(i + 1)) == null) {
					return null;
				}
				List<node_data> TwoConNodes = (shortestPath(targets.get(i), targets.get(i + 1)));
				OurConNodes.addAll(TwoConNodes.subList(subListStartIndex, TwoConNodes.size()));
				subListStartIndex = 1;
			}

			return OurConNodes;
		}
		return null;
	}

	@Override
	public dataStructure.graph copy() {
		DGraph dGraph = new DGraph();

		for(node_data nodeData : this.graph.getV()) {
			Node node = new Node(nodeData.getKey(), new Point3D(nodeData.getLocation()), nodeData.getWeight(), nodeData.getInfo(),
					nodeData.getTag());
			dGraph.addNode(node);
			Collection<edge_data> edges = dGraph.getE(nodeData.getKey());

			for(edge_data edgeData : edges) {
				dGraph.connect(nodeData.getKey(), edgeData.getDest(), edgeData.getWeight());
			}
		}

		return dGraph;
	}
	public void drawGraph(){
		StdDraw.setCanvasSize(800, 800);

		node_data minXNodeData = Collections.max(this.graph.getV(), new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return -1 * (o1.getLocation().ix() - o2.getLocation().ix());
			}
		});
		node_data maxXNodeData = Collections.max(this.graph.getV(), new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return o1.getLocation().ix() - o2.getLocation().ix();
			}
		});

		node_data minYNodeData = Collections.max(this.graph.getV(), new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return -1 * (o1.getLocation().iy() - o2.getLocation().iy());
			}
		});
		node_data maxYNodeData = Collections.max(this.graph.getV(), new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return o1.getLocation().iy() - o2.getLocation().iy();
			}
		});
		StdDraw.setXscale(Math.min(minXNodeData.getLocation().ix(), minYNodeData.getLocation().iy())-2 ,
				Math.max(maxXNodeData.getLocation().ix(), maxYNodeData.getLocation().iy()) +3);
		StdDraw.setYscale(Math.min(minXNodeData.getLocation().ix(), minYNodeData.getLocation().iy()) -4,
				Math.max(maxXNodeData.getLocation().ix(), maxYNodeData.getLocation().iy()) +3);

		double circleRadius = Math.sqrt(Math.pow(maxXNodeData.getLocation().ix() - minXNodeData.getLocation().ix(), 2)
				+ Math.pow(maxYNodeData.getLocation().iy() - minYNodeData.getLocation().iy(), 2))
				/ 350.0;

		// draw vertices
		for(node_data nodeData : this.graph.getV()) {
			StdDraw.filledCircle(nodeData.getLocation().x(), nodeData.getLocation().y(), circleRadius);
			StdDraw.setPenColor(Color.blue);
			StdDraw.setPenRadius(0.02);
			Point3D p = nodeData.getLocation();
			StdDraw.point(p.x(), p.y());
			String s = nodeData.getKey()+"";
			StdDraw.text(p.x()+0.5, p.y()+0.5, s);
			StdDraw.setPenRadius(0.005);
		}
		Collection <node_data> nodes = graph.getV();
		Iterator <node_data> i ;
		i = nodes.iterator();
		while (i.hasNext()) {
			node_data nd = i.next();
			Iterator <edge_data> edgei = graph.getE(nd.getKey()).iterator();
			while (edgei.hasNext()) {
				edge_data EDGE = edgei.next();
				Point3D src = nd.getLocation();
				Point3D dest = graph.getNode(EDGE.getDest()).getLocation();
				double w = EDGE.getWeight();
				StdDraw.setPenColor(Color.black);
				StdDraw.line(src.x(), src.y(), dest.x(), dest.y());
				StdDraw.setPenColor(Color.red);
				StdDraw.text((dest.x()+src.x())/2, (dest.y()+src.y())/2, String.format("%.1f", w));
			}
		}
		try {

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
