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
import wbdv.models.MultipleChoiceQuestion;
import wbdv.models.Question;
import wbdv.models.Topic;
import wbdv.models.TrueFalseQuestion;
import wbdv.models.Widget;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.TrueFalseQuestionRepository;
import wbdv.repositories.MultipleChoiceQuestionRepository;
import wbdv.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*")
public class ExamService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	TopicRepository tRepository;
	@Autowired
	TrueFalseQuestionRepository trueFalseRepository;
	@Autowired
	MultipleChoiceQuestionRepository multiRepository;
	
	@GetMapping("/api/multi/{questionId}")
	public MultipleChoiceQuestion findMultiQuestionById(@PathVariable("questionId") int questionId) {
		Optional<MultipleChoiceQuestion> optional = multiRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@GetMapping("/api/truefalse/{questionId}")
	public TrueFalseQuestion findTrueFalseQuestionById(@PathVariable("questionId") int questionId) {
		Optional<TrueFalseQuestion> optional = trueFalseRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@GetMapping("/api/exam/{examId}/question")
	public List<Question> findAllQuestionsForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<Question> questions = exam.getQuestions();
			int count = questions.size();
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
	
	@PostMapping("/api/exam/{eId}/truefalse")
	public TrueFalseQuestion createTrueFalseQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody TrueFalseQuestion newTFQ) {
		Optional <Exam> data = examRepository.findById(eId);
		if(data.isPresent()) {
			Exam exam = data.get();
			newTFQ.setExam(exam);
			trueFalseRepository.save(newTFQ);
			return newTFQ;
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eId}/choice")
	public MultipleChoiceQuestion createMultipleChoiceQuestionForExam(
			@PathVariable("eId") int eId,
			@RequestBody MultipleChoiceQuestion newMCQ) {
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
			@RequestBody MultipleChoiceQuestion newMCQ){
		Optional <MultipleChoiceQuestion> data = multiRepository.findById(qId);
		if(data.isPresent()) {
			MultipleChoiceQuestion MCQ = data.get();
			MCQ.setTitle(newMCQ.getTitle());
			MCQ.setPoints(newMCQ.getPoints());
			MCQ.setDescription(newMCQ.getDescription());
			MCQ.setOptions(newMCQ.getOptions());
			MCQ.setCorrectOption(newMCQ.getCorrectOption());
			multiRepository.save(MCQ);
			return new ResponseEntity<MultipleChoiceQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<MultipleChoiceQuestion>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/exam/{eId}")
	public void deleteExam(@PathVariable("eId") int eId) {
		examRepository.deleteById(eId);
	}
}
