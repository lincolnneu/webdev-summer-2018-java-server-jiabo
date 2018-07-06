package wbdv.models;

import javax.persistence.Entity;

@Entity
// We are using join strategy for question. So there is a
// table for this type of question.
public class TrueOrFalseExamQuestion extends BaseExamQuestion{
	private boolean isTrue;

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}


	
}
