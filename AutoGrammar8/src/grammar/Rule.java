package grammar;

public record Rule(String left, String right) {
    @Override
    public String toString() {
        return left + " -> " + right;
    }
}
