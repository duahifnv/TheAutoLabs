package auto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс минимизации конечного автомата
 */
public class Minimize {
    private final Map<Auto, List<Auto>> automata;
    private final List<Character> alph;
    public Minimize(Map<Auto, List<Auto>> automata, List<Character> alph) {
        this.automata = automata;
        this.alph = alph;
    }
    /** Поиск классов эквиваленции */
    public void findClasses() {
        List<List<Auto>> classes = new ArrayList<>() {{
            add(new ArrayList<>());
            add(new ArrayList<>());
        }};
        // Начальное разделение на два класса
        for (Auto auto : automata.keySet()) {
            if (!auto.getState().equals("end")) {
                classes.get(0).add(auto);
            }
            else classes.get(1).add(auto);;
        }
        List<List<Auto>> finalClasses = new ArrayList<>();
        // Проход по неразбитым классам пока они есть
        while (classes.size() != 0) {
            for (List<Auto> clazz : List.copyOf(classes)) {
                // Класс состоит из одного элемента
                if (clazz.size() == 1) {
                    finalClasses.add(clazz);
                    classes.remove(clazz);
                    if (classes.size() == 0) break;
                    continue;
                }
                boolean gotSlice = false;
                for (int i = 0; i < alph.size() - 1; i++) {
                    for (Auto auto : clazz) {
                        Auto jump = automata.get(auto).get(i);  // p(i) -> p(j)
                        // Если нет перехода
                        if (jump == null) continue;
                        // Класс содержит переходы не из своего класса
                        if (!clazz.contains(jump)) {
                            clazz.remove(auto); // Удаляем элемент из класса
                            // Добавляем новый класс из одного элемента в финальный список
                            finalClasses.add(new ArrayList<>(Collections.singleton(auto)));
                            gotSlice = true;   // Разбили класс хотя бы раз
                            break;
                        }
                    }
                    if (!gotSlice) continue;
                    // Остановка разбиения класса
                    break;
                }
                if (!gotSlice) {
                    classes.remove(clazz);; // Удаляем класс из неразбитых классов
                    if (classes.size() == 0) break;
                    finalClasses.add(clazz); // Добавляем класс в финальный список
                }
            }
        }
        System.out.println("Классы эквивалентности:");
        for(List<Auto> clazz : finalClasses) {
            List<String> values = clazz.stream().map(x -> "q" + x.getIdx().toString()).toList();
            String format = String.join(",", values);
            System.out.println("[" + format + "]");
        }
    }
}
