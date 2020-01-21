package gameClient;

import algorithms.Graph_Algo;
import dataStructure.Edge;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_node_data;
import oop_elements.OOP_Edge;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class Fruit {
    private final double value;
    private final int type;
    private final Point3D pos;

    public Fruit(double value, int type, Point3D pos) {
        this.value = value;
        this.type = type;
        this.pos = pos;
    }

    public static Fruit parseJSON(JSONObject obj) throws JSONException {
        JSONObject fruitObj = obj.getJSONObject("Fruit");
        double value = fruitObj.getDouble("value");
        int type = fruitObj.getInt("type");
        Point3D pos = Utils.parsePoint3DFromJSON(fruitObj.getString("pos"));
        return new Fruit(value, type, pos);
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public Point3D getPos() {
        return pos;
    }

    // (x-x1)/(x2-x1) = (y-y1)/(y2-y1) = c
    // 0 <= c <= 1.0
    public static List<oop_edge_data> getEdges(List<Fruit> fruitList, OOP_DGraph dGraph) {
        List<oop_edge_data> fruitEdgeList = new ArrayList<>();

        List<oop_edge_data> allEdges = Utils.getAllEdges(dGraph);

        for(Fruit fruit : fruitList) {
            oop_edge_data fruitEdge = null;

            for(oop_edge_data edge : allEdges) {
                oop_node_data source = dGraph.getNode(edge.getSrc());
                oop_node_data dest = dGraph.getNode(edge.getDest());

                // y = ax + b
                double a = (dest.getLocation().y() - source.getLocation().y()) / (dest.getLocation().x() - source.getLocation().x());
                double b = dest.getLocation().y() - a * dest.getLocation().x();

                if(Utils.doublesEqual(a * fruit.getPos().x() + b, fruit.getPos().y())
                    && Utils.isBetween(dest.getLocation().x(), source.getLocation().x(), fruit.getPos().x())) {
                    if((fruit.getType() == -1 && edge.getSrc() < edge.getDest())
                            || (fruit.getType() != -1 && edge.getSrc() > edge.getDest())) {
                        fruitEdge = edge;
                        break;
                    }
                }

//                double xDiff = (fruit.getPos().x() - dGraph.getNode(edge.getSrc()).getLocation().x())
//                        / (dGraph.getNode(edge.getDest()).getLocation().x() - dGraph.getNode(edge.getSrc()).getLocation().x());
//                double yDiff = (fruit.getPos().y() - dGraph.getNode(edge.getSrc()).getLocation().y())
//                        / (dGraph.getNode(edge.getDest()).getLocation().y() - dGraph.getNode(edge.getSrc()).getLocation().y());
//
//                if(Utils.doublesEqual(xDiff, yDiff) && (xDiff >= 0 && xDiff <= 1)){
//                    if((fruit.getType() == -1 && edge.getSrc() < edge.getDest())
//                        || (fruit.getType() == 0 && edge.getSrc() > edge.getDest()))
//                    fruitEdge = edge;
//                    break;
//                }
            }

            fruitEdgeList.add(fruitEdge);
        }

        return fruitEdgeList;
    }


    @Override
    public String toString() {
        return "Fruit{" +
                "value=" + value +
                ", type=" + type +
                ", pos=" + pos +
                '}';
    }
}
