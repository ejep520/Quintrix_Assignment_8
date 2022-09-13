package com.quintrix.jepsen.erik.eighth;

import com.quintrix.jepsen.erik.eighth.controller.RunSteps;
import com.quintrix.jepsen.erik.eighth.service.DBService;

public class App {
  private static RunSteps runSteps;
  private static DBService dbService;

  public static void main(String[] args) {
    dbService = new DBService();
    runSteps = new RunSteps(dbService);
    runSteps.addDepartments();
    runSteps.addPerson();
    runSteps.getAllDepartments();
    runSteps.getAllPersons();
    runSteps.renameAPerson();
    runSteps.deleteAll();
    dbService.tearDown();
  }
}
