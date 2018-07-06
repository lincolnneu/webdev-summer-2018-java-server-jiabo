package wbdv.services;

import java.util.ArrayList;
import java.util.List;
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
import wbdv.models.BaseExamQuestion;
import wbdv.models.Topic;
import wbdv.models.Widget;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*")
public class ExamService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	TopicRepository tRepository;

	@GetMapping("/api/exam/{examId}/question")
	public List<BaseExamQuestion> findAllQuestionsForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<BaseExamQuestion> questions = exam.getQuestions();
			return questions;
		}
		return null;
	}
	
	@GetMapping("/api/exam")
	public Iterable <Exam> findAllExams(){
		return examRepository.findAll();
	}
	
	@GetMapping("/api/exam/{eId}")
	public Exam findExamById(@PathVariable("eId") int eId) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/topic/{topicId}/exam")
	public List<Exam> findAllExamsForTopic(
			@PathVariable("topicId") int topicId){
		Optional<Topic> data = tRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			List<Exam> exams = new ArrayList<Exam>();
			List<Widget> widgets = topic.getWidgets();
			for(Widget widget: widgets) {
				if(widget.getWidgetType().equals("Exam")) {
					exams.add((Exam) widget);
				}
			}
			return exams;
		}
		return null;
	}
	
	@PostMapping("/api/topic/{topicId}/exam")
	public Exam createExam(
			@PathVariable("topicId") int topicId,
			@RequestBody Exam newExam) {
		Optional <Topic> data = tRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			newExam.setTopic(topic);
			examRepository.save(newExam);
			return newExam;
		}
		return null;
	}
	
	@PutMapping("/api/exam/{examId}")
	public ResponseEntity<?> updateExam(
			@PathVariable("examId") int examId,
			@RequestBody Exam newExam) {
		Optional <Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			exam.setTitle(newExam.getTitle());
			exam.setPoints(newExam.getPoints());
			exam.setDescription(newExam.getDescription());
			examRepository.save(exam);
			return new ResponseEntity<Exam>(HttpStatus.OK);
		}
		return new ResponseEntity<Exam>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/exam/{eId}")
	public void deleteExam(@PathVariable("eId") int eId) {
		examRepository.deleteById(eId);
	}
}
