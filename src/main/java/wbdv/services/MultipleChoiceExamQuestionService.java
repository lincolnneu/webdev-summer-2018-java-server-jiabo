package wbdv.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Exam;
import wbdv.models.MultipleChoiceExamQuestion;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.MultipleChoiceExamQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class MultipleChoiceExamQuestionService {
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	MultipleChoiceExamQuestionRepository multiRepository;
	
	@GetMapping("/api/multi/{questionId}")
	public MultipleChoiceExamQuestion findMultiQuestionById(@PathVariable("questionId") int questionId) {
		Optional<MultipleChoiceExamQuestion> optional = multiRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eId}/choice")
	public MultipleChoiceExamQuestion createMultipleChoiceQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody MultipleChoiceExamQuestion newMCQ) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			Exam exam = data.get();
			newMCQ.setExam(exam);
			multiRepository.save(newMCQ);
			return newMCQ;
		}
		return null;
	}
	
	@PutMapping("/api/multi/{questionId}")
	public ResponseEntity<?> updateMultipleChoiceQuestion(
			@PathVariable("questionId") int qId,
			@RequestBody MultipleChoiceExamQuestion newMCQ){
		Optional <MultipleChoiceExamQuestion> data = multiRepository.findById(qId);
		if(data.isPresent()) {
			MultipleChoiceExamQuestion MCQ = data.get();
			MCQ.setTitle(newMCQ.getTitle());
			MCQ.setPoints(newMCQ.getPoints());
			MCQ.setDescription(newMCQ.getDescription());
			MCQ.setOptions(newMCQ.getOptions());
			MCQ.setCorrectOption(newMCQ.getCorrectOption());
			multiRepository.save(MCQ);
			return new ResponseEntity<MultipleChoiceExamQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<MultipleChoiceExamQuestion>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/multi/{questionId}")
	public void deleteMultipleChoiceQuestion(@PathVariable("questionId") int questionId) {
		multiRepository.deleteById(questionId);
	}
}
