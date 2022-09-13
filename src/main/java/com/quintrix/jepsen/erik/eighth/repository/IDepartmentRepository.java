package com.quintrix.jepsen.erik.eighth.repository;

import com.quintrix.jepsen.erik.eighth.model.Department;

public interface IDepartmentRepository {
  public Department departmentCreate(Department department);

  public Department departmentRead(int deptId);

  public Department departmentUpdate(int deptId, String name);

  public void departmentDelete(int deptId);

  public Department departmentFind(String name);

  public boolean isDepartmentClose();

  public Long departmentCount();

  public Department[] departmentGetAll();
}
