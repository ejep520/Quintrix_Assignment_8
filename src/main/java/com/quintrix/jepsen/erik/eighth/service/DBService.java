package com.quintrix.jepsen.erik.eighth.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBService {
  private EntityManagerFactory entityManagerFactory =
      Persistence.createEntityManagerFactory("com.quintrix.jepsen.erik.eighth");
  private EntityManager entityManager = entityManagerFactory.createEntityManager();


  public EntityManager getEntityManager() {
    return entityManager;
  }

  public void tearDown() {
    entityManager.close();
    entityManagerFactory.close();
  }
}
