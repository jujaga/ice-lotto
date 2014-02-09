package com.jrfom.icelotto.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This listener is used to unload database drivers when the context is
 * shutdown, thereby preventing memory leaks.
 */
public class ContextListener implements ServletContextListener {
  private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      Driver driver = drivers.nextElement();
      if (driver.getClass().getClassLoader() == this.getClass().getClassLoader()) {
        try {
          log.info("Deregistering driver: `{}`", driver.getClass().getName());
          DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
          log.error("Could not deregister driver: `{}`", e.getMessage());
          log.debug(e.toString());
        }
      }
    }
  }
}