package grammar;

import utils.Utils;

import java.util.List;

public class TypeDetector {
    private Grammar grammar;
    private List<Rule> rules;
    public TypeDetector(Grammar grammar) {
        this.grammar = grammar;
        this.rules = grammar.getRules();
    }
    public Type getType() {
        if (isType3("left")) return Type.TYPE3L;
        if (isType3("right")) return Type.TYPE3R;
        if (isType2()) return Type.TYPE2;
        if (isType1Nonshort()) {
            if (isType1Dependent()) return Type.TYPE1DEP;
            return Type.TYPE1NS;
        }
        return Type.TYPE0;
    }
    private boolean isType3(String line) {
        int shift = 0;
        if (line.equals("left")) shift++;
        for (Rule rule : rules) {
            String left = rule.left();
            String right = rule.right();
            if (!isNonTerminal(left)) return false;
            if (right.equals("eps")) continue;
            for (Character element : right.substring(shift, right.length() - 1 + shift).toCharArray()) {
                if (!isTerminal(element.toString())) return false;
            }
        }
        return true;
    }
    private boolean isType2() {
        for (Rule rule : rules) {
            String left = rule.left();
            String right = rule.right();
            if (!isNonTerminal(left)) return false;
        }
        return true;
    }
    private boolean isType1Dependent() {
        boolean startInRight = rules.stream().anyMatch(x -> x.right().contains(grammar.start()));
        for (Rule rule : rules) {
            String left = rule.left();
            String right = rule.right();
            if (right.equals("eps")) {
                if (!left.equals(grammar.start()) || startInRight) return false;
                continue;
            }
            boolean leftHasNT = left.chars().mapToObj(c -> ((char) c)).anyMatch(x -> grammar.non_terminal().contains(x.toString()));
            if (!leftHasNT) return false;
            for (int i = 0; i < left.length(); i++) {
                if (!isNonTerminal(String.valueOf(left.charAt(i)))) continue;
                String ksi1 = left.substring(0, i);
                String ksi2 = left.substring(i + 1);
                if (right.startsWith(ksi1) && right.endsWith(ksi2)) {
                    String gamma;
                    gamma = right.replaceFirst(ksi1, "");
                    gamma = Utils.replaceLast(gamma, ksi2, "");
                    if (gamma.isBlank()) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean isType1Nonshort() {
        boolean startInRight = rules.stream().anyMatch(x -> x.right().contains(grammar.start()));
        for (Rule rule : rules) {
            String left = rule.left();
            String right = rule.right();
            if (right.equals("eps")) {
                if (!left.equals(grammar.start()) || startInRight) return false;
                continue;
            }
            if (left.length() > right.length()) return false;
        }
        return true;
    }
    private boolean isTerminal(String element) {
        return (element.length() == 1) && (grammar.terminal().contains(element));
    }
    private boolean isNonTerminal(String element) {
        return (element.length() == 1) && (grammar.non_terminal().contains(element));
    }
}