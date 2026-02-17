package portfolioservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static byte[] toJsonBytes(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(object);
    }
}
