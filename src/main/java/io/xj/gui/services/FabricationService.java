package io.xj.gui.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

@org.springframework.stereotype.Service
public class FabricationService extends Service<Boolean> {

  public FabricationService() {
    // todo something
  }

  protected Task<Boolean> createTask() {
    return new Task<>() {
      protected Boolean call() {
        // todo something
        return true;
      }
    };
  }
}
