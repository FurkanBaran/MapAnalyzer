import java.io.*;
import java.util.*;

/**
 * Class representing the road map and the analysis operations.
 */
class RoadMap {
    private List<Road> roads = new ArrayList<>();
    private Set<String> points = new HashSet<>();
    private String startPoint;
    private String endPoint;

    /**
     * Loads the road map from the given file.
     *
     * @param filename the name of the file to load the road map from.
     * @throws IOException if an I/O error occurs.
     */
    public void loadFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        if (line != null) {
            String[] parts = line.split("\t");
            startPoint = parts[0];
            endPoint = parts[1];
        }

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            String point1 = parts[0];
            String point2 = parts[1];
            int distance = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[3]);
            roads.add(new Road(point1, point2, distance, id));
            points.add(point1);
            points.add(point2);
        }
        reader.close();
    }

    /**
     * Analyzes the road map and writes the results to the given output file.
     *
     * @param outputFile the name of the file to write the results to.
     * @throws IOException if an I/O error occurs.
     */
    public void analyzeAndWriteResults(String outputFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        List<Road> fastestRoute = findFastestRoute(startPoint, endPoint, roads);
        writer.println("Fastest Route from " + startPoint + " to " + endPoint + " (" + getTotalDistance(fastestRoute) + " KM):");
        for (Road road : fastestRoute) {
            writer.println(road);
        }

        List<Road> barelyConnectedMap = findBarelyConnectedMap();
        writer.println("Roads of Barely Connected Map is:");
        for (Road road : barelyConnectedMap) {
            writer.println(road);
        }

        List<Road> fastestRouteBarelyConnected = findFastestRoute(startPoint, endPoint, barelyConnectedMap);
        writer.println("Fastest Route from " + startPoint + " to " + endPoint + " on Barely Connected Map (" + getTotalDistance(fastestRouteBarelyConnected) + " KM):");
        for (Road road : fastestRouteBarelyConnected) {
            writer.println(road);
        }

        writer.println("Analysis:");
        writer.printf(Locale.US, "Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n", (double) getTotalDistance(barelyConnectedMap) / getTotalDistance(roads));
        writer.printf(Locale.US, "Ratio of Fastest Route Between Barely Connected and Original Map: %.2f\n", (double) getTotalDistance(fastestRouteBarelyConnected) / getTotalDistance(fastestRoute));
        writer.close();
    }

    /**
     * Finds the fastest route from the start point to the end point using Dijkstra's algorithm.
     *
     * @param start the start point.
     * @param end   the end point.
     * @param roads the list of roads.
     * @return the list of roads representing the fastest route.
     */
    private List<Road> findFastestRoute(String start, String end, List<Road> roads) {
        Map<String, List<Road>> adjList = new HashMap<>();
        for (Road road : roads) {
            adjList.computeIfAbsent(road.point1, k -> new ArrayList<>()).add(road);
            adjList.computeIfAbsent(road.point2, k -> new ArrayList<>()).add(road);
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, Road> previousRoad = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String point : points) {
            distances.put(point, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String currentPoint = pq.poll();
            if (currentPoint.equals(end)) {
                break;
            }
            List<Road> sortedRoads = new ArrayList<>(adjList.get(currentPoint));
            Collections.sort(sortedRoads); //  Sort the roads to ensure the order is consistent, thanks to the Comparable interface implemented in the Road class

            for (Road road :sortedRoads) {
                String neighbor = road.point1.equals(currentPoint) ? road.point2 : road.point1;
                int newDist = distances.get(currentPoint) + road.distance;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousRoad.put(neighbor, road);
                    pq.add(neighbor);
                }
            }
        }

        List<Road> path = new ArrayList<>();
        for (String at = end; at != null; at = previousRoad.get(at) == null ? null : (previousRoad.get(at).point1.equals(at) ? previousRoad.get(at).point2 : previousRoad.get(at).point1)) {
            Road road = previousRoad.get(at);
            if (road != null) {
                path.add(road);
            }
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Calculates the total distance of the given list of roads.
     *
     * @param roads the list of roads.
     * @return the total distance.
     */
    private int getTotalDistance(List<Road> roads) {
        return roads.stream().mapToInt(road -> road.distance).sum();
    }

    /**
     * Finds the barely connected map using Kruskal's algorithm.
     *
     * @return the list of roads representing the barely connected map.
     */
    private List<Road> findBarelyConnectedMap() {
        Collections.sort(roads);
        UnionFind uf = new UnionFind(points.size());
        Map<String, Integer> pointIndex = new HashMap<>();
        int index = 0;
        for (String point : points) {
            pointIndex.put(point, index++);
        }

        List<Road> mst = new ArrayList<>();
        for (Road road : roads) {
            int index1 = pointIndex.get(road.point1);
            int index2 = pointIndex.get(road.point2);
            if (uf.union(index1, index2)) {
                mst.add(road);
            }
        }
        return mst;
    }
}
