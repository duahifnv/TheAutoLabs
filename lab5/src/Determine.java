import java.util.ArrayList;
import java.util.Arrays;
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
}
