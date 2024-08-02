package org.dstu.regex;

import java.util.ArrayList;
import java.util.List;

public class AlphabetRegex extends Regex {
    private final List<Character> alphabet;
    public AlphabetRegex(String expression, List<Character> alphabet) {
        super(expression);
        alphabetValidation(alphabet);
        this.alphabet = alphabet;
    }
    private void alphabetValidation(List<Character> alphabet)
            throws IllegalArgumentException {
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

    public List<Character> getAlphabet() {
        return alphabet;
    }
}
