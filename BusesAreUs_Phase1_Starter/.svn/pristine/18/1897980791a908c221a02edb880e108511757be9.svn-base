package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }
    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }
    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)
     *     <li>JSON data is not an array
     * </ul>
     * If a JSONException is thrown, no stops should be added to the stop manager
     *
     * @throws RouteDataMissingException when
     * <ul>
     *  <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *  <li>The value of the Patterns element is not an array for any route</li>
     *  <li>JSON data is missing PatternNo, Destination, or Direction element for any route pattern</li>
     * </ul>
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */

    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {
        try {
            JSONArray routes = new JSONArray(jsonResponse);

            for (int i = 0; i < routes.length(); i++) {
                JSONObject singleRoute = routes.getJSONObject(i);
                parseRoute(singleRoute);
            }
        } catch (JSONException e) {
            throw new JSONException("");
        }
    }

    public void parseRoute(JSONObject route) throws JSONException, RouteDataMissingException {
        try {
            String routeNumber = route.getString("RouteNo");
            String routeName = route.getString("Name");
            JSONArray routePatterns = route.getJSONArray("Patterns");
            String patternNo;
            String destination;
            String direction;
            RoutePattern newRoutePattern;

            Route createRoute = RouteManager.getInstance().getRouteWithNumber(routeNumber, routeName);

            try {
                for (int i = 0; i < routePatterns.length(); i++) {
                    JSONObject routePattern = routePatterns.getJSONObject(i);
                    patternNo = routePattern.getString("PatternNo");
                    destination = routePattern.getString("Destination");
                    direction = routePattern.getString("Direction");
                    newRoutePattern = new RoutePattern(patternNo, destination, direction, createRoute);
                    createRoute.addPattern(newRoutePattern);
                }
            } catch(JSONException e) {
                throw new RouteDataMissingException();
            }
        } catch (JSONException e) {
            throw new RouteDataMissingException();
        }
    }
}
