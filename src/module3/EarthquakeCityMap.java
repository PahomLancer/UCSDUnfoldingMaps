package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	//private static final boolean offline = false;
	private static final boolean offline = true;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
		/*	
	    Location valLoc = new Location(-38.14, -73.03);
	    Feature valEq = new PointFeature(valLoc);
	    valEq.addProperty("title", "Valdiva, Chile");
	    valEq.addProperty("magnitude", "9.5");
	    valEq.addProperty("date", "May 22, 1960");
	    valEq.addProperty("year", "1960");
	    Marker val = new SimplePointMarker(valLoc, valEq.getProperties());
	    map.addMarker(val);
	    */
	    // The List you will populate with new SimplePointMarkers
	    //List<Marker> markers = new ArrayList<Marker>();
	    List<SimplePointMarker> markers = new ArrayList<SimplePointMarker>();
	    //markers.add(val);
	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    /*
	    System.out.print("Earthquakes.size() = " + earthquakes.size() + "\n");
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    }
	    */
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    int red = color(255, 0, 0);
	    int green = color(0, 255, 0);
	    //TODO: Add code here as appropriate
	    for (PointFeature eq: earthquakes)
	    {
	    	markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
	    }
	    for (SimplePointMarker mk : markers)
	    {
	    	Object magObj = mk.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	if (mag > 5)
	    	{
	    		mk.setColor(red);
	    		mk.setRadius(15);
	    	}
	    	else
	    	{
	    		if (mag > 4)
	    		{
	    			mk.setColor(yellow);
	    			mk.setRadius(10);
	    		}
	    		else
	    		{
	    			mk.setColor(green);
	    			mk.setRadius(5);
	    		}
	    	}
	    	Marker val = new SimplePointMarker();
	    	val = mk;
		    map.addMarker(mk);
	    }
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(100);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(200);
		rect(10, 50, 180, 500);
		textSize(20);
		fill(15);
		text("Earthquake Key", 20, 80);
		textSize(15);
		text("5.0+ Magnitude", 60, 120);
		text("4.0+ Magnitude", 60, 160);
		text("Bellow 4.0", 60, 200);
		fill(255, 0, 0);
		ellipse(40, 115, 15, 15);
		fill(255, 255, 0);
		ellipse(40, 155, 10, 10);
		fill(0, 255, 0);
		ellipse(40, 195, 5, 5);
	}
}
