package dev.finder.busroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.finder.busroute.rest.service.RouteFinder;

/**
 * 
 * @author Deivarayan Azhagappan
 *
 */
@SpringBootApplication
public class Application {

  public static RouteFinder routeFinder;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

    createRouteFinder(args[0]);
  }

  public static void createRouteFinder(String fileName) {
    Application.routeFinder = new RouteFinder(fileName);
  }

  public static RouteFinder getRouteFinder() {
    return routeFinder;
  }
}
