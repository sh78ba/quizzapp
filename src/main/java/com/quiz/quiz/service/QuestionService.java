package com.quiz.quiz.service;

import com.quiz.quiz.model.Question;
import com.quiz.quiz.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
       try{
           return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public  ResponseEntity<List<Question>>getQuestionsByCategory(String category){
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question){
       try {
           questionDao.save(question);
           return new ResponseEntity<>("success", HttpStatus.CREATED);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
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
