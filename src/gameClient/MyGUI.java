package gameClient;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_node_data;
import oop_elements.OOP_NodeData;
import oop_utils.OOP_Point3D;
import org.json.JSONException;
import utils.StdDraw;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MyGUI extends JPanel {
    JFrame jFrame;
    private OOP_DGraph graph;
    private oop_GraphAlgo graphAlgo;
    private MyGameClient game ;

    public MyGUI(MyGameClient game) throws JSONException {
        // Initialize an empty graph and painting.
        graph = new OOP_DGraph();
        graphAlgo = new oop_GraphAlgo();
        graphAlgo.init(graph);
        game.start();
        drawGUI();
        // draw
    }

    public MyGUI(OOP_DGraph graph) {
        this.graph = graph;
        graphAlgo = new oop_GraphAlgo();
        graphAlgo.init(graph);
        drawGUI();
    }

    public void drawGUI() {
        jFrame = new JFrame();
        jFrame.setTitle("Draw Graph");
        jFrame.setSize(800, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(this);

    }

    public void paintComponent(Graphics g) {
        for (oop_node_data CurrNode : this.graph.getV()) {
            g.setColor(Color.black);
            g.fillOval(CurrNode.getLocation().ix(), CurrNode.getLocation().iy() , 10, 10);
        }
        Collection<oop_node_data> nodes = graph.getV();
        Iterator<oop_node_data> i;
        i = nodes.iterator();
        while (i.hasNext()) {
            oop_node_data nd = i.next();
            Iterator<oop_edge_data> edgei = graph.getE(nd.getKey()).iterator();
            while (edgei.hasNext()) {
                oop_edge_data EDGE = edgei.next();
                OOP_Point3D src = nd.getLocation();
                OOP_Point3D dest = graph.getNode(EDGE.getDest()).getLocation();
                double w = EDGE.getWeight();
                g.setColor(Color.blue);
                g.drawLine(src.ix(), src.iy(), dest.ix(), dest.iy());
                g.setColor(Color.ORANGE);
                int src_x = src.ix();
                int src_y = src.iy();
                int dest_x = dest.ix();
                int dest_y = dest.iy();
                int dir_x = (((((((src_x + dest_x) / 2) + dest_x) / 2) + dest_x) / 2) + dest_x) / 2;
                int dir_y = (((((((src_y + dest_y) / 2) + dest_y) / 2) + dest_y) / 2) + dest_y) / 2;
                g.fillOval(dir_x - 5, dir_y - 5, 10, 10);
                g.setColor(Color.red);
                g.drawString(String.format("%.1f", w), (int) (dest.x() + src.x()) / 2, (int) (dest.y() + src.y()) / 2);
                g.setColor(Color.CYAN);


          //      g.drawOval();

            }
        }
    }

        public static void main (String[]args) throws JSONException {
//            OOP_DGraph e = new OOP_DGraph();
//            oop_GraphAlgo p = new oop_GraphAlgo();
//            e.addNode(new OOP_NodeData(0, 2, new OOP_Point3D(10, 10), "none"));
//            e.addNode(new OOP_NodeData(1, 3, new OOP_Point3D(70, 70), "none"));
//            e.addNode(new OOP_NodeData(2, 4, new OOP_Point3D(100, 200), "none"));
//            e.addNode(new OOP_NodeData(3, 9, new OOP_Point3D(300, 100), "none"));
//            e.addNode(new OOP_NodeData(4, 15, new OOP_Point3D(76, 200), "none"));
//            e.addNode(new OOP_NodeData(5, 13, new OOP_Point3D(150, 220), "none"));
//            e.connect(0, 1, 5);
//            e.connect(1, 2, 12);
//            e.connect(2,3,6);
//            e.connect(3,5,90);
//            p.init(e);
            //MyGUI x = new MyGUI(e);
            MyGameClient game =new MyGameClient(2);

        }

    }



