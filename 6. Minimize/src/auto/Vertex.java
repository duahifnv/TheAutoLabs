package auto;

/**
 * Вершина
 */
public class Vertex {
    private final int idx;
    private String state = "none";
    public Vertex(int idx, String state) {
        this.idx = idx;
        this.state = state;
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
}
