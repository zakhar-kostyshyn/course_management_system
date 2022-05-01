package com.sombra.promotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CourseManagementSystemApplication {

    public static void main(String[] args) {
        System.out.println("Started");
        SpringApplication.run(CourseManagementSystemApplication.class, args);
    }

}


@RestController
class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("It is working bich. Pipeline made a deploy!!");
    }

}
