import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Determine {
    private List<Vertex> vertexes;
    private Map<Integer, List<List<Vertex>>> tableJump;
    private Map<Integer, List<Vertex>> eps;

    public Determine(List<Vertex> vertexes, Map<Integer, List<List<Vertex>>> tableJump) {
        this.vertexes = vertexes;
        this.tableJump = tableJump;
    }
    public void CreateEps() {
        Map<Integer, List<Vertex>> eps = new HashMap<>();

        for (Integer idx : tableJump.keySet()) {
            Vertex start = vertexes.get(idx);
            List<Vertex> dst = new ArrayList<>(Arrays.asList(start));
            List<Vertex> epsVxs = tableJump.get(idx).get(0);
            if (epsVxs != null) {
                for (Vertex vertex : epsVxs) {
                    EpsVector(vertex, dst);
                }
            }
            eps.put(idx, dst);
        }
        this.eps = eps;
    }
    public void PrintEps() {
        System.out.println("E-переходы: ");
        for (int i = 0; i < vertexes.size(); i++) {
            List<Vertex> dst = eps.get(i);
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
            epsVxs = tableJump.get(nextIdx).get(0);
            if (epsVxs == null) return;
            for (Vertex vertex : epsVxs) {
                EpsVector(vertex, dst);
            }
        }
        return;
    }
}
