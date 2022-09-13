package com.quintrix.jepsen.erik.eighth.repository;

import com.quintrix.jepsen.erik.eighth.model.Person;

public interface IPersonRepository {
  public Person personCreate(Person person);

  public Person personRead(int personId);

  public Person personUpdate(Person person);

  public void personDelete(int personId);

  public boolean isClosed();

  public Person personFind(String fName, String lName);

  public Long personCount();

  public Person[] personGetAll();
}
