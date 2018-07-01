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

import wbdv.models.EssayExamQuestion;
import wbdv.models.Exam;
import wbdv.models.FillInTheBlanksExamQuestion;
import wbdv.models.MultipleChoiceExamQuestion;
import wbdv.models.BaseExamQuestion;
import wbdv.models.Topic;
import wbdv.models.TrueFalseQuestion;
import wbdv.models.Widget;
import wbdv.repositories.ExamRepository;
import wbdv.repositories.FillInTheBlanksExamQuestionRepository;
import wbdv.repositories.TrueFalseQuestionRepository;
import wbdv.repositories.MultipleChoiceExamQuestionRepository;
import wbdv.repositories.TopicRepository;
import wbdv.repositories.EssayExamQuestionRepository;

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
	MultipleChoiceExamQuestionRepository multiRepository;
	@Autowired
	EssayExamQuestionRepository essayRepository;
	@Autowired
	FillInTheBlanksExamQuestionRepository fbRepository;
	
	
	@GetMapping("/api/multi/{questionId}")
	public MultipleChoiceExamQuestion findMultiQuestionById(@PathVariable("questionId") int questionId) {
		Optional<MultipleChoiceExamQuestion> optional = multiRepository.findById(questionId);
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
	
	@GetMapping("/api/essay/{questionId}")
	public EssayExamQuestion findEssayExamQuestionById(@PathVariable("questionId") int questionId) {
		Optional<EssayExamQuestion> optional = essayRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@GetMapping("/api/blanks/{questionId}")
	public FillInTheBlanksExamQuestion findFillInTheBlanksExamQuestionById(@PathVariable("questionId") int questionId) {
		Optional<FillInTheBlanksExamQuestion> optional = fbRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
	@GetMapping("/api/exam/{examId}/question")
	public List<BaseExamQuestion> findAllQuestionsForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<BaseExamQuestion> questions = exam.getQuestions();
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
	
	
	@PutMapping("/api/truefalse/{questionId}")
	public ResponseEntity<?> updateTrueFalseQuestion(
			@PathVariable("questionId") int qId,
			@RequestBody TrueFalseQuestion newTFQ){
		Optional <TrueFalseQuestion> data = trueFalseRepository.findById(qId);
		if(data.isPresent()) {
			TrueFalseQuestion TFQ = data.get();
			TFQ.setTitle(newTFQ.getTitle());
			TFQ.setPoints(newTFQ.getPoints());
			TFQ.setDescription(newTFQ.getDescription());
			TFQ.setTrue(newTFQ.isTrue());
			trueFalseRepository.save(TFQ);
			return new ResponseEntity<TrueFalseQuestion>(HttpStatus.OK);
		}
		return new ResponseEntity<TrueFalseQuestion>(HttpStatus.BAD_REQUEST);
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
	
	@DeleteMapping("/api/multi/{questionId}")
	public void deleteMultipleChoiceQuestion(@PathVariable("questionId") int questionId) {
		multiRepository.deleteById(questionId);
	}
	
	@DeleteMapping("/api/essay/{questionId}")
	public void deleteEssayExamQuestion(@PathVariable("questionId") int questionId) {
		essayRepository.deleteById(questionId);
	}
	
	@DeleteMapping("/api/truefalse/{questionId}")
	public void deleteTrueFalseQuestion(@PathVariable("questionId") int questionId) {
		trueFalseRepository.deleteById(questionId);
	}
	
	@DeleteMapping("/api/exam/{eId}")
	public void deleteExam(@PathVariable("eId") int eId) {
		examRepository.deleteById(eId);
	}
}
