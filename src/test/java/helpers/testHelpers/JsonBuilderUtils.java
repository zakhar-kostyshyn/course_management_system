package helpers.testHelpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBuilderUtils {

    private final ObjectMapper mapper;

    public JsonBuilderUtils(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
