package commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.event.MouseAdapter;
import java.util.Map;

public class UncheckedMapper {

    private final ObjectMapper mapper;

    public UncheckedMapper(){
        this(new ObjectMapper());
    }

    public UncheckedMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public JsonNode readTree(String string) {
        try {
            return mapper.readTree(string);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid json schema");
        }
    }

    public ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }
}
