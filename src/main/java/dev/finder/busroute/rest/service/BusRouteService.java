package dev.finder.busroute.rest.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import dev.finder.busroute.Application;

/**
 * 
 * @author Deivarayan Azhagappan
 *
 */
@Service
public class BusRouteService {

  public Route getDirectRoute(int dep_sid, int arr_sid) throws IOException {
    return Application.getRouteFinder()
          .findDirectRoute(dep_sid, arr_sid);
  }
}
