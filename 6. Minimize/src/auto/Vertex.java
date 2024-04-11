package auto;

/**
 * Вершина
 */
public class Vertex {
    private final int idx;
    private String state;
    public Vertex(int idx) {
        this.idx = idx;
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
