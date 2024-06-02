
/**
 * Class representing a road with two endpoints, distance, and an ID.
 */
class Road implements Comparable<Road> {
    String point1;
    String point2;
    int distance;
    int id;

    /**
     * Constructs a Road object with the given parameters.
     *
     * @param point1   the first endpoint of the road.
     * @param point2   the second endpoint of the road.
     * @param distance the distance of the road.
     * @param id       the ID of the road.
     */
    public Road(String point1, String point2, int distance, int id) {
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
        this.id = id;
    }

    @Override
    public int compareTo(Road other) {
        if (this.distance != other.distance) {
            return Integer.compare(this.distance, other.distance);
        }
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        String p1 = point1;
        String p2 = point2;
        return p1 + "\t" + p2 + "\t" + distance + "\t" + id;
    }
}
