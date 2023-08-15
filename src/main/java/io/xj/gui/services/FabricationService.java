package io.xj.gui.services;

import io.xj.gui.listeners.MainWindowStageReadyListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.stereotype.Service
public class FabricationService extends Service<Boolean> {
  static final Logger LOG = LoggerFactory.getLogger(FabricationService.class);
  private final ObjectProperty<Integer> seconds = new SimpleObjectProperty<>();

  protected Task<Boolean> createTask() {
    return new Task<>() {
      protected Boolean call() {
        LOG.info("Will fake work for {} seconds", seconds.get());
        // get system millis. do a while loop until the difference is greater than the seconds, waiting in 100 ms increments
        var start = System.currentTimeMillis();
        var end = start + (seconds.get() * 1000);
        while (System.currentTimeMillis() < end) {
          try {
            //noinspection BusyWait
            Thread.sleep(100);
          } catch (InterruptedException e) {
            LOG.warn("Interrupted while waiting for fabrication to complete", e);
          }
        }
        return true;
      }
    };
  }

  public void setSeconds(Integer seconds) {
    this.seconds.set(seconds);
  }
}
