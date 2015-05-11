/*
Author: Conner Anderson
Class : CSCI 330 - Relational Databases
Due   : 4/16/2015

Purpose of this file:
    This is the main application class of our program. It will
    read in the initial file (provided by the args) and parse
    the file it retrieves into Airport objects (See: Airport.java).
    After the file has been parsed, it will ask for two airports and
    display the global distance between them in km. Then the program
    will list how many airports each country has, and the total number
    of airport entries parsed from the file.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Prog1 {

    private static TreeMap<String, Airport> codeTable;
    private static TreeMap<String, ArrayList<Airport>> countryTable;

    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Argument Missing: Filename");
            System.exit(0);
        } else {
            codeTable = new TreeMap<>();
            countryTable = new TreeMap<>();

            // Parse each file line using a quotation-catching regular expression.
            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                String line = br.readLine();

                while (line != null) {
                    String[] data = line.split(",(?=([^\"]|\"[^\"]*\")*$)");
                    parseAirportData(data);
                    line = br.readLine();
                }
            } catch(IOException e) {
                System.err.println("File \"" + args[0] + "\" not found!");
                System.exit(0);
            }
        }

        // Request two airport short-names
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Airport Code of two airports:");

        System.out.print("Airport 1: ");
        String ap1 = in.nextLine();
        System.out.print("\nAirport 2: ");
        String ap2 = in.nextLine();

        // If the airports exist, display the distance between them
        if(!codeTable.containsKey(ap1) || !codeTable.containsKey(ap2)) {
            System.err.println("One of the listed airports was not found.");
        } else {
            int dist = Airport.worldDistance(codeTable.get(ap1), codeTable.get(ap2));
            System.out.println("\nThe approximate distance between " + ap1 + " and " + ap2 + " is: " + dist + " km");
        }

        // List number of airports that were parsed per country and in total
        System.out.println("\nList of Airports:");
        for(String country : countryTable.keySet()) {
            System.out.println(country + ": " + countryTable.get(country).size() + " airports.");
        }
        System.out.println("\nThe file has " + codeTable.size() + " airports on record.");
    }

    /**
     * Parses a data line with known attribute count of 8 and
     * adds the newly parsed airport information into two tables.
     * @param data 8 Attribute tuple containing an airport's global information
     */
    private static void parseAirportData(String[] data) {
        Airport airport;
        if(data.length == 8) {
            double lat = Float.parseFloat(data[5]);
            double lon = Float.parseFloat(data[6]);
            int code  = Integer.parseInt(data[7]);

            airport = new Airport(data[0], data[1], data[2], data[3], data[4], lat, lon, code);

            codeTable.put(data[0], airport);

            if(countryTable.containsKey(data[3])) {
                countryTable.get(data[3]).add(airport);
            } else {
                ArrayList<Airport> aps = new ArrayList<>();
                aps.add(airport);
                countryTable.put(data[3], aps);
            }
        } else {
            System.out.println("A line was skipped -- missing data or extra data found.");
        }
    }
}
