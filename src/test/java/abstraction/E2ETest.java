package abstraction;

import helpers.testConfigs.TestHelpersConfiguration;
import helpers.testHelpers.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestHelpersConfiguration.class})
abstract public class E2ETest {

    @Autowired protected JsonBuilderUtils jsonBuilderUtils;
    @Autowired protected JwtTokenUtil jwtTokenUtil;
    @Autowired protected InsertUtils insert;
    @Autowired protected SelectUtils select;
    @Autowired protected DeleteUtils delete;
    @LocalServerPort protected int port;

    protected String testURL(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void setUp() {
        deleteWholeDB();
    }

    private void deleteWholeDB() {

        // tables delete should be in the right order

        delete.userRole();
        delete.instructorCourse();
        delete.studentCourse();

        delete.feedback();
        delete.role();
        delete.courseMark();
        delete.homework();
        delete.mark();

        delete.lesson();
        delete.course();
        delete.user();

    }

}
