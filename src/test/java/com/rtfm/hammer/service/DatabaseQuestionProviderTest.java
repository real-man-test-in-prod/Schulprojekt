package com.rtfm.hammer.service;

import com.rtfm.hammer.model.GapField;
import com.rtfm.hammer.model.GapOption;
import com.rtfm.hammer.model.McAnswer;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.model.dialogue.AnswerOption;
import com.rtfm.hammer.model.dialogue.GapOptionItem;
import com.rtfm.hammer.model.dialogue.QuestionItem;
import com.rtfm.hammer.repository.GapFieldRepository;
import com.rtfm.hammer.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseQuestionProviderTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private GapFieldRepository gapFieldRepository;

    @InjectMocks
    private DatabaseQuestionProvider databaseQuestionProvider;

    @Test
    void shouldGetQuestionsForRoomWithMcQuestion() {
        List<String> questionRefs = List.of("1");
        Question question = createMcQuestion(1, 10);
        List<Question> questions = List.of(question);

        when(questionRepository.findByIdsWithAnswers(List.of(1))).thenReturn(questions);

        List<QuestionItem> result = databaseQuestionProvider.getQuestionsForRoom("room_test", questionRefs);

        assertEquals(1, result.size());
        QuestionItem item = result.get(0);
        assertEquals("1", item.id());
        assertEquals("MC", item.type());
        assertEquals(10, item.points());
        assertNotNull(item.options());
        assertEquals(2, item.options().size());
        assertInstanceOf(AnswerOption.class, item.options().get(0));
    }

    @Test
    void shouldGetQuestionsForRoomWithGapQuestion() {
        List<String> questionRefs = List.of("2");
        Question question = createGapQuestion(2, 15);
        List<Question> questions = List.of(question);

        GapField gapField = new GapField();
        gapField.setGapId(1);
        gapField.setTextBefore("Before");
        gapField.setTextAfter("After");

        GapOption option1 = new GapOption();
        option1.setOptionText("Answer1");
        option1.setIsCorrect(false);
        option1.setOptionOrder(1);

        GapOption option2 = new GapOption();
        option2.setOptionText("Correct");
        option2.setIsCorrect(true);
        option2.setOptionOrder(2);

        gapField.setGapOptions(List.of(option1, option2));

        when(questionRepository.findByIdsWithAnswers(List.of(2))).thenReturn(questions);
        when(gapFieldRepository.findByQuestionIdWithOptions(2)).thenReturn(List.of(gapField));

        List<QuestionItem> result = databaseQuestionProvider.getQuestionsForRoom("room_test", questionRefs);

        assertEquals(1, result.size());
        QuestionItem item = result.get(0);
        assertEquals("2", item.id());
        assertEquals("GAP", item.type());
        assertEquals(15, item.points());
        assertNotNull(item.options());
        assertEquals(1, item.options().size());
        assertInstanceOf(GapOptionItem.class, item.options().get(0));
        GapOptionItem gapItem = (GapOptionItem) item.options().get(0);
        assertEquals("1", gapItem.gapId());
        assertEquals("Before", gapItem.textBefore());
        assertEquals("After", gapItem.textAfter());
        assertEquals(List.of("Answer1", "Correct"), gapItem.choices());
        assertEquals("Correct", gapItem.correct());
    }

    @Test
    void shouldFilterOutInvalidQuestionIds() {
        List<String> questionRefs = List.of("1", "999");
        Question question = createMcQuestion(1, 10);
        List<Question> questions = List.of(question);

        when(questionRepository.findByIdsWithAnswers(List.of(1, 999))).thenReturn(questions);

        List<QuestionItem> result = databaseQuestionProvider.getQuestionsForRoom("room_test", questionRefs);

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).id());
    }

    @Test
    void shouldReturnEmptyListWhenNoQuestionsFound() {
        List<String> questionRefs = List.of("1");
        List<Question> questions = List.of();

        when(questionRepository.findByIdsWithAnswers(List.of(1))).thenReturn(questions);

        List<QuestionItem> result = databaseQuestionProvider.getQuestionsForRoom("room_test", questionRefs);

        assertTrue(result.isEmpty());
    }

    private Question createMcQuestion(int id, int points) {
        Question question = new Question();
        question.setQuestionId(id);
        question.setQuestionType("MC");
        question.setStartText("Start");
        question.setEndText("End");
        question.setPoints(points);

        McAnswer answer1 = new McAnswer();
        answer1.setAnswerId(1);
        answer1.setOptionText("Option 1");
        answer1.setIsCorrect(true);
        answer1.setOptionOrder(1);

        McAnswer answer2 = new McAnswer();
        answer2.setAnswerId(2);
        answer2.setOptionText("Option 2");
        answer2.setIsCorrect(false);
        answer2.setOptionOrder(2);

        question.setMcAnswers(List.of(answer1, answer2));
        return question;
    }

    private Question createGapQuestion(int id, int points) {
        Question question = new Question();
        question.setQuestionId(id);
        question.setQuestionType("GAP");
        question.setStartText("Start");
        question.setEndText("End");
        question.setPoints(points);
        return question;
    }
}