package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.PutMarkRequest;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.factory.MarkFactory;
import com.sombra.promotion.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final MarkFactory markFactory;

    public MarkResponse assignMark(PutMarkRequest request) {
        UUID insertMarkId = markRepository.insertMark(request);
        return markFactory.build(insertMarkId);
    }

    public List<MarkResponse> getMarksForStudents(String student, List<UUID> lessonsIds) {
        return markFactory.build(markRepository.selectMarksByStudentAndLessons(student, lessonsIds));
    }

}
