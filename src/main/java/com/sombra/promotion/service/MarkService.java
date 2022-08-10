package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.PutMarkRequest;
import com.sombra.promotion.dto.details.MarkDetails;
import com.sombra.promotion.factory.MarkDetailsFactory;
import com.sombra.promotion.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final  MarkDetailsFactory markDetailsFactory;

    public MarkDetails assignMark(PutMarkRequest request) {
        UUID insertMarkId = markRepository.insertMark(request);
        return markDetailsFactory.build(insertMarkId);
    }

    public List<MarkDetails> getMarksForStudents(String student, List<UUID> lessonsIds) {
        return markDetailsFactory.build(markRepository.selectMarksByStudentAndLessons(student, lessonsIds));
    }

}
