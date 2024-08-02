package org.dstu.regex;

public class Regex {
    protected String expression;
    protected static final String REGEX_SYMBOLS = "[|]?*+.!";
    protected String endSymbol;

    public Regex(String expression) {
        this.expression = expression;
        compile();
    }

    private void compile() {
        expression = formatExpression(expression);
        int prefixIndex = expression.indexOf('+');
        endSymbol = expression.substring(prefixIndex + 1);
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
}
