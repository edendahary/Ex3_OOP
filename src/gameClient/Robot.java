package gameClient;

import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Utils;

import java.util.List;

public class Robot {
    private int src;
    private Point3D pos;
    private int id;
    private int dest;
    private int value;
    private int speed;

    public Robot(int src,Point3D pos,int id, int dest,int value,int speed){
        this.src=src;
        this.pos=pos;
        this.id=id;
        this.dest=dest;
        this.value=value;
        this.speed=speed;
    }

    public static Robot parseJSON(JSONObject obj) throws JSONException{
        JSONObject RobotObj = obj.getJSONObject("Robot");
        int src = RobotObj.getInt("src");
        Point3D pos = Utils.parsePoint3DFromJSON(RobotObj.getString("pos"));
        int id = RobotObj.getInt("id");
        int dest = RobotObj.getInt("dest");
        int value = RobotObj.getInt("value");
        int speed = RobotObj.getInt("speed");
        return new Robot(src,pos,id,dest,value,speed);
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public Point3D getPos() {
        return pos;
    }

    public void setPos(Point3D pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDes() {
        return dest;
    }

    public void setDes(int des) {
        this.dest = des;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "src=" + src +
                ", pos=" + pos +
                ", id=" + id +
                ", dest=" + dest +
                ", value=" + value +
                ", speed=" + speed +
                '}';
    }
}
