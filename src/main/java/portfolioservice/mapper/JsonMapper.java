package portfolioservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T read (String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Invalid JSON body", exception);
        }
    }

    public String write (Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Cannot convert object to JSON", exception);
        }
    }
}
