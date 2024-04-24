package json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <h2>Парсер JSON обьектов</h2>
 */
public class JSONparser {
    public static <T> T getObject(File file, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, clazz);
    }
    // Десериализовать JSON в список
    public static <T> ArrayList<T> getList(File file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory t = TypeFactory.defaultInstance();
        return mapper.readValue(file, t.constructCollectionType(ArrayList.class, clazz));
    }
    // В РАЗРАБОТКЕ
    public static <T> Map<String, ArrayList<T>> getMap(File file)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<>() {});
    }
}