package com.quintrix.jepsen.erik.eighth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {
  @Column(name = "f_name")
  private String fName;
  @Column(name = "l_name")
  private String lName;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "person_id")
  private Integer personId;
  @ManyToOne
  @JoinColumn(name = "dept_id", foreignKey = @ForeignKey(name = "FK_departments"))
  private Department dept;

  public Person() {
    super();
  }

  public Person(String fName, String lName) {
    this.fName = fName;
    this.lName = lName;
  }

  public Person(String fName, String lName, Department dept) {
    this(fName, lName);
    this.dept = dept;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public Department getDeptId() {
    return dept;
  }

  public void setDeptId(Department dept) {
    this.dept = dept;
  }

  public String toString() {
    return String.format("%d. %s %s (Department: %s)", personId, fName, lName, dept.getName());
  }
}
