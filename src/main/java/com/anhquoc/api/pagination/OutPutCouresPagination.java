package com.anhquoc.api.pagination;

import java.util.ArrayList;
import java.util.List;

import com.anhquoc.entity.CourseEntity;

public class OutPutCouresPagination {
	private int page;
	private int totalPage;
	private List<CourseEntity> courseList;
	
	public OutPutCouresPagination() {
		courseList = new ArrayList<>();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<CourseEntity> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseEntity> courseList) {
		this.courseList = courseList;
	}

	

}
