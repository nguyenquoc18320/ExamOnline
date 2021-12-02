package com.anhquoc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserQuestionEmbeddable implements Serializable{
	@Column(name = "user_id")
    Long userId;

    @Column(name = "question_id")
    Long questionId;
    
    @Column(name="date")
    private Date date;

    
    
	public UserQuestionEmbeddable() {
		super();
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
    // standard constructors, getters, and setters
    // hashcode and equals implementation
    
}
