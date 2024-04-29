package grammar;

public enum Type {
    TYPE3R("Класс 3: Праволинейная грамматика"),
    TYPE3L("Класс 3: Леволинейная грамматика"),
    TYPE2("Класс 2: Контекстно-свободная грамматика"),
    TYPE1DEP("Класс 1: Контекстно-зависимая грамматика"),
    TYPE1NS("Класс 1: Неукорачивающая грамматика"),
    TYPE0("Класс 0: Неограниченная грамматика");
    private String label;
    Type(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
