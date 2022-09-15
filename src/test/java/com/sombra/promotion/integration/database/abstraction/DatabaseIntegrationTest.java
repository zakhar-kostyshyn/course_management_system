package com.sombra.promotion.integration.database.abstraction;

import configs.DatabaseTestConfiguration;
import helpers.testHelpers.DeleteUtils;
import helpers.testHelpers.InsertUtils;
import helpers.testHelpers.SelectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({DatabaseTestConfiguration.class})
public abstract class DatabaseIntegrationTest {

    @Autowired protected SelectUtils select;
    @Autowired protected InsertUtils insert;
    @Autowired protected DeleteUtils delete;

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
