public class Vertex {
    private int idx;
    private Boolean state;
    // Start if state == false, End otherwise
    public Vertex(int idx, Boolean state) {
        this.idx = idx;
        this.state = state;
    }
    public int getIdx() {
        return idx;
    }
    public Boolean getState() {
        return state;
    }
    public void setState(Boolean state) {
        this.state = state;
    }
}
