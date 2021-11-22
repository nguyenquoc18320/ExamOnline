package com.anhquoc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="test")
public class TestEntity extends BaseEntity{
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "attempt_number")
	private int attemptnumber;
	
	@ManyToOne
    @JoinColumn(name="courseid", nullable=false)
    private CourseEntity course;
	
	@OneToMany(mappedBy="test", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<QuestionEntity> tests = new ArrayList<>();
	
	
	public TestEntity() {
    	
    }
	
	public TestEntity(String name, String description, int duration, int attemptnumber, CourseEntity course) {
		super();
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.attemptnumber = attemptnumber;
		this.course = course;
	}	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAttemptnumber() {
		return attemptnumber;
	}

	public void setAttemptnumber(int attemptnumber) {
		this.attemptnumber = attemptnumber;
	}

	public CourseEntity getCourse() {
		return course;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	
	
	
	
}