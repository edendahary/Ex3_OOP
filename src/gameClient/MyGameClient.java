package gameClient;

import Server.Game_Server;
import Server.game_service;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_node_data;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.*;

public class MyGameClient {
    private  int scenarioNum;
    private  game_service gameService;
    private  OOP_DGraph dgraph;
    private  oop_GraphAlgo graphAlgo;


    public MyGameClient(int scenarioNum) throws JSONException {
        this.scenarioNum = scenarioNum;
        this.gameService = Game_Server.getServer(scenarioNum); // you have [0,23] games
        this.dgraph = new OOP_DGraph();
        this.dgraph.init(this.gameService.getGraph());
        this.graphAlgo = new oop_GraphAlgo(this.dgraph);
        MyGUI T=new MyGUI(dgraph);
        // init robtos
        JSONObject infoObject = new JSONObject(this.gameService.toString());
        JSONObject gameObject = infoObject.getJSONObject("GameServer");
        int numOfRobots = gameObject.getInt("robots");
        oop_node_data nodeData = dgraph.getV().iterator().next();
        for(int i = 0; i < numOfRobots; i++) {
            // init robots
            gameService.addRobot(nodeData.getKey());
        }
    }

    public MyGameClient() {

    }


    public void start() throws JSONException {
        this.gameService.startGame();
        while(this.gameService.isRunning()) {
            List<Fruit> fruitList = getFruitList();
            List<Robot> robotList = getRobotList();
            moveRobots(fruitList, robotList);

        }
    }



    private void moveRobots(List<Fruit> fruitList, List<Robot> robotList){
        List<String> log = this.gameService.move();

        List<oop_edge_data> fruitEdges = Fruit.getEdges(fruitList, this.dgraph);

        Iterator<oop_node_data> it = dgraph.getV().iterator();
        oop_node_data lastNode = it.next();

        for (Robot r:robotList){
            if(r.getDes() == -1){
              gameService.chooseNextEdge(r.getId(),setRobotsToFruit(dgraph, r,fruitEdges));
//                if(it.hasNext()) {
//                    lastNode = it.next();
//                }
                gameService.chooseNextEdge(r.getId(),lastNode.getKey());
            }
        }
//        System.out.println(fruitList.toString());
//        System.out.println(robotList.toString());
    }
    private List<Robot> getRobotList() throws JSONException {
        List<Robot> RobotList = new ArrayList<>();
        List<String> RobotStrs = this.gameService.getRobots();

        for(String RobotStr : RobotStrs){
            Robot robot = Robot.parseJSON(new JSONObject(RobotStr));
            RobotList.add(robot);
        }
        return RobotList;
    }
    private List<Fruit> getFruitList() throws JSONException {
        List<Fruit> fruitList = new ArrayList<>();
        List<String> fruitStrs = this.gameService.getFruits();
        for(String fruitStr : fruitStrs) {
            Fruit fruit = Fruit.parseJSON(new JSONObject(fruitStr));
            fruitList.add(fruit);
        }

        return fruitList;
    }
//    public static void main(String[] args) throws JSONException {
//    int x =2;
//    MyGameClient z=new MyGameClient(x);
//    z.start();
//
//}
    public int  setRobotsToFruit(OOP_DGraph graph, Robot r, List<oop_edge_data> FruitL)  {
        for (oop_edge_data f:FruitL) {
            if(r.getSrc() == f.getSrc()){
                return f.getDest();
            }else {
                List<oop_node_data> Path ;
              Path=graphAlgo.shortestPath(r.getSrc(), f.getSrc());
              if(Path!=null){
                  return Path.get(1).getKey();
              }
            }
        }
        return -1;
    }

}
