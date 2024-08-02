package org.dstu.regex;

import java.util.ArrayList;
import java.util.List;

public class Regex {
    private String expression;
    private static final String REGEX_SYMBOLS = "[|]?*+.!";
    private String endSymbol;
    private final List<Character> alphabet;

    public Regex(List<Character> alphabet, String expression) {
        this.alphabet = alphabet;
        this.expression = expression;
        compile(alphabet);
    }

    private void compile(List<Character> alphabet) {
        expression = formatExpression(expression);

        int prefixIndex = expression.indexOf('+');
        endSymbol = expression.substring(prefixIndex + 1);

        alphabetValidation(alphabet);
    }

    private void alphabetValidation(List<Character> alphabet) throws IllegalArgumentException {
        List<Character> alphabetFromExpression = new ArrayList<>();
        for (char ch : expression.toCharArray()) {
            if (REGEX_SYMBOLS.indexOf(ch) == -1 && !alphabetFromExpression.contains(ch)) {
                alphabetFromExpression.add(ch);
            }
        }

        if (alphabet.size() > alphabetFromExpression.size()) {
            throw new IllegalArgumentException(
                    "Алфавит " + alphabet + " содержит символы не из регулярного выражения!"
            );
        }
        if (alphabet.size() < alphabetFromExpression.size()) {
            throw new IllegalArgumentException(
                    "В алфавите " + alphabet + " не хватает символов!"
            );
        }
        System.out.println(alphabetFromExpression);
        for (char ch : alphabet) {
            if (!alphabetFromExpression.contains(ch)) {
                throw new IllegalArgumentException(
                        "Алфавит " + alphabet + " содержит символы не из регулярного выражения!"
                );
            }
        }
    }

    protected String formatExpression(String expression) {
        if (expression.charAt(0) == '^') {
            expression = expression.substring(1);
        }
        if (expression.charAt(expression.length() - 1) == '$') {
            expression = expression.substring(0, expression.length() - 1);
        }
        return expression;
    }

    public String getExpression() {
        return expression;
    }

    public String getEndSymbol() {
        return endSymbol;
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }
}
