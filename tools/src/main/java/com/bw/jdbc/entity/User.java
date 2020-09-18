package com.bw.jdbc.entity;

import java.io.Serializable;

import com.bw.jdbc.annotation.Column;
import com.bw.jdbc.annotation.Table;

@Table(table = "user")
public class User implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Column(name="id",type=String.class)
    private String id;

    @Column(name="name",type=String.class)
    private String name;

    @Column(name="age",type=Integer.class)
    private Integer age;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

   

}
