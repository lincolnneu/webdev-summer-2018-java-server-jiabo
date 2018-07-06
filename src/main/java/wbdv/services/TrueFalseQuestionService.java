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
import wbdv.models.TrueOrFalseExamQuestion;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.TrueOrFalseExamQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class TrueFalseQuestionService {
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	TrueOrFalseExamQuestionRepository trueFalseRepository;
	
	@GetMapping("/api/truefalse/{questionId}")
	public TrueOrFalseExamQuestion findTrueFalseQuestionById(@PathVariable("questionId") int questionId) {
		Optional<TrueOrFalseExamQuestion> optional = trueFalseRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eId}/truefalse")
	public TrueOrFalseExamQuestion createTrueFalseQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody TrueOrFalseExamQuestion newTFQ) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			Exam exam = data.get();
			newTFQ.setExam(exam);
			trueFalseRepository.save(newTFQ);
			return newTFQ;
		}
		return null;
	}
	
	@PutMapping("/api/truefalse/{questionId}")
	public ResponseEntity<?> updateTrueFalseQuestion(
			@PathVariable("questionId") int qId,
			@RequestBody TrueOrFalseExamQuestion newTFQ){
		Optional <TrueOrFalseExamQuestion> data = trueFalseRepository.findById(qId);
		if(data.isPresent()) {
			TrueOrFalseExamQuestion TFQ = data.get();
			TFQ.setTitle(newTFQ.getTitle());
			TFQ.setPoints(newTFQ.getPoints());
			TFQ.setDescription(newTFQ.getDescription());
			TFQ.setTrue(newTFQ.isTrue());
			trueFalseRepository.save(TFQ);
			return new ResponseEntity<TrueOrFalseExamQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<TrueOrFalseExamQuestion>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/truefalse/{questionId}")
	public void deleteTrueFalseQuestion(@PathVariable("questionId") int questionId) {
		trueFalseRepository.deleteById(questionId);
	}
}
