import java.util.List;
import java.util.Map;

public class Utils {
    public static void PrintTable(int n_columns, String[] labels, String[][] params, int[] fieldSizes) {
        if (labels.length != n_columns || params[0].length != n_columns || fieldSizes.length != n_columns) {
            System.out.println("Количество столбцов не совпадает с количеством параметров");
            return;
        }
        for (int i = 0; i < n_columns; i++) {
            String format = "%-" + fieldSizes[i % n_columns] + "s";
            System.out.printf(format, labels[i]);
            System.out.print(" | ");
        }
        System.out.println();
        for (int i = 0; i < params.length; i++) {
            for (int j = 0; j < n_columns; j++) {
                String format = "%-" + fieldSizes[j % n_columns] + "s";
                System.out.printf(format, params[i][j]);
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
    public static String[][] MapToMatrix(Map<Integer, List<Vertex>> map) {
        int n_rows = map.size();
        String[][] params = new String[n_rows][];
        for (int i = 0; i < n_rows; i++) {
            List<Vertex> row = map.get(i);
            params[i] = new String[row.size() + 1];
            params[i][0] = "q" + String.valueOf(i);
            for (int j = 1; j < row.size() + 1; j++) {
                params[i][j] = (row.get(j - 1) != null) ? "q" + String.valueOf(row.get(j - 1).getIdx()) : "-";
            }
        }
        return params;
    }
}
