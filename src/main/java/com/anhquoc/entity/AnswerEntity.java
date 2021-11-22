package com.anhquoc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class AnswerEntity{
	
	@EmbeddedId
    private UserQuestionEmbeddable id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private QuestionEntity question;
	
	
	@Column(name="attemp")
	private int attemp;
	
	@Column(name="selection")
	private String selection;
	
//	@OneToOne
//	@JoinColumn(name="questionid", referencedColumnName = "id")
//	private QuestionEntity question;
	
	public AnswerEntity() {
		
	}
	public AnswerEntity(UserEntity user, QuestionEntity question, int attemp, String selection) {
		this.user = user;
		this.question = question;
		this.attemp = attemp;
		this.selection = selection;
		
	}
	public AnswerEntity(int attemp, String selection) {
		this.attemp = attemp;
		this.selection = selection;
		
	}
	public UserQuestionEmbeddable getId() {
		return id;
	}
	public void setId(UserQuestionEmbeddable id) {
		this.id = id;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public QuestionEntity getQuestion() {
		return question;
	}
	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}
	public int getAttemp() {
		return attemp;
	}
	public void setAttemp(int attemp) {
		this.attemp = attemp;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	

}
