package helpers.testConfigs;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.testHelpers.JsonBuilderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import( { JacksonTestConfiguration.class } )
public class JsonTestConfiguration {

    @Bean
    public JsonBuilderUtils jsonBuilder(@Autowired ObjectMapper mapper) {
        return new JsonBuilderUtils(mapper);
    }

}
