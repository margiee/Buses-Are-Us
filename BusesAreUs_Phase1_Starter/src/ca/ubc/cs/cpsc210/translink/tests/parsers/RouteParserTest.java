package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the RouteParser
 */
// TODO: Write more tests

public class RouteParserTest {
    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testRouteParserNormal() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        assertEquals(229, RouteManager.getInstance().getNumRoutes());
    }

    @Test
    public void testRouteParser() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();

/*            "Name": "WHITE ROCK SOUTH/BRIDGEPORT STATION",
                "OperatingCompany": "CMBC",
                "Patterns": [
                "Destination": "BRIDGEPORT STN",
                    "Direction": "NORTH",
                    "PatternNo": "NB1",
                    "RouteMap": {
                "Href": "http://nb.translink.ca/geodata/trip/354-NB1.kmz"
                "Destination": "WHITE ROCK SOUTH",
                    "Direction": "SOUTH",
                    "PatternNo": "SB1",
                    "RouteMap": {
                "Href": "http://nb.translink.ca/geodata/trip/354-SB1.kmz"
            "RouteNo": "354"*/
        Route testRoute = RouteManager.getInstance().getRouteWithNumber("354");
        assertEquals("WHITE ROCK SOUTH/BRIDGEPORT STATION", testRoute.getName());
        assertEquals(2, testRoute.getPatterns().size());

        RoutePattern testPattern1 = testRoute.getPattern("NB1");
        assertEquals("NB1", testPattern1.getName());
        assertEquals("BRIDGEPORT STN", testPattern1.getDestination());
        assertEquals("NORTH", testPattern1.getDirection());
        RoutePattern testPattern2 = testRoute.getPattern("SB1", "WHITE ROCK SOUTH", "SOUTH");
        assertEquals("SB1", testPattern2.getName());
        assertEquals("WHITE ROCK SOUTH", testPattern2.getDestination());
        assertEquals("SOUTH", testPattern2.getDirection());
    }
}
