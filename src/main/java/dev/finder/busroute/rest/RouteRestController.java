package dev.finder.busroute.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.finder.busroute.rest.service.BusRouteService;
import dev.finder.busroute.rest.service.Route;

/**
 * 
 * @author Deivarayan Azhagappan
 *
 */
@RestController
@RequestMapping("/api")
public class RouteRestController {

  @Autowired
  BusRouteService routeService;
  
  @RequestMapping("/direct")
  public Route getRoute(@RequestParam int dep_sid,
        @RequestParam int arr_sid) throws IOException {
    return routeService.getDirectRoute(dep_sid, arr_sid);
  }
}
