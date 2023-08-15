package io.xj.gui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WorkstationGuiApplication {


  public WorkstationGuiApplication(
  ) {
    // todo something
  }

  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    // todo something
  }

  public static void main(String[] args) {
    Application.launch(WorkstationGuiFxApplication.class, args);
  }
}
