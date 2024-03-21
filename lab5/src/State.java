// Структура состояний по эпсилон замыканиям

import java.util.List;

public class State extends Idxable {
    private Integer idx;
    private List<Vertex> vxs;
    private String label = "S?";
    private String state = "none";
    public State(Integer idx, List<Vertex> vxs, String label, String state) {
        this.idx = idx;
        this.vxs = vxs;
        this.label = label;
        this.state = state;
    }
    public State(Integer idx, List<Vertex> vxs) {
        this.idx = idx;
        this.vxs = vxs;
    }
    public Integer getIdx() {
        return idx;
    }
    public List<Vertex> getVertexs() {
        return vxs;
    }
    public String getLabel() {
        return label;
    }
    public String getState() {
        return state;
    }
}
