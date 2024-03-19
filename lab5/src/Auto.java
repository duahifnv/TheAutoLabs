import java.util.List;

public class Auto extends Idxable{
    private Integer idx;
    private List<State> states;
    private String label = "P?";
    public Auto(Integer idx, List<State> states, String label) {
        this.idx = idx;
        this.states = states;
        this.label = label;
    }
    public Auto(Integer idx, List<State> states) {
        this.idx = idx;
        this.states = states;
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
}
