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

import wbdv.models.EssayExamQuestion;
import wbdv.models.Exam;
import wbdv.repositories.EssayExamQuestionRepository;
import wbdv.repositories.ExamRepository;

@RestController
@CrossOrigin(origins="*")
public class EssayExamQuestionService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	EssayExamQuestionRepository essayRepository;
	
	@GetMapping("/api/essay/{questionId}")
	public EssayExamQuestion findEssayExamQuestionById(@PathVariable("questionId") int questionId) {
		Optional<EssayExamQuestion> optional = essayRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eId}/essay")
	public EssayExamQuestion createEssayExamQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody EssayExamQuestion newEQ) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			Exam exam = data.get();
			newEQ.setExam(exam);
			essayRepository.save(newEQ);
			return newEQ;
		}
		return null;
	}
	
	@PutMapping("/api/essay/{questionId}")
	public ResponseEntity<?> updateEssayExamQuestion(
			@PathVariable("questionId") int qId,
			@RequestBody EssayExamQuestion newEQ){
		Optional <EssayExamQuestion> data = essayRepository.findById(qId);
		if(data.isPresent()) {
			EssayExamQuestion EQ = data.get();
			EQ.setTitle(newEQ.getTitle());
			EQ.setPoints(newEQ.getPoints());
			EQ.setDescription(newEQ.getDescription());
			EQ.setEssay(newEQ.getEssay());
			essayRepository.save(EQ);
			return new ResponseEntity<EssayExamQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<EssayExamQuestion>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/essay/{questionId}")
	public void deleteEssayExamQuestion(@PathVariable("questionId") int questionId) {
		essayRepository.deleteById(questionId);
	}
	
}
