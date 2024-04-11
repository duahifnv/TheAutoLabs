import auto.Alphabet;
import auto.Input;
import auto.Vertex;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Получаем алфавит входных символов
        Alphabet a = new Alphabet();
        a.SetAlph();
        List<Character> alph = a.GetAlph();
        // Размер графа
        Input input = new Input("exit");
        int size = input.Size("Введите число вершин: ", 2, 10);
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
    }
}