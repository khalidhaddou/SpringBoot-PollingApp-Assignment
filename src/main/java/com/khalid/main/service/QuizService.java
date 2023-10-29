package com.khalid.main.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.khalid.main.model.Question;
import com.khalid.main.model.QuestionForm;
import com.khalid.main.repository.QuestionRepository;
import com.khalid.main.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.khalid.main.model.Result;

@Service
public class QuizService {
	
	@Autowired
	Question question;
	@Autowired
	QuestionForm questionForm;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	Result result;
	@Autowired
	ResultRepository resultRepository;
	
	public QuestionForm getQuestions() {
		List<Question> allQues = questionRepository.findAll();
		List<Question> qList = new ArrayList<Question>();
		
		Random random = new Random();
		
		for(int i=0; i<5; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		questionForm.setQuestions(qList);
		
		return questionForm;
	}
	
	public int getResult(QuestionForm qForm) {
		int correct = 0;
		
		for(Question q: qForm.getQuestions())
			if(q.getAns() == q.getChose())
				correct++;
		
		return correct;
	}
	
	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setUsername(result.getUsername());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		resultRepository.save(saveResult);
	}
	
	public List<Result> getTopScore() {
		List<Result> sList = resultRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
		
		return sList;
	}
}
