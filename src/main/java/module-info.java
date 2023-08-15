module main {
  requires jakarta.annotation;
  requires java.desktop;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires org.apache.commons.codec;
  requires org.apache.commons.io;
  requires org.slf4j;
  requires spring.beans;
  requires spring.core;
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires spring.context;
  requires org.bytedeco.flandmark.platform;
  requires org.bytedeco.flandmark;
  requires org.bytedeco.javacv.platform;
  requires org.bytedeco.javacv;
  requires org.jooq;
  requires org.bytedeco.ffmpeg;
  requires spring.jcl;
  requires ch.qos.logback.classic;
  requires ch.qos.logback.core;

  opens io.xj.gui.controllers to javafx.graphics, javafx.base, javafx.fxml, javafx.controls, spring.beans;
  opens io.xj.gui.events to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
  opens io.xj.gui.listeners to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core, ch.qos.logback.core;
  opens io.xj.gui.services to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
  opens io.xj.gui to ch.qos.logback.core, javafx.base, javafx.controls, javafx.fxml, javafx.graphics, spring.beans, spring.context, spring.core;
}
