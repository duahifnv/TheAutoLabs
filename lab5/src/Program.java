import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("ТЕОРИЯ АВТОМАТОВ И ФОРМАЛЬНЫХ ЯЗЫКОВ\nЛабораторная работа #5");
        System.out.println("Детерминизация конечных автоматов");
        // Получаем алфавит входных символов
        Alphabet a = new Alphabet();
        a.SetAlph(true);
        List<Character> alph = a.GetAlph();
        // Размер графа
        Input input = new Input();
        int size = input.Size("Введите число вершин: ", 2, 10);
        // Массив вершин
        List<Vertex> vxs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Vertex vx = new Vertex(i, null);
            vxs.add(vx);
        }
        // Расставляем конечные и начальные вершины
        Set<Integer> endVxs, startVxs;
        while(true) {
            endVxs = input.Set("Введите конечные вершины",
                    "Введите конечную вершину", -1, 1, size - 1);
            startVxs = input.Set("Введите начальные вершины",
                    "Введите начальную вершину", -1, 1, size - 1);
            // if (!(Collections.disjoint(endVxs, startVxs) && Collections.disjoint(startVxs, endVxs))) {
            //     System.out.println("Вершины не могут быть и конечными и начальными");
            // continue;
            // }
            break;
        }
        for (Vertex vx : vxs) {
            int vx_idx = vx.getIdx();
            if (endVxs.contains(vx_idx)) vx.setState(true);
            if (startVxs.contains(vx_idx)) vx.setState(false);
        }
        // Ввод таблицы переходов
        Map<Integer, List<List<Vertex>>> tableJump = new HashMap<>();
        System.out.println("Введите переходы ('-1', чтобы закончить):");
        for (int i = 0; i < size; i++) {
            List<List<Vertex>> dst = new ArrayList<>();     // Ряд таблицы
            for (int j = 0; j < alph.size(); j++) {
                List<Vertex> cell = new ArrayList<>();      // Ячейка с вершинами
                String comm = String.format("Введите переходы из q%d по %s: ",
                 i, alph.get(j));
                while (true) {
                    int idx = input.Size(comm, -1, size - 1);
                    if (idx == -1) break;
                    if (cell.contains(vxs.get(idx))) continue;
                    cell.add(vxs.get(idx));
                }
                if (cell.size() == 0) dst.add(null);
                else dst.add(cell);
            }
            tableJump.put(i, dst);
        }
        List<String> labels = new ArrayList<>(Arrays.asList("Вершина"));
        for (Character letter : alph) {
            labels.add(letter.toString());
        }

        // Вывод таблицы переходов
        // String[][] params = Utils.MapToMatrix(tableJump);
        // int[] fieldSizes = new int[labels.size()];
        // Arrays.fill(fieldSizes, 7);
        // Utils.PrintTable(labels.size(), labels.toArray(new String[0]), params, fieldSizes);
        List<List<String>> params = Utils.MapToMatrix(tableJump);
        List<Integer> fieldSizes = new ArrayList<Integer>(Collections.nCopies(labels.size(), 7));
        Utils.PrintTable(labels.size(), labels, params, fieldSizes);

        // TODO: (Со звездочкой) Вывести эпсилон замыкания
        // Map<Integer, List<Vertex>> eps = new HashMap<>();
        // for (Integer idx : tableJump.keySet()) {
        //     Vertex start = vxs.get(idx);
        //     List<Vertex> dst = new ArrayList<>(Arrays.asList(start));

        //     while(true) {
        //         List<Vertex> jumps = tableJump.get(idx);
        //         if (jumps.get(0) != null) {
        //             if ()
        //         }
        //     }

        // }
        // TODO: Детерминизация автомата и вывод таблицы состояний



    }
}
