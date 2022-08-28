package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.FinishCourseResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.factory.generic.CourseMarkFactory;
import com.sombra.promotion.tables.pojos.CourseMark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FinishCourseFactory {

    private final CourseMarkFactory courseMarkFactory;

    public FinishCourseResponse build(CourseMark courseMark, List<MarkResponse> markResponses) {
        return FinishCourseResponse.builder()
                .courseMark(courseMarkFactory.build(courseMark))
                .marks(markResponses)
                .build();
    }

}
