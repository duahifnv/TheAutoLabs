package auto;

/**
 * Вершина
 */
public class Vertex {
    private final int idx;
    private String state;
    private int clazz;
    public Vertex(int idx) {
        this.idx = idx;
        this.state = "none";
    }
    public Integer getIdx() {
        return idx;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getClazz() {
        return clazz;
    }
    public void setClazz(int clazz) {
        this.clazz = clazz;
    }
}
