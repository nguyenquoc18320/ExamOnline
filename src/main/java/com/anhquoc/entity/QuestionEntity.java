package com.anhquoc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="question")
public class QuestionEntity extends BaseEntity{
	
	@Column(name="question_number")
	private int questionnumber;
	
	@Column(name="correct_answer ")
	private String correctanswer ;
	
	@Column(name="content")
	private String content;
	
	@Column(name="a")
	private String optionA;
	
	@Column(name="b")
	private String optionB;
	
	@Column(name="c")
	private String optionC;
	
	@Column(name="d")
	private String optionD;
	
	@Column(name="e")
	private String optionE;
	
	@Column(name="f")
	private String optionF;
	
	@Column(name="g")
	private String optionG;
	
	@Column(name="h")
	private String optionH;
	
	@Column(name="i")
	private String optionI;
	
	@Column(name="j")
	private String optionJ;
	
	@ManyToOne
    @JoinColumn(name="testid", nullable=false)
    private TestEntity test;
	
	@ManyToMany(mappedBy = "questions")
    private List<UserEntity> users = new ArrayList<>();
	
	public QuestionEntity() {
		
	}
	public QuestionEntity(int questionnumber,String correctanswer, String content, String optionA, String optionB, String optionC, String optionD, String optionE, String optionF, String optionG, String optionH, String optionI, String optionJ, TestEntity test) {
		super();
		this.questionnumber = questionnumber;
		this.correctanswer = correctanswer;
		this.content = content;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionD = optionD;
		this.optionE = optionE;
		this.optionF = optionF;
		this.optionG = optionG;
		this.optionH = optionH;
		this.optionI = optionI;
		this.optionJ = optionJ;
		this.test = test;
	}
	
	public int getQuestionnumber() {
		return questionnumber;
	}
	public void setQuestionnumber(int questionnumber) {
		this.questionnumber = questionnumber;
	}
	public String getCorrectanswer() {
		return correctanswer;
	}
	public void setCorrectanswer(String correctanswer) {
		this.correctanswer = correctanswer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getOptionF() {
		return optionF;
	}
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}
	public String getOptionG() {
		return optionG;
	}
	public void setOptionG(String optionG) {
		this.optionG = optionG;
	}
	public String getOptionH() {
		return optionH;
	}
	public void setOptionH(String optionH) {
		this.optionH = optionH;
	}
	public String getOptionI() {
		return optionI;
	}
	public void setOptionI(String optionI) {
		this.optionI = optionI;
	}
	public String getOptionJ() {
		return optionJ;
	}
	public void setOptionJ(String optionJ) {
		this.optionJ = optionJ;
	}
	public TestEntity getTest() {
		return test;
	}
	public void setTest(TestEntity test) {
		this.test = test;
	}
	public List<UserEntity> getUsers() {
		return users;
	}
	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
