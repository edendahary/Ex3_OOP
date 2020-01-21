//package gameClient;
//
//import dataStructure.graph;
//import oop_dataStructure.OOP_DGraph;
//import oop_dataStructure.oop_edge_data;
//import oop_dataStructure.oop_node_data;
//import oop_utils.OOP_Point3D;
//import utils.Point3D;
//import utils.StdDraw;
//
//import java.awt.*;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Iterator;
//
//public class MyGameGUI {
//    private OOP_DGraph graph;
//    public MyGameGUI(){
//
//    }
//    public MyGameGUI(OOP_DGraph graph){
//        this.graph =graph;
//        drawGraph();
//    }
//    public void drawGraph(){
//        StdDraw.setCanvasSize(1000, 1000);
//
//        oop_node_data minXNodeData = Collections.max(this.graph.getV(), new Comparator<oop_node_data>() {
//            @Override
//            public int compare(oop_node_data o1, oop_node_data o2) {
//                return -1 * (o1.getLocation().ix() - o2.getLocation().ix());
//            }
//        });
//        oop_node_data maxXNodeData = Collections.max(this.graph.getV(), new Comparator<oop_node_data>() {
//            @Override
//            public int compare(oop_node_data o1, oop_node_data o2) {
//                return o1.getLocation().ix() - o2.getLocation().ix();
//            }
//        });
//
//        oop_node_data minYNodeData = Collections.max(this.graph.getV(), new Comparator<oop_node_data>() {
//            @Override
//            public int compare(oop_node_data o1, oop_node_data o2) {
//                return -1 * (o1.getLocation().iy() - o2.getLocation().iy());
//            }
//        });
//        oop_node_data maxYNodeData = Collections.max(this.graph.getV(), new Comparator<oop_node_data>() {
//            @Override
//            public int compare(oop_node_data o1, oop_node_data o2) {
//                return o1.getLocation().iy() - o2.getLocation().iy();
//            }
//        });
//        StdDraw.setXscale(Math.min(minXNodeData.getLocation().ix(), minYNodeData.getLocation().iy())-2 ,
//                Math.max(maxXNodeData.getLocation().ix(), maxYNodeData.getLocation().iy()) +3);
//        StdDraw.setYscale(Math.min(minXNodeData.getLocation().ix(), minYNodeData.getLocation().iy()) -4,
//                Math.max(maxXNodeData.getLocation().ix(), maxYNodeData.getLocation().iy()) +3);
//
//        double circleRadius = Math.sqrt(Math.pow(maxXNodeData.getLocation().ix() - minXNodeData.getLocation().ix(), 2)
//                + Math.pow(maxYNodeData.getLocation().iy() - minYNodeData.getLocation().iy(), 2))
//                / 350;
//
//        // draw vertices
//        for(oop_node_data nodeData : this.graph.getV()) {
//           StdDraw.filledCircle(nodeData.getLocation().x(), nodeData.getLocation().y(), circleRadius);
//            StdDraw.setPenColor(Color.blue);
//            StdDraw.setPenRadius(0.02);
//            OOP_Point3D p = nodeData.getLocation();
//            StdDraw.point(p.x(), p.y());
//            String s = nodeData.getKey()+"";
//            StdDraw.text(p.x()+0.5, p.y()+0.5, s);
//            StdDraw.setPenRadius(0.005);
//        }
//        Collection<oop_node_data> nodes = graph.getV();
//        Iterator<oop_node_data> i ;
//        i = nodes.iterator();
//        while (i.hasNext()) {
//            oop_node_data nd = i.next();
//            Iterator <oop_edge_data> edgei = graph.getE(nd.getKey()).iterator();
//            while (edgei.hasNext()) {
//                oop_edge_data EDGE = edgei.next();
//                OOP_Point3D src = nd.getLocation();
//                OOP_Point3D dest = graph.getNode(EDGE.getDest()).getLocation();
//                double w = EDGE.getWeight();
//                StdDraw.setPenColor(Color.black);
//                StdDraw.line(src.x(), src.y(), dest.x(), dest.y());
//                StdDraw.setPenColor(Color.red);
//                StdDraw.text((dest.x()+src.x())/2, (dest.y()+src.y())/2, String.format("%.1f", w));
//            }
//        }
//        try {
//
//            Thread.sleep(10000);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//
