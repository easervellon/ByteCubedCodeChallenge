package teleportation.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;


public class TeleportationHub {

/**
 * All possible two-way route combinations
 */

static String r1 = "Atlanta-Washington";		static String r11 = "Washington-Atlanta";
static String r2 = "Baltimore-Washington";		static String r22 = "Washington-Baltimore";
static String r3 = "Baltimore-Philadelphia";	static String r33 = "Philadelphia-Baltimore";
static String r4 = "Baltimore-Seattle";			static String r44 = "Seattle-Baltimore";
static String r5 = "New York-Philadelphia";		static String r55 = "Philadelphia-New York";
static String r6 = "New York-Seattle";			static String r66 = "Seattle-New York";
static String r7 = "Los Angeles-San Francisco"; static String r77 = "San Francisco-Los Angeles";
static String r8 = "Los Angeles-Oakland";		static String r88 = "Oakland-Los Angeles";
static String r9 = "Oakland-San Francisco";		static String r99 = "San Francisco-Oakland";
static String r10 = "Baltimore-Oakland";		static String r100 = "Oakland-Baltimore";

/**
 * Adding all route combinations to a List  
 */

static List<String>  allRoutes = Arrays.asList( 
							r1,r2,r3,
							r4,r5,r6,
							r7,r8,r9,r10,
							r11,r22,r33,
							r44,r55,r66,
							r77,r88,r99,r100
							);

/**
 * Creating a HashMap of String(key), List<String> (value)
 * mapping destination city to possible arrival cities
 */

private static HashMap<String, List<String>> left_map_rights;

@Test
public void letsTravel() {
	
	Scanner scan = new Scanner(System.in);
	String departCity;
	String arrivalCity;
	final String WELCOME_MESSAGE = "Welcome to our Teleportation Hub!";
	final String DEPART_FROM = "Which city will you be departing from? ";
	final String ARRIVE_TO = "Which city will you be arriving to? ";
	
	System.out.println(WELCOME_MESSAGE);
	
	System.out.print(DEPART_FROM);
	departCity=scan.next();
	
	System.out.print(ARRIVE_TO);
	arrivalCity=scan.next();
	scan.close();
	
    left_map_rights = new HashMap<>();
    Iterator<String> allRoutesIterator = allRoutes.iterator(); 
    String line;
    List<String> lines = new ArrayList<>();
    
    while (allRoutesIterator.hasNext()) {
    		line = allRoutesIterator.next(); 
        if (lines.contains(line)) { // ensure no duplicate lines
            continue;
        }
        lines.add(line);
        int space_location = line.indexOf('-');
        String left = line.substring(0, space_location);
        String right = line.substring(space_location + 1);
        if(left.equals(right)){ // rejects entries whereby left = right
            continue;
        }
        List<String> rights = left_map_rights.get(left);
        if (rights == null) {
            rights = new ArrayList<String>();
            left_map_rights.put(left, rights);
        }
        rights.add(right);
        //System.out.println("rights "+rights);
    }

    System.out.println("left_map_rights "+ left_map_rights);
 
    List<List<String>> routes = GetAllRoutes(departCity, arrivalCity);
    
    System.out.print("Route(s) available for desired travel plan: ");
    
    for (List<String> route : routes) {
        System.out.print(route);
    }
}

	public static List<List<String>> GetAllRoutes(String start, String end) {

	List<List<String>> routes = new ArrayList<>();
    List<String> rights = left_map_rights.get(start);
    if (rights != null) {
        for (String right : rights) {
            List<String> route = new ArrayList<>();
            route.add(start);
            route.add(right);
            Chain(routes, route, right, end);
        }
    }
    return routes;
}

	public static void Chain(List<List<String>> routes, List<String> route, String right_most_currently, String end) {
	
		if (right_most_currently.equals(end)) {
        routes.add(route);
        return;
		}
		
    List<String> rights = left_map_rights.get(right_most_currently);
    
    if (rights != null) {
        for (String right : rights) {
            if (!route.contains(right)) {
                List<String> new_route = new ArrayList<String>(route);
                new_route.add(right);
                Chain(routes, new_route, right, end);
           
            	}
        	}
    	}
	}
}
