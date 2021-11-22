package com.anhquoc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class UserQuestionEmbeddable implements Serializable{
	@Column(name = "user_id")
    Long userId;

    @Column(name = "question_id")
    Long questionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
    
    // standard constructors, getters, and setters
    // hashcode and equals implementation
    
}
