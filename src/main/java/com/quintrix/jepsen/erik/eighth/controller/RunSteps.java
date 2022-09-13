package com.quintrix.jepsen.erik.eighth.controller;

import java.util.Scanner;
import com.quintrix.jepsen.erik.eighth.model.Department;
import com.quintrix.jepsen.erik.eighth.model.Person;
import com.quintrix.jepsen.erik.eighth.repository.DepartmentRepositoryImpl;
import com.quintrix.jepsen.erik.eighth.repository.IDepartmentRepository;
import com.quintrix.jepsen.erik.eighth.repository.IPersonRepository;
import com.quintrix.jepsen.erik.eighth.repository.PersonRepositoryImpl;
import com.quintrix.jepsen.erik.eighth.service.DBService;

public class RunSteps {
  private IDepartmentRepository departmentRepository;
  private IPersonRepository personRepository;
  private String[] departmentNames = new String[] {"Accounting", "PR", "Administration", "B&G"};
  private String[] personNames = new String[] {"Bugs Bunny", "Porky Pig", "Daffy Duck",
      "Elmer Fudd", "Mickey Mouse", "Minney Mouse", "Goofy Dogg", "Donald Duck"};

  public RunSteps(DBService dbService) {
    departmentRepository = new DepartmentRepositoryImpl(dbService.getEntityManager());
    personRepository = new PersonRepositoryImpl(dbService.getEntityManager());
  }

  public void addDepartments() {
    if (departmentRepository.isDepartmentClose()) {
      System.out.println("Department repository is closed.");
      return;
    }
    if (departmentRepository.departmentCount().longValue() <= 0) {
      System.out.println("Adding departments...");
      for (String dept : departmentNames) {
        departmentRepository.departmentCreate(new Department(dept));
      }
    } else {
      System.out.println("Departments have already been added.");
    }
  }

  public void addPerson() {
    if (personRepository.isClosed()) {
      System.out.println("Person repository is closed.");
      return;
    }
    if (personRepository.personCount() <= 0) {
      System.out.println("Adding persons...");
      for (String name : personNames) {
        String[] nameParts = name.split(" ");
        Department addDept;
        if (name.equals("Bugs Bunny") || name.equals("Mickey Mouse"))
          addDept = departmentRepository.departmentFind(departmentNames[2]);
        else if (name.equals("Donald Duck") || name.equals("Daffy Duck"))
          addDept = departmentRepository.departmentFind(departmentNames[1]);
        else if (name.equals("Porky Pig") || name.equals("Minney Mouse"))
          addDept = departmentRepository.departmentFind(departmentNames[0]);
        else
          addDept = departmentRepository.departmentFind(departmentNames[3]);
        personRepository.personCreate(new Person(nameParts[0], nameParts[1], addDept));
      }
    } else {
      System.out.println("Persons have already been added.");
    }
  }

  public void getAllDepartments() {
    Department[] deptArray = departmentRepository.departmentGetAll();
    System.out.println("Here are all the departments...");
    for (Department dept : deptArray)
      System.out.println(dept.toString());
  }

  public void getAllPersons() {
    Person[] personArray = personRepository.personGetAll();
    System.out.println("Here are all the persons...");
    for (Person person : personArray)
      System.out.println(person.toString());
  }

  public void renameAPerson() {
    String readIn;
    Integer whichPerson;
    Person updatedPerson, currentPerson;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Who would you like to rename (or 0 to bypass): ");
    whichPerson = Integer.parseInt(scanner.nextLine());
    if (whichPerson == 0) {
      scanner.close();
      return;
    }
    currentPerson = personRepository.personRead(whichPerson);
    updatedPerson = new Person(currentPerson.getfName(), currentPerson.getlName());
    updatedPerson.setPersonId(currentPerson.getPersonId());
    System.out.printf("What should the new first name of %s be? ", currentPerson.getfName());
    readIn = scanner.nextLine();
    if (!readIn.isEmpty())
      updatedPerson.setfName(readIn);
    System.out.printf("What should the new last name of %s be? ", updatedPerson.getlName());
    readIn = scanner.nextLine();
    scanner.close();
    if (!readIn.isEmpty())
      updatedPerson.setlName(readIn);
    updatedPerson.setDeptId(currentPerson.getDeptId());
    System.out.println("Here is the updated person...");
    System.out.println(personRepository.personUpdate(updatedPerson));
  }

  public void deleteAll() {
    Person[] persons;
    Department[] depts;
    System.out.println("Deleting everything.");
    persons = personRepository.personGetAll();
    depts = departmentRepository.departmentGetAll();
    for (Person person : persons)
      personRepository.personDelete(person.getPersonId());
    for (Department dept : depts)
      departmentRepository.departmentDelete(dept.getDeptId());
  }
}
