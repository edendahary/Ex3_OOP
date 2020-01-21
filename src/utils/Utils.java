package utils;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import gameClient.Fruit;
import gameClient.Robot;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_node_data;
import oop_elements.OOP_Edge;
import oop_elements.OOP_NodeData;

import java.util.*;

public class Utils {
    private final static double EPSILON = 0.00001;

    public static Point3D parsePoint3DFromJSON(String jsonString) {
        String[] parts = jsonString.split(",");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        return new Point3D(x, y);
    }

    public static List<oop_edge_data> getAllEdges(OOP_DGraph graph) {
        List<oop_edge_data> edges = new ArrayList<>();

        for(oop_node_data node : graph.getV()) {
            edges.addAll(graph.getE(node.getKey()));
        }

        return edges;
    }

    public static boolean doublesEqual(double d1, double d2) {
        return Math.abs(d1 - d2) <= EPSILON;
    }

    public static boolean isBetween(double x1, double x2, double xToCheck) {
        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);

        return xToCheck >= minX && xToCheck <= maxX;
    }

}
