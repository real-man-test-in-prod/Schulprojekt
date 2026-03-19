package com.rtfm.hammer.service;

import com.rtfm.hammer.model.GapOption;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.model.dialogue.AnswerOption;
import com.rtfm.hammer.model.dialogue.GapOptionItem;
import com.rtfm.hammer.model.dialogue.QuestionItem;
import com.rtfm.hammer.repository.GapFieldRepository;
import com.rtfm.hammer.repository.QuestionRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Primary
public class DatabaseQuestionProvider implements QuestionProvider {

    private final QuestionRepository questionRepository;
    private final GapFieldRepository gapFieldRepository;

    public DatabaseQuestionProvider(QuestionRepository questionRepository,
                                    GapFieldRepository gapFieldRepository) {
        this.questionRepository = questionRepository;
        this.gapFieldRepository = gapFieldRepository;
    }

    @Override
    public List<QuestionItem> getQuestionsForRoom(String roomCode, List<String> questionRefs) {
        List<Integer> ids = questionRefs.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Map<Integer, Question> byId = questionRepository.findByIdsWithAnswers(ids).stream()
                .collect(Collectors.toMap(Question::getQuestionId, Function.identity()));

        return ids.stream()
                .filter(byId::containsKey)
                .map(id -> toQuestionItem(byId.get(id)))
                .collect(Collectors.toList());
    }

    private QuestionItem toQuestionItem(Question q) {
        List<Object> options;

        if ("GAP".equals(q.getQuestionType())) {
            options = gapFieldRepository.findByQuestionIdWithOptions(q.getQuestionId())
                    .stream()
                    .map(gf -> {
                        List<String> choices = gf.getGapOptions().stream()
                                .sorted(Comparator.comparing(GapOption::getOptionOrder))
                                .map(GapOption::getOptionText)
                                .collect(Collectors.toList());

                        String correct = gf.getGapOptions().stream()
                                .filter(GapOption::getIsCorrect)
                                .map(GapOption::getOptionText)
                                .findFirst()
                                .orElse("");

                        return (Object) new GapOptionItem(
                                String.valueOf(gf.getGapId()),
                                gf.getTextBefore(),
                                gf.getTextAfter(),
                                choices,
                                correct
                        );
                    })
                    .collect(Collectors.toList());
        } else {
            options = q.getMcAnswers() == null ? List.of() :
                    q.getMcAnswers().stream()
                            .sorted(Comparator.comparing(a -> a.getOptionOrder()))
                            .map(a -> (Object) new AnswerOption(a.getOptionText(), a.getIsCorrect()))
                            .collect(Collectors.toList());
        }

        return new QuestionItem(
                String.valueOf(q.getQuestionId()),
                q.getQuestionType(),
                null,
                q.getStartText(),
                q.getEndText(),
                q.getPoints(),
                options
        );
    }
}
