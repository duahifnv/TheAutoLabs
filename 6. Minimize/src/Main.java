import auto.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Получаем алфавит входных символов
        Alphabet a = new Alphabet();
        a.SetAlph();
        List<Character> alph = a.GetAlph();
        // Размер графа
        Input input = new Input("exit");
        int size = input.Size("Введите число вершин", 2, 10);
        // Массив вершин
        List<Vertex> vxs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Vertex vx = new Vertex(i);
            vxs.add(vx);
        }
        // Расставляем конечные вершины
        Set<Integer> endVxs;
        endVxs = input.Set("Введите конечные вершины",
                "Введите конечную вершину", -1, 1, size - 1);
        for (Vertex vx : vxs) {
            if (endVxs.contains(vx.getIdx())) vx.setState("end");
        }
        // Устанавлиаем начальную вершину
        int startIdx = input.Size("Введите начальную вершину", 0, size - 1);
        vxs.get(startIdx).setState("start");

        // Ввод детерминированного автомата
        Map<Vertex, List<Vertex>> automata = input.Automata(alph, size, vxs);
        // Вывод детерминированного автомата
        List<String> autoLabels = new ArrayList<>(List.of("Вершина"));
        for (Character character : alph) {
            autoLabels.add(character.toString());
        }
        // FIXME: Сортировка вершин по индексу
        Utils.BuildTable(autoLabels, automata, "Детерминированный автомат");
        // Находим и удаляем недостижимые вершины
        Set<Vertex> unreachedVx = Minimize.findUnreachable(vxs.get(startIdx), automata, alph);
        if (unreachedVx != null) {
            List<String> format = unreachedVx.stream().map(x -> "q" + x.getIdx()).toList();
            System.out.println("Были удалены недостижимые вершины: " + String.join(",", format));
            for (Vertex vx : unreachedVx) {
                automata.remove(vx);
            }
        }
        Utils.BuildTable(autoLabels, automata, "Детерминированный автомат (без недостижимых)");
        /*if (!Minimize.minimize(autoJump, alph)) {
            System.out.println("Исходный автомат уже минимален");
        }
        else {
            Determine.BuildTable(autoLabels, automata, "Минимизированный автомат");
        }*/
    }
}