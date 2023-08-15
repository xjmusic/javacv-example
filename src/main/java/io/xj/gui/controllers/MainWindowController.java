package io.xj.gui.controllers;

import io.xj.gui.WorkstationLogAppender;
import io.xj.gui.services.FabricationService;
import io.xj.gui.services.FabricationStatus;
import jakarta.annotation.Nullable;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MainWindowController {
  Logger LOG = LoggerFactory.getLogger(MainWindowController.class);
  private FabricationStatus status;
  private final HostServices hostServices;
  private final ConfigurableApplicationContext ac;
  private final FabricationService fabricationService;
  private final String launchRepositoryUrl;
  private final String lightTheme;
  private final String darkTheme;
  private final String defaultOutputSeconds;

  @Nullable
  private Scene mainWindowScene;

  public MainWindowController(
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") HostServices hostServices,
    @Value("${gui.launch.repository.url}") String launchRepositoryUrl,
    @Value("${gui.theme.dark}") String darkTheme,
    @Value("${gui.theme.light}") String lightTheme,
    @Value("${output.seconds}") String defaultOutputSeconds,
    ConfigurableApplicationContext ac,
    FabricationService fabricationService
  ) {
    this.fabricationService = fabricationService;
    status = FabricationStatus.Ready;
    this.hostServices = hostServices;
    this.ac = ac;
    this.launchRepositoryUrl = launchRepositoryUrl;
    this.lightTheme = lightTheme;
    this.darkTheme = darkTheme;
    this.defaultOutputSeconds = defaultOutputSeconds;

    // bind to the log appender
    WorkstationLogAppender.LISTENER.set(this::appendLogLine);
  }

  @FXML
  protected CheckMenuItem darkThemeCheck;
  @FXML
  protected TextField fieldOutputSeconds;
  @FXML
  protected TextArea textAreaLogs;
  @FXML
  protected Button buttonAction;

  @FXML
  private void toggleDarkTheme() {
    if (darkThemeCheck.isSelected()) {
      enableDarkTheme();
    } else {
      disableDarkTheme();
    }
  }

  @FXML
  protected void onQuit() {
    var exitCode = SpringApplication.exit(ac, () -> 0);
    LOG.info("Will exit with code {}", exitCode);
    System.exit(exitCode);
  }

  @FXML
  protected void onButtonActionPress() {
    switch (status) {
      case Ready -> start();
      case Active -> stop();
      case Cancelled, Done, Failed -> reset();
    }
  }

  public void start() {
    onStatusUpdate(FabricationStatus.Starting);
    fabricationService.setOnReady((WorkerStateEvent ignored) -> onStatusUpdate(FabricationStatus.Ready));
    fabricationService.setOnRunning((WorkerStateEvent ignored) -> onStatusUpdate(FabricationStatus.Active));
    fabricationService.setOnSucceeded((WorkerStateEvent ignored) -> onStatusUpdate(FabricationStatus.Done));
    fabricationService.setOnCancelled((WorkerStateEvent ignored) -> onStatusUpdate(FabricationStatus.Cancelled));
    fabricationService.setOnFailed((WorkerStateEvent ignored) -> onStatusUpdate(FabricationStatus.Failed));
    fabricationService.start();
  }

  public void stop() {
    onStatusUpdate(FabricationStatus.Cancelling);
    fabricationService.cancel();
  }

  public void reset() {
    onStatusUpdate(FabricationStatus.Resetting);
    fabricationService.reset();
  }

  public void appendLogLine(String line) {
    if (Objects.nonNull(line) && Objects.nonNull(textAreaLogs))
      try {
        Platform.runLater(() -> textAreaLogs.appendText(line + "\n"));
      } catch (Exception e) {
        // no op
      }
  }

  @FXML
  protected Label labelStatus;

  @FXML
  protected void onLaunchRepo() {
    LOG.info("Will launch repository");
    hostServices.showDocument(launchRepositoryUrl);
  }

  public @Nullable Scene getMainWindowScene() {
    return mainWindowScene;
  }

  public void setMainWindowScene(Scene mainWindowScene) {
    this.mainWindowScene = mainWindowScene;
  }

  public void onStageReady() {
    enableDarkTheme();
    onStatusUpdate(status);
    fieldOutputSeconds.setText(defaultOutputSeconds);
  }

  public void onStatusUpdate(FabricationStatus status) {
    LOG.info("Status update: {} -> {}", this.status, status);
    this.status = status;
    labelStatus.setText(status.toString());
    switch (status) {
      case Initializing, Starting, Cancelling, Resetting -> buttonAction.setDisable(true);
      case Ready -> {
        buttonAction.setText("Start");
        buttonAction.setDisable(false);
      }
      case Active -> {
        buttonAction.setText("Stop");
        buttonAction.setDisable(false);
      }
      case Cancelled, Done, Failed -> {
        buttonAction.setText("Reset");
        buttonAction.setDisable(false);
      }
    }
  }

  private void enableDarkTheme() {
    mainWindowScene.getStylesheets().remove(lightTheme);
    mainWindowScene.getStylesheets().add(darkTheme);
  }

  private void disableDarkTheme() {
    mainWindowScene.getStylesheets().remove(darkTheme);
    mainWindowScene.getStylesheets().add(lightTheme);
  }
}
