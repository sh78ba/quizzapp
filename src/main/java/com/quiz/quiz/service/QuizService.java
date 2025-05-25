//package com.quiz.quiz.service;
//
//import com.quiz.quiz.dao.QuestionDao;
//import com.quiz.quiz.dao.QuizDao;
//import com.quiz.quiz.model.Question;
//import com.quiz.quiz.model.Quiz;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class QuizService {
//    @Autowired
//    QuizDao quizDao;
//
//    @Autowired
//    QuestionDao questionDao;
//
//
//    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
//        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
//        Quiz quiz=new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questions);
//        quizDao.save(quiz);
//        return new ResponseEntity<>("Created", HttpStatus.CREATED);
//    }
//}

package com.quiz.quiz.service;

import com.quiz.quiz.dao.QuestionDao;
import com.quiz.quiz.dao.QuizDao;
import com.quiz.quiz.model.Question;
import com.quiz.quiz.model.QuestionWrapper;
import com.quiz.quiz.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // Use PageRequest for dynamic limit
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, PageRequest.of(0, numQ));

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>>getQuizQuestions(Integer id) {
       Optional<Quiz> quiz= quizDao.findById(id);
       List<Question> questionFromDb=quiz.get().getQuestions();
        List<QuestionWrapper>questionForUsers=new ArrayList<>();
        for(Question q:questionFromDb){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUsers.add(qw);
        }

        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }
}
