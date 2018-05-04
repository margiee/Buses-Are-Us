package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop             stop to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not an array</li>
     * </ul>
     * @throws ArrivalsDataMissingException  when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {
        try {
            JSONArray arrivals = new JSONArray(jsonResponse);

            for (int i = 0; i < arrivals.length(); i++) {
                JSONObject singleArrival = arrivals.getJSONObject(i);
                parseArrival(stop, singleArrival);
            }
        } catch (JSONException e) {
            throw new JSONException("");
        }
    }

    public static void parseArrival(Stop stop, JSONObject arrival) throws JSONException, ArrivalsDataMissingException {
        try {
            String routeNumber = arrival.getString("RouteNo");
            JSONArray arrivalSchedules = arrival.getJSONArray("Schedules");

            Route createRoute = RouteManager.getInstance().getRouteWithNumber(routeNumber);
            try {
                for (int i = 0; i < arrivalSchedules.length(); i++) {
                    JSONObject singleSchedule = arrivalSchedules.getJSONObject(i);

                    int countdown = singleSchedule.getInt("ExpectedCountdown");
                    String status = singleSchedule.getString("ScheduleStatus");
                    String destination = singleSchedule.getString("Destination");

                    Arrival createArrival = new Arrival(countdown, destination, createRoute);
                    createArrival.setStatus(status);
                    stop.addArrival(createArrival);
                }
            } catch (JSONException e) {
                throw new ArrivalsDataMissingException();
            }
        } catch (JSONException e) {
            throw new ArrivalsDataMissingException();
        }

    }
}
