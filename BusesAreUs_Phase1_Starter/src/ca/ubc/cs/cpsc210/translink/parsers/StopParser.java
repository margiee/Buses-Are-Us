package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }
    /**
     * Parse stop data from the file and add all stops to stop manager.
     *
     */
    public void parse() throws IOException, StopDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }
    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)</li>
     *     <li>JSON data is not an array</li>
     * </ul>
     * If a JSONException is thrown, no stops should be added to the stop manager
     * @throws StopDataMissingException when
     * <ul>
     *  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     * </ul>
     * If a StopDataMissingException is thrown, all correct stops are first added to the stop manager.
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {
        try {
            JSONArray stops = new JSONArray(jsonResponse);

            for (int i = 0; i < stops.length(); i++) {
                JSONObject singleStop = stops.getJSONObject(i);
                parseStop(singleStop);
            }
        } catch (JSONException e) {
            throw new JSONException("");
        }
    }

    public void parseStop(JSONObject stop) throws JSONException, StopDataMissingException {
        try {
            String stopName = stop.getString("Name");
            Integer stopNumber = stop.getInt("StopNo");
            String stopRoutes = stop.getString("Routes");
            LatLon stopLocation = new LatLon(stop.getDouble("Latitude"), stop.getDouble("Longitude"));

            Stop createStop = StopManager.getInstance().getStopWithNumber(stopNumber, stopName, stopLocation);
            String[] routeList = stopRoutes.split(",");
            for (int i = 0; i < routeList.length; i++) {
                Route createRoute = RouteManager.getInstance().getRouteWithNumber(routeList[i].trim());
                createStop.addRoute(createRoute);
            }
        } catch (JSONException e) {
            throw new StopDataMissingException();
        }
    }
}
