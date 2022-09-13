package com.quintrix.jepsen.erik.eighth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
  private String name;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dept_id")
  private Integer deptId;

  /**
   * This exists for de/serialization purposes. It is not intended to be used.
   */
  private Department() {
    super();
  }

  public Department(String name) {
    this();
    this.name = name;
  }

  public Department(String name, Integer deptId) {
    this(name);
    this.deptId = deptId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String toString() {
    return String.format("%d.  %s", deptId, name);
  }
}
