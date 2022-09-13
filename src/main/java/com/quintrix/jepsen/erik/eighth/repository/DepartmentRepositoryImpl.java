package com.quintrix.jepsen.erik.eighth.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import com.quintrix.jepsen.erik.eighth.model.Department;

public class DepartmentRepositoryImpl implements IDepartmentRepository {
  private EntityManager entityManager;

  public DepartmentRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Department departmentCreate(Department department) {
    entityManager.getTransaction().begin();
    entityManager.persist(department);
    entityManager.getTransaction().commit();
    return entityManager.createQuery("from Department order by deptId desc", Department.class)
        .setMaxResults(1).getSingleResult();
  }

  @Override
  public Department departmentRead(int deptId) {
    entityManager.getTransaction().begin();
    Department department = entityManager
        .createQuery("from department where departments.dept_id = :id", Department.class)
        .setParameter("id", deptId).getSingleResult();
    entityManager.getTransaction().commit();
    return department;
  }

  @Override
  public Department departmentUpdate(int deptId, String name) {
    int updatedEntities;
    Department department = null;
    entityManager.getTransaction().begin();
    updatedEntities =
        entityManager.createQuery("update Department set name = :newName where deptId = :deptId")
            .setParameter("newName", name).setParameter("deptId", deptId).executeUpdate();
    if (updatedEntities == 1) {
      entityManager.getTransaction().commit();
      department =
          entityManager.createQuery("from Department d where d.deptId = :id", Department.class)
              .setParameter("id", deptId).getSingleResult();
    } else
      entityManager.getTransaction().rollback();
    return department;
  }

  @Override
  public void departmentDelete(int deptId) {
    Department doomedDept =
        entityManager.createQuery("from Department where deptId = :id", Department.class)
            .setParameter("id", deptId).getSingleResult();
    entityManager.getTransaction().begin();
    entityManager.remove(doomedDept);
    entityManager.getTransaction().commit();
  }

  @Override
  public Department departmentFind(String name) {
    return entityManager.createQuery("from Department d where d.name like :name", Department.class)
        .setParameter("name", name).getSingleResult();
  }

  @Override
  public boolean isDepartmentClose() {
    return !entityManager.isOpen();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Long departmentCount() {
    javax.persistence.Query countQuery =
        entityManager.createQuery("select count(*) from Department");
    return ((Query<Long>) countQuery).uniqueResult();
  }

  @Override
  public Department[] departmentGetAll() {
    List<Department> result =
        entityManager.createQuery("from Department", Department.class).getResultList();
    return result.toArray(new Department[result.size()]);
  }
}
