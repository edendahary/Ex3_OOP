package dataStructure;

import java.io.Serializable;

public class Edge implements edge_data, Serializable {
    private int srcKey, destKey;
    private double weight;
    private String info;
    private int tag;

    public Edge(int srcKey, int destKey, double weight) {
        this.srcKey = srcKey;
        this.destKey = destKey;
        this.weight = weight;
    }

    @Override
    public int getSrc() {
        return srcKey;
    }

    @Override
    public int getDest() {
        return destKey;
    }

    @Override
    public double getWeight() {
        return weight;
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

    public static int hashCode(int srcKey, int destKey) {
        int res = 17;

        res = res * 13  + srcKey;
        res = res * 13 + destKey;

        return res;
    }

    @Override
    public int hashCode() {
        return hashCode(this.srcKey, this.destKey);
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Edge)) {
            return false;
        }

        Edge otherEdge = (Edge)obj;

        return srcKey == otherEdge.srcKey && destKey == otherEdge.destKey;
    }
}
