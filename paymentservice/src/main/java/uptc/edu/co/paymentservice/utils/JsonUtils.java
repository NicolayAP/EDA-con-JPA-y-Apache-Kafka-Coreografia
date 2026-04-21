package uptc.edu.co.paymentservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error serializando a JSON", e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando desde JSON", e);
        }
    }
}