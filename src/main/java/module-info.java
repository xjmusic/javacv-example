module main {
  requires ch.qos.logback.classic;
  requires ch.qos.logback.core;
  requires jakarta.annotation;
  requires java.desktop;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires org.apache.commons.codec;
  requires org.apache.commons.io;
  requires org.bytedeco.ffmpeg;
  requires org.bytedeco.flandmark.platform;
  requires org.bytedeco.flandmark;
  requires org.bytedeco.javacv.platform;
  requires org.bytedeco.javacv;
  requires org.slf4j;
  requires spring.beans;
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires spring.context;
  requires spring.core;
  requires spring.jcl;
  requires org.apache.logging.log4j;

  opens io.xj.gui.controllers to javafx.graphics, javafx.base, javafx.fxml, javafx.controls, spring.beans;
  opens io.xj.gui.events to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
  opens io.xj.gui.listeners to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core, ch.qos.logback.core;
  opens io.xj.gui.services to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
  opens io.xj.gui to ch.qos.logback.core, javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
}
