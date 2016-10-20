package dev.finder.busroute.rest.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Deivarayan Azhagappan
 *
 */
public class RouteFinder {

  private static Logger LOGGER = Logger.getLogger("InfoLogging");

  private String inputFileName;

  private Route route = new Route();

  private Map<Integer, Set<Integer>> routes;

  public RouteFinder(String inputFileName) {
    this.inputFileName = inputFileName;

    try {
      initialize();
    } catch (IOException | URISyntaxException exception) {
      LOGGER.severe("File " + exception.getMessage() + " not found");
    }
  }

  public void initialize() throws IOException, URISyntaxException {
    routes = readFile();

    routes = createStationMapping(routes);

  }

  public Route findDirectRoute(int dep_sid, int arr_sid) throws IOException {

    boolean directRoute = false;

    if(routes == null) {
      return null;  
    }
    
    Set<Integer> stationsSet = routes.get(dep_sid);
    if (stationsSet != null && stationsSet.contains(arr_sid)) {
      directRoute = true;
    }
    route.setArr_sid(arr_sid);
    route.setDep_sid(dep_sid);
    route.setDirect_bus_route(directRoute);

    return route;
  }

  private Map<Integer, Set<Integer>> createStationMapping(
      Map<Integer, Set<Integer>> routes) {

    Map<Integer, Set<Integer>> stationMap = routes
        .values()
        .parallelStream()
        .collect(
            HashMap::new,
            (map, stations) -> stations.forEach(station -> map.computeIfAbsent(
                station, key -> new HashSet<>()).addAll(stations)),
            (m1, m2) -> m2.forEach((stationId, stationIds) -> m1.merge(
                stationId, stationIds, (a, b) -> {
                  a.addAll(b);
                  return a;
                })));

     return stationMap;
  }

  private Map<Integer, Set<Integer>> readFile() throws IOException,
      URISyntaxException {

    Map<Integer, Set<Integer>> routeMap = new HashMap<>();

    File file = new File(inputFileName);

    try (Stream<String> lines = Files.lines(file.toPath())) {
      lines.forEach(line -> {
        List<Integer> routes = Arrays.stream(line.trim().split(" "))
            .filter(s -> s.matches("[0-9]+"))
            .map(Integer::valueOf)
            .collect(Collectors.toList());

        if (routes.size() > 1) {
          routeMap.put(routes.get(0),
              routes.stream().skip(1).collect(Collectors.toSet()));
        }
      });
    }
    LOGGER.info("Completed reading files from :" + inputFileName);
    return routeMap;
  }
}
