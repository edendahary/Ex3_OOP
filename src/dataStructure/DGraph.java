package dataStructure;

import java.io.Serializable;
import java.util.*;

public class DGraph implements graph, Serializable {
	private final Map<Integer, edge_data> edgeDataMap = new HashMap<>();
	private final Map<node_data, Set<edge_data>> nodeDataEdgeMap =  new HashMap<>();
	private final List<node_data> nodeDataList = new ArrayList<>();
	private int modeCount = 0;

    @Override
	public node_data getNode(int key) {
		node_data nodeData = null;

		for(node_data cur : this.nodeDataList) {
			if(cur.getKey() == key) {
				nodeData = cur;
				break;
			}
		}
		return nodeData;
	}
	@Override
	public edge_data getEdge(int src, int dest) {
		return this.edgeDataMap.get(Edge.hashCode(src, dest));
	}

	@Override
	public void addNode(node_data n) {
		modeCount++;
		this.nodeDataList.add(n);
		this.nodeDataEdgeMap.put(n, new HashSet<>());
	}

	@Override
	public void connect(int src, int dest, double w) {
		modeCount++;
        Edge edge = new Edge(src, dest, w);
        this.edgeDataMap.put(Edge.hashCode(src, dest), edge);
        this.nodeDataEdgeMap.get(getNode(src)).add(edge);
	}

	@Override
	public Collection<node_data> getV() {
        return this.nodeDataList;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return this.nodeDataEdgeMap.get(getNode(node_id));
	}

	@Override
	public node_data removeNode(int key) {
        node_data nodeDataToRemove = null;

		for(node_data nodeData : this.nodeDataList) {
			if(nodeData.getKey() == key) {
				nodeDataToRemove = nodeData;
				break;
			}
		}

        if(nodeDataToRemove == null) {
            return null;
        } else {
			modeCount++;
			this.nodeDataEdgeMap.remove(getNode(key));
            this.nodeDataList.remove(nodeDataToRemove);

            for(node_data nodeData : this.nodeDataList) {
                // check edge (nodeDataToRemove, nodeData)
                int edgeHashcodeSrc = Edge.hashCode(nodeDataToRemove.getKey(), nodeData.getKey());

                if(this.edgeDataMap.containsKey(edgeHashcodeSrc)) {
                    this.edgeDataMap.remove(edgeHashcodeSrc);
                }

                // check edge (nodeData, nodeDataToRemove)
                edgeHashcodeSrc = Edge.hashCode(nodeData.getKey(), nodeDataToRemove.getKey());

                if(this.edgeDataMap.containsKey(edgeHashcodeSrc)) {
					this.nodeDataEdgeMap.get(nodeData).remove(this.edgeDataMap.get(edgeHashcodeSrc)); 	// remove from edge set of nodeData
                    this.edgeDataMap.remove(edgeHashcodeSrc);
                }
            }

            return nodeDataToRemove;
        }
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		int edgeHashcode = Edge.hashCode(src, dest);

		if(this.edgeDataMap.containsKey(edgeHashcode)) {
			modeCount++;
		    edge_data edgeData = this.edgeDataMap.get(edgeHashcode);
		    this.edgeDataMap.remove(edgeHashcode);
            this.nodeDataEdgeMap.get(getNode(src)).remove(edgeData);
		    return edgeData;
        } else {
		    return null;
        }
	}

	@Override
	public int nodeSize() {
	    return this.nodeDataList.size();
	}

	@Override
	public int edgeSize() {
		return this.nodeDataEdgeMap.size();
	}

	@Override
	public int getMC() {
		return modeCount;
	}
}
