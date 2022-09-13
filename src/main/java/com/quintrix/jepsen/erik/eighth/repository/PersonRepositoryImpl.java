package com.quintrix.jepsen.erik.eighth.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import com.quintrix.jepsen.erik.eighth.model.Person;

public class PersonRepositoryImpl implements IPersonRepository {
  private EntityManager entityManager;

  public PersonRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Person personCreate(Person person) {
    if (isClosed())
      return null;
    entityManager.getTransaction().begin();
    entityManager.persist(person);
    entityManager.getTransaction().commit();
    return entityManager.createQuery("from Person order by personId desc", Person.class)
        .setMaxResults(1).getSingleResult();
  }

  @Override
  public Person personRead(int personId) {
    if (isClosed())
      return null;
    return entityManager.createQuery("from Person where personId = :id", Person.class)
        .setParameter("id", personId).getSingleResult();
  }

  @Override
  public Person personUpdate(Person person) {
    if (isClosed())
      return null;
    entityManager.getTransaction().begin();
    int result = entityManager.createQuery(
        "update from Person p set p.fName = :f_name, p.lName = :l_name, p.dept = :deptId where personId = :id")
        .setParameter("f_name", person.getfName()).setParameter("l_name", person.getlName())
        .setParameter("deptId", person.getDeptId()).setParameter("id", person.getPersonId())
        .executeUpdate();
    if (result == 1) {
      entityManager.getTransaction().commit();
      return person;
    }
    entityManager.getTransaction().rollback();
    return null;
  }

  @Override
  public void personDelete(int personId) {
    Person doomedPerson;
    if (isClosed())
      return;
    doomedPerson = entityManager.createQuery("from Person where personId = :id", Person.class)
        .setParameter("id", personId).getSingleResult();
    entityManager.getTransaction().begin();
    entityManager.remove(doomedPerson);
    entityManager.getTransaction().commit();
  }

  public boolean isClosed() {
    return !entityManager.isOpen();
  }

  @Override
  public Person personFind(String fName, String lName) {
    return entityManager
        .createQuery("from Person p where p.fName like :name0 and p.lName like :name1",
            Person.class)
        .setParameter("name0", fName).setParameter("name1", lName).getSingleResult();
  }

  @Override
  public Long personCount() {
    Query<Long> countQuery =
        (Query<Long>) entityManager.createQuery("select count(*) from Person", Long.class);
    return countQuery.uniqueResult();
  }

  @Override
  public Person[] personGetAll() {
    List<Person> results = entityManager.createQuery("from Person", Person.class).getResultList();
    return results.toArray(new Person[results.size()]);
  }
}
