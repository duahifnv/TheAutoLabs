package auto;

import java.util.*;

/**
 * Класс минимизации конечного автомата
 */
public class Minimize {
    // Метод нахождения недостижимых вершин
    public static Set<Vertex>
    findUnreachable(Vertex start, Map<Vertex, List<Vertex>> automata, List<Character> alph) {
        Map<Vertex, List<Vertex>> this_automata = new HashMap<>(automata);
        Set<Vertex> reachedVx = new HashSet<>(List.of(start));
        widthSearch(reachedVx, start, this_automata, alph);
        Set<Vertex> unreachedVx = this_automata.keySet();
        unreachedVx.removeAll(reachedVx);
        if (unreachedVx.size() != 0) return unreachedVx;
        else return null;
    }
    // Рекурсивный метод поиска в ширину
    public static void
    widthSearch(Set<Vertex> reachedVx, Vertex start, Map<Vertex, List<Vertex>> automata, List<Character> alph) {
        for (int i = 0; i < alph.size(); i++) {
            Vertex nextVx = automata.get(start).get(i);
            if (!reachedVx.contains(nextVx)) {
                reachedVx.add(nextVx);
                widthSearch(reachedVx, nextVx, automata, alph);
            }
        }
    }
    /** Поиск классов эквиваленции */
    private static List<Set<Vertex>>
    findClasses(Map<Vertex, List<Vertex>> automata, List<Character> alph) {
        List<Set<Vertex>> classes = new ArrayList<>() {{
            add(new HashSet<>());
            add(new HashSet<>());
        }};
        // Начальное разделение на два класса
        for (Vertex vertex : automata.keySet()) {
            if (!vertex.getState().equals("end")) {
                classes.get(0).add(vertex);
                vertex.setClazz(0);
            }
            else {
                classes.get(1).add(vertex);
                vertex.setClazz(1);
            }
        }
        boolean findClasses = true; // Флаг нахождения классов эквиваленции
        // Проход по неразбитым классам пока они есть
        List<Set<Vertex>> newClasses = new ArrayList<>(classes);
        while (findClasses) {
            findClasses = false;
            for (int i = 0; i < classes.size(); i++) {
                Set<Vertex> clazz = classes.get(i);
                // Класс состоит из одного элемента
                if (clazz.size() == 1) continue;
                for (int j = 0; j < alph.size(); j++) {
                    // Массив групп фиксированного размера
                    List<Set<Vertex>> staticClasses = classes;
                    List<Set<Vertex>> groups = new ArrayList<>() {{
                        for (int k = 0; k < staticClasses.size(); k++) {
                            add(new HashSet<>());
                        }
                    }};
                    for (Vertex vx : clazz) {
                        Vertex jump = automata.get(vx).get(j);  // Переход (q, w) -> q
                        groups.get(jump.getClazz()).add(vx);    // groups[idx] -> vx
                    }
                    // Отбираем непустые группы
                    groups = groups.stream().filter(list -> (!list.isEmpty()))
                            .toList();
                    // Проверяем, в различные ли классы попали вершины
                    if (groups.size() > 1) {
                        // Старый класс -> несколько новых классов
                        newClasses.remove(clazz);
                        newClasses.addAll(groups);
                        findClasses = true;
                        break;
                    }
                }
            }
            // Новые индексы классов у вершин
            int index = 0;
            for (Set<Vertex> newClazz : newClasses) {
                for (Vertex vx : newClazz) {
                    vx.setClazz(index);
                }
                index++;
            }
            classes = newClasses;
        }
        System.out.println("Классы эквивалентности:");
        for(Set<Vertex> clazz : classes) {
            List<String> values = clazz.stream().map(x -> "q" + x.getIdx().toString()).toList();
            String format = String.join(",", values);
            System.out.println("[" + format + "]");
        }
        return classes;
    }
    public static boolean minimize(Map<Vertex, List<Vertex>> automata, List<Character> alph) {
        // Находим классы эквивалентности
        List<Set<Vertex>> classes = findClasses(automata, alph);
        // Из них находим те, в которых больше одного элемента
        List<Set<Vertex>> multiClasses = classes.stream().filter(x -> x.size() > 1).toList();
        if (multiClasses.size() == 0) {
            return false;
        }
        for (Set<Vertex> clazz : multiClasses) {
            List<Vertex> clazzList = new ArrayList<>(clazz);
            Vertex first = clazzList.get(0);
            List<Vertex> row = new ArrayList<>();
            // Переходы на эквивалентные элементы заменяем первым элементом
            for (int i = 0; i < alph.size(); i++) {
                Vertex jump = automata.get(first).get(i);
                if (clazz.contains(jump)) {
                    row.add(first);
                    automata.remove(jump);  // Удаляем эквивалетный элемент из автомата
                }
                else row.add(jump);
            }
            automata.put(first, row);
        }
        return true;
    }
}
