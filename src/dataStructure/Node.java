package dataStructure;

import utils.Point3D;

import java.io.Serializable;
import java.util.Objects;

public class Node implements node_data, Serializable {
    private int key;
    private Point3D location;
    private double weight;
    private String info;
    private int tag;

    public Node(int key, Point3D location, double weight, String info, int tag) {
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    @Override
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public Point3D getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point3D location) {
        this.location = location;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key == node.key &&
                Double.compare(node.weight, weight) == 0 &&
                location.equals(node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, location, weight);
    }
}
