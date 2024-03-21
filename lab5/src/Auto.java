import java.util.List;

public class Auto extends Idxable{
    private Integer idx;
    private List<State> states;
    private String label = "P?";
    private String state;
    public Auto(Integer idx, List<State> states, String label, String state) {
        this.idx = idx;
        this.states = states;
        this.label = label;
        this.state = state;
    }
    public Auto(Integer idx, List<State> states, String state) {
        this.idx = idx;
        this.states = states;
        this.state = state;
    }

    public Integer getIdx() {
        return idx;
    }

    public List<State> getStates() {
        return states;
    }

    public String getLabel() {
        return label;
    }
    public String getState() {
        return state;
    }
}
