public class Airport {

    private String codeName;
    private String longName;
    private String cityName;
    private String countryName;
    private String countryCode;
    private double lat, lon;
    private int worldCode;

    public Airport(String codeName, String longName, String cityName, String countryName, String countryCode,
                   double lat, double lon, int worldCode) {
        this.codeName = codeName;
        this.longName = longName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.lat = lat;
        this.lon = lon;
        this.worldCode = worldCode;
    }

    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }

    /**
     * This function calculates the world distance between
     * two airports in kilometers (rounded to the nearest int).
     * @param start Current airport
     * @param end Destination airport
     * @return World distance between start and end airports.
     */
    public static int worldDistance(Airport start, Airport end) {
        double lat1 = Math.toRadians(start.getLat());
        double lat2 = Math.toRadians(end.getLat());
        double deltaLon = Math.toRadians(start.getLon() - end.getLon());

        double arc = Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(deltaLon);
        return (int) (6371 * Math.acos(arc));
    }
}
