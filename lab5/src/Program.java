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
            if (endVxs.contains(vx_idx)) vx.setState("end");
            if (startVxs.contains(vx_idx)) vx.setState("start");
        }
        // Ввод таблицы переходов
        Map<Integer, List<List<Vertex>>> vertexJump = new HashMap<>();
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
            vertexJump.put(i, dst);
        }
        List<String> labels = new ArrayList<>(Arrays.asList("Вершина"));
        for (Character letter : alph) {
            labels.add(letter.toString());
        }

        // Вывод таблицы переходов
        List<List<String>> params = Utils.MapToMatrix(vertexJump);
        List<Integer> fieldSizes = new ArrayList<Integer>(Collections.nCopies(labels.size(), 7));
        Utils.PrintTable(labels.size(), labels, params, fieldSizes);

        // Вывод эпсилон замыкания
        Determine det = new Determine(vxs, vertexJump, alph);
        List<State> epsStates = det.CreateEps();
        det.PrintEps();

        // TODO: Составление и вывод таблицы состояний на основе эпсилон-замыканий
        Map<Integer, List<List<State>>> stateJump = new HashMap<>();    // Таблица состояний
        // Составляем таблицу состояний
        for (State state : epsStates) {     // Проходим состояния
            List<List<State>> row = new ArrayList<>();  // Очередной ряд таблицы
            System.out.print(state.getLabel() + ": "); // S0, S1, S2, ...
            Integer idx = state.getIdx();
            List<Vertex> values = state.getVertexs();           // Список вершин в состоянии
            for (int i = 0; i < alph.size(); i++) {             // Проход всех букв
                List<Vertex> dstList = new ArrayList<>();       // Список вершин, куда можно попасть по букве
                for (Vertex value : values) {
                    det.Vector(value, dstList, i + 1, false);
                    // Вывод переходов
                    for (Vertex dst : dstList) {
                        System.out.printf("(q%d, %c) -> q%d%n", value.getIdx(), alph.get(i), dst.getIdx());
                    }
                }
                // Смотрим какие состояния входят в наш новый список вершин
                List<State> matchStates = new ArrayList<>();
                for (State epState : epsStates) {
                    if (dstList.contains(epState.getVertexs())) matchStates.add(epState);
                }
                row.add(matchStates);
            }
            stateJump.put(state.getIdx(), row);
        }

        // Вывод таблицы состояний
        List<String> stLabels = new ArrayList<>(Arrays.asList("Состояние"));
        for (Character letter : alph) {
            stLabels.add(letter.toString());
        }
        List<List<String>> stParams = Utils.MapToMatrix(stateJump);
        List<Integer> stFieldSizes = new ArrayList<Integer>(Collections.nCopies(labels.size(), 7));
        Utils.PrintTable(stLabels.size(), stLabels, stParams, stFieldSizes);
        // TODO: Детерминизация автомата и вывод таблицы состояний
    }
}
