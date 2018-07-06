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
import wbdv.models.FillInTheBlanksExamQuestion;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.FillInTheBlanksExamQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class FillInTheBlanksExamQuestionRepositoryService {
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	FillInTheBlanksExamQuestionRepository fbRepository;

	@GetMapping("/api/blanks/{questionId}")
	public FillInTheBlanksExamQuestion findFillInTheBlanksExamQuestionById(@PathVariable("questionId") int questionId) {
		Optional<FillInTheBlanksExamQuestion> optional = fbRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eId}/blanks")
	public FillInTheBlanksExamQuestion createFillInTheBlanksExamQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody FillInTheBlanksExamQuestion newFB) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			Exam exam = data.get();
			newFB.setExam(exam);
			fbRepository.save(newFB);
			return newFB;
		}
		return null;
	}
	
	@PutMapping("/api/blanks/{questionId}")
	public ResponseEntity<?> updateFillInTheBlanksExamQuestion(
			@PathVariable("questionId") int qId,
			@RequestBody FillInTheBlanksExamQuestion newFBQ){
		Optional <FillInTheBlanksExamQuestion> data = fbRepository.findById(qId);
		if(data.isPresent()) {
			FillInTheBlanksExamQuestion FBQ = data.get();
			FBQ.setTitle(newFBQ.getTitle());
			FBQ.setPoints(newFBQ.getPoints());
			FBQ.setDescription(newFBQ.getDescription());
			FBQ.setVariables(newFBQ.getVariables());
			FBQ.setContext(newFBQ.getContext());
			fbRepository.save(FBQ);
			return new ResponseEntity<FillInTheBlanksExamQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<FillInTheBlanksExamQuestion>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/blanks/{questionId}")
	public void deleteFillInTheBlanksExamQuestion(@PathVariable("questionId") int questionId) {
		fbRepository.deleteById(questionId);
	}
	
}
