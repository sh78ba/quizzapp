package com.quiz.quiz.service;

import com.quiz.quiz.Question;
import com.quiz.quiz.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public  List<Question>getQuestionsByCategory(String category){
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question){
        questionDao.save(question);
        return "success";
    }

    public String deleteQuestion(Integer id) {
        if (questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return "Question deleted successfully";
        } else {
            return "Question with ID " + id + " not found";
        }
    }

    public Question updateQuestion(Integer id, Question updatedQuestion) {
        Question existing = questionDao.findById(id).orElse(null);
        if (existing != null) {
            existing.setQuestionTitle(updatedQuestion.getQuestionTitle());
            existing.setOption1(updatedQuestion.getOption1());
            existing.setOption2(updatedQuestion.getOption2());
            existing.setOption3(updatedQuestion.getOption3());
            existing.setOption4(updatedQuestion.getOption4());
            existing.setRightAnswer(updatedQuestion.getRightAnswer());
            existing.setDifficultyLevel(updatedQuestion.getDifficultylevel());
            existing.setCategory(updatedQuestion.getCategory());
            return questionDao.save(existing);
        }
        return null;
    }


}
