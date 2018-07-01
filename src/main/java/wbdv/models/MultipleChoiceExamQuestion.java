package wbdv.models;

import javax.persistence.Entity;

@Entity
// As we use join strategy for Question, there will be a table for this
// type too.
public class MultipleChoiceExamQuestion extends BaseExamQuestion{
	private String options;
	private int correctOption;
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public int getCorrectOption() {
		return correctOption;
	}
	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}
	
	
}
