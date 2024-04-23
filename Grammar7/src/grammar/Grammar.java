package grammar;

import java.util.ArrayList;
import java.util.List;

public record Grammar(String terminal,
                      String non_terminal,
                      String rules,
                      String start) {
    public List<Rule> getRules() {
        List<Rule> ruleList = new ArrayList<>();
        for (String rule : rules.split("&")) {
            String[] sides = rule.split("->");
            for (String right : sides[1].split("\\|")) {
                ruleList.add(new Rule(sides[0], right));
            }
        }
        return ruleList;
    }
    public Type getType() {
        TypeDetector typeDetector = new TypeDetector(this);
        return (typeDetector.getType());
    }
    @Override
    public String toString() {
        return "Грамматика {" +
                "Терминалы: [" + terminal + ']' +
                ", Нетерминалы [" + non_terminal + ']' +
                ", Правила " + getRules() +
                ", Стартовый символ: " + start +
                ", \nКлассификация по Хомскому: " + this.getType().getLabel() + '}';
    }
}