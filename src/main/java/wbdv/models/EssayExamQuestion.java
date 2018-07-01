package wbdv.models;

import javax.persistence.Entity;

@Entity
public class EssayExamQuestion extends BaseExamQuestion{
	private String essay;

	public String getEssay() {
		return essay;
	}

	public void setEssay(String essay) {
		this.essay = essay;
	}
	
}
