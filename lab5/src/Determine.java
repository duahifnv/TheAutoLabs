import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Determine {
    private List<Character> alph;
    private List<Vertex> vertexes;
    private Map<Integer, List<List<Vertex>>> vertexJump;
    private List<State> epsStates;
    public Determine(List<Vertex> vertexes, Map<Integer, List<List<Vertex>>> vertexJump, List<Character> alph) {
        this.vertexes = vertexes;
        this.vertexJump = vertexJump;
        this.alph = alph;
    }
    public List<State> CreateEps() {
        List<State> epsStates = new ArrayList<>();

        for (Integer idx : vertexJump.keySet()) {
            Vertex start = vertexes.get(idx);
            List<Vertex> dst = new ArrayList<>(Arrays.asList(start));
            List<Vertex> epsVxs = vertexJump.get(idx).get(0);
            if (epsVxs != null) {
                for (Vertex vertex : epsVxs) {
                    EpsVector(vertex, dst);
                }
            }
            State st = new State(idx, dst, "S" + idx.toString());
            epsStates.add(st);
        }
        this.epsStates = epsStates;
        return epsStates;
    }
    public void PrintEps() {
        System.out.println("E-переходы: ");
        for (int i = 0; i < vertexes.size(); i++) {
            List<Vertex> dst = epsStates.get(i).getVertexs();
            List<String> formatDst = dst.stream().map(idx -> "q" + idx.getIdx()).collect(Collectors.toList());
            String stringDst = String.join(", ", formatDst);
            System.out.printf("~(q%d): {%s}%n", i, stringDst);
        }
    }
    // Рекурсивная функция прохождения по эпсилон переходам
    private void EpsVector(Vertex v, List<Vertex> dst) {
        List<Vertex> epsVxs;
        if (!dst.contains(v)) {
            dst.add(v);
            int nextIdx = v.getIdx();
            epsVxs = vertexJump.get(nextIdx).get(0);
            if (epsVxs == null) return;
            for (Vertex vertex : epsVxs) {
                EpsVector(vertex, dst);
            }
        }
        return;
    }
    // Рекурсивная функция прохождения по всем переходам
    public void Vector(Vertex v, Set<Vertex> dst, Integer letterIdx, Boolean gotLetter) {
        if (gotLetter) dst.add(v);  // Буква в цепочке уже есть
        Integer vIdx = v.getIdx();
        List<Vertex> vEps = vertexJump.get(vIdx).get(0);
        List<Vertex> vLtr = vertexJump.get(vIdx).get(letterIdx);
        // Прервалась цепочка
        if (vEps == null && vLtr == null) return; // Конец цепочки
        if (vEps != null) {
            for (Vertex vertex : vEps) {
                if (dst.contains(vertex))
                    continue; // Избегаем зацикливания
                Vector(vertex, dst, letterIdx, gotLetter);
            }
        }
        if (vLtr != null && !gotLetter) {
            for (Vertex vertex : vLtr) {
                if (dst.contains(vertex))
                    continue;
                Vector(vertex, dst, letterIdx, true);
            }
        }
        return;
    }
    // Рекурсивная функция нахождения всех множеств состояний
    public void FindAutos(Auto auto, Set<Auto> dst, Map<Integer,
     List<List<State>>> stateJump, Map<Auto, List<Auto>> autoJump) {
        List<Auto> jumps = new ArrayList<>();   // Список переходов на другие множества
        // Проход по алфавиту без Е
        for (int i = 1; i < alph.size(); i++) {
            Set<Auto> uniqueJumps = new HashSet<>(); // Множество уникальных переходов по букве
            for (State state : auto.getStates()) {
                List<State> jump = stateJump.get(i - 1).get(state.getIdx());    // (S, w) -> {S}
                if (jump == null) continue;
                // Проверка на уже имеющееся множество
                Boolean isNew = true;
                for (Auto dstA : dst) {
                    if (jump.equals(dstA.getStates())) {
                        uniqueJumps.add(dstA);
                        isNew = false;
                        break;
                    }
                }
                // Добавление нового множества
                if (isNew) {
                    Integer index = dst.size();
                    String label = "P" + String.valueOf(index);
                    Auto newA = new Auto(index, jump, label);
                    dst.add(newA);
                    uniqueJumps.add(newA);
                    FindAutos(newA, dst, stateJump, autoJump);
                }
            }
            jumps.addAll(uniqueJumps);
        }
        autoJump.put(auto, jumps);
        return;
    }

    public List<List<String>> MapToMatrix(Map<Auto, List<Auto>> map) {
        List<List<String>> params = new ArrayList<>();
        List<Auto> labels = new ArrayList<>(map.keySet());

        int n_rows = map.size();
        for (int i = 0; i < n_rows; i++) {
            List<String> new_row = new ArrayList<>();
            Auto label = labels.get(i);
            new_row.add(label.getLabel());

            List<Auto> map_row = map.get(label);
            for (int j = 0; j < map_row.size(); j++) {
                String name = map_row.get(j).getLabel();
                new_row.add(name);
            }
            params.add(new_row);
        }
        return params;
    }
}
