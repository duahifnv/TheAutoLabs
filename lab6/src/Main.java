import java.util.*;
import java.util.stream.Collectors;

import auto.*;
/**
 * Лабораторная работа #6
 * "Минимизация конечных автоматов-распознавателей"
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("ТЕОРИЯ АВТОМАТОВ И ФОРМАЛЬНЫХ ЯЗЫКОВ\nЛабораторная работа #5");
        System.out.println("Детерминизация конечных автоматов");
        // Получаем алфавит входных символов
        Alphabet a = new Alphabet();
        a.SetAlph(true);
        List<Character> alph = a.GetAlph();
        // Размер графа
        Input input = new Input("exit");
        int size = input.Size("Введите число вершин: ", 2, 10);
        // Массив вершин
        List<Vertex> vxs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Vertex vx = new Vertex(i, null);
            vxs.add(vx);
        }
        // Расставляем конечные и начальные вершины
        Set<Integer> endVxs, startVxs;
        while (true) {
            endVxs = input.Set("Введите конечные вершины",
                    "Введите конечную вершину", -1, 1, size - 1);
            startVxs = input.Set("Введите начальные вершины",
                    "Введите начальную вершину", -1, 1, size - 1);
            break;
        }
        for (Vertex vx : vxs) {
            int vx_idx = vx.getIdx();
            if (endVxs.contains(vx_idx))
                vx.setState("end");
            if (startVxs.contains(vx_idx))
                vx.setState("start");
        }
        // Ввод таблицы переходов
        Map<Integer, List<List<Vertex>>> vertexJump = new HashMap<>();
        System.out.println("Введите переходы ('-1', чтобы закончить):");
        for (int i = 0; i < size; i++) {
            List<List<Vertex>> dst = new ArrayList<>(); // Ряд таблицы
            for (int j = 0; j < alph.size(); j++) {
                List<Vertex> cell = new ArrayList<>(); // Ячейка с вершинами
                String comm = String.format("Введите переходы из q%d по %s: ",
                        i, alph.get(j));
                while (true) {
                    int idx = input.Size(comm, -1, size - 1);
                    if (idx == -1)
                        break;
                    if (cell.contains(vxs.get(idx)))
                        continue;
                    cell.add(vxs.get(idx));
                }
                if (cell.size() == 0)
                    dst.add(null);
                else
                    dst.add(cell);
            }
            vertexJump.put(i, dst);
        }
        List<String> labels = new ArrayList<>(Arrays.asList("Вершина"));
        for (Character letter : alph) {
            labels.add(letter.toString());
        }

        // Вывод таблицы переходов
        Utils.PrintTableFromMap(vertexJump, labels, "q", "Таблица переходов", 7);

        // Вывод эпсилон замыкания
        Determine det = new Determine(vxs, vertexJump, alph);
        List<State> epsStates = det.CreateEps();
        det.PrintEps();

        Map<Integer, List<List<State>>> stateJump = new HashMap<>(); // Таблица состояний
        // Составляем таблицу состояний
        for (State state : epsStates) { // Проходим состояния
            List<List<State>> row = new ArrayList<>(); // Очередной ряд таблицы
            System.out.print(state.getLabel() + ": "); // S0, S1, S2, ...
            Integer idx = state.getIdx();
            List<Vertex> values = state.getVertexs(); // Список вершин в состоянии

            for (int i = 1; i < alph.size(); i++) { // Проход всех букв
                Set<Vertex> dstList = new HashSet<>(); // Множество вершин, куда можно попасть по букве

                for (Vertex value : values) {
                    Set<Vertex> dstPrev = new HashSet<>();
                    dstPrev.addAll(dstList);
                    det.Vector(value, dstList, i, false);
                    // Вывод новых переходов
                    for (Vertex vx : dstList) {
                        if (!dstPrev.contains(vx)) {
                            System.out.printf("(q%d, %c) -> q%d%n", value.getIdx(), alph.get(i), vx.getIdx());
                        }
                    }
                }
                // Если по букве нет ни одного перехода
                if (dstList.isEmpty())
                    System.out.printf("(%s, %c) -> NaN%n",
                            values.stream().map(index -> "q" + index.getIdx()).collect(Collectors.toList()),
                            alph.get(i));
                // Смотрим какие состояния входят в наш новый список вершин
                List<State> matchStates = new ArrayList<>();
                for (State epState : epsStates) {
                    Boolean isNested = true;
                    for (Vertex vx : epState.getVertexs()) {
                        if (!dstList.contains(vx)) {
                            isNested = false;
                            break;
                        }
                    }
                    if (isNested)
                        matchStates.add(epState);
                }
                if (matchStates.isEmpty())
                    row.add(null);
                else
                    row.add(matchStates);
            }
            stateJump.put(idx, row);
        }

        // Вывод таблицы состояний
        List<String> stLabels = new ArrayList<>(Arrays.asList("Состояние"));
        for (int i = 1; i < alph.size(); i++) {
            stLabels.add(alph.get(i).toString());
        }
        Utils.PrintTableFromMap(stateJump, stLabels, "S", "Таблица состояний", 10);
        // Начальное множество состояний
        List<State> start = new ArrayList<>();
        for (State state : epsStates) {
            Boolean isStart = true;
            for (Vertex vx : state.getVertexs()) {
                // Есть хоть одна не начальная вершина - состояние не подходит
                if (vx.getState() != "start") {
                    isStart = false;
                    break;
                }
            }
            if (isStart) start.add(state);
        }
        Auto startA = new Auto(0, start, "P0", "start");
        List<String> startContainer = new ArrayList<>();
        for (State state : start) {
            startContainer.add(state.getLabel());
        }
        System.out.printf("Начальное множество: %s -> {%s}%n", startA.getLabel(), String.join(", ", startContainer));
        Map<Auto, List<Auto>> autoJump = new HashMap<>(); // Таблица автомата
        Set<Auto> autos = new HashSet<>(Arrays.asList(startA)); // Множество найденных
        det.FindAutos(startA, autos, stateJump, autoJump);

        List<String> autoLabels = new ArrayList<>(Arrays.asList("Множество"));
        for (int i = 1; i < alph.size(); i++) {
            autoLabels.add(alph.get(i).toString());
        }

        List<List<String>> autoParams = det.MapToMatrix(autoJump, true);
        List<Integer> aFieldSizes = new ArrayList<Integer>(Collections.nCopies(autoLabels.size(), 10));
        Utils.PrintTable(autoLabels.size(), autoLabels, autoParams,
                "Таблица множеств состояний", aFieldSizes);

        Input wordInput = new Input("exit");
        while (true) {
            String word = wordInput.Stroke("Введите слово (exit для выхода)", alph);
            if (word.equals("exit")) break;
            Boolean isValid = det.CheckWord(word, startA, autoJump);
            String validMsg = (isValid) ? "Слово соответствует автомату" : "Слово не соответствует автомату";
            System.out.println(validMsg);
        }
    }
}