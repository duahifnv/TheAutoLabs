package grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Grammar(String terminal,
                      String non_terminal,
                      String rules) {
    public List<Rule> getRules() {
        List<Rule> ruleList = new ArrayList<>();
        for (String rule : rules.split("&")) {
            String[] sides = rule.split("->");
            ruleList.add(new Rule(sides[0], Arrays.stream(sides[1].split("\\|")).toList()));
        }
        return ruleList;
    }
}