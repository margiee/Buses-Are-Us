package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the parser for route pattern map information
 */

// TODO: Write more tests

public class RouteMapParserTest {
    private static final double TOL = 0.000000000000000000000001;

    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    private int countNumRoutePatterns() {
        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count ++;
            }
        }
        return count;
    }

    @Test
    public void testRouteParserNormal() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        assertEquals(1232, countNumRoutePatterns());
    }

    // N100-W4-71;49.199658;-122.949611;49.199405;-122.950261;49.19956;-122.950457;49.198501;-122.952413; ...
    @Test
    public void testRouteParserSinglePoint() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route testRoute = RouteManager.getInstance().getRouteWithNumber("N100");
        RoutePattern testPattern = testRoute.getPattern("W4-71");
        assertEquals("W4-71", testPattern.getName());
        List<LatLon> testPath = testPattern.getPath();
        assertEquals(221, testPath.size());
        LatLon firstCoordinates = testPath.get(0);
        LatLon secondCoordinates = testPath.get(1);
        assertEquals(49.199658, firstCoordinates.getLatitude(), TOL);
        assertEquals(-122.949611, firstCoordinates.getLongitude(), TOL);
        assertEquals(49.199405, secondCoordinates.getLatitude(), TOL);
        assertEquals(-122.950261, secondCoordinates.getLongitude(), TOL);
    }

    //TestRoute1:
    //NC41-EB1;49.224353;-122.694752;49.224357;-122.690832;49.224419;-122.690441;49.224579;-122.689931;
    //TestRoute2:
    //N100-W1-71;49.199658;-122.949611;49.199405;-122.950261;49.19956;-122.950457;49.198501;-122.952413;
    @Test
    public void testRouteParserMultiplePoints() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route testRoute1 = RouteManager.getInstance().getRouteWithNumber("NC41");
        RoutePattern testPattern1 = testRoute1.getPattern("EB1");
        assertEquals("EB1", testPattern1.getName());
        List<LatLon> testPath1 = testPattern1.getPath();
        assertEquals(49, testPath1.size());
        LatLon firstCoordinate1 = testPath1.get(0);
        assertEquals(49.224353, firstCoordinate1.getLatitude(), TOL);
        assertEquals(-122.694752, firstCoordinate1.getLongitude(), TOL);

        Route testRoute2 = RouteManager.getInstance().getRouteWithNumber("N100");
        assertEquals("N100", testRoute2.getNumber());
        RoutePattern testPattern2 = testRoute2.getPattern("W1-71");
        List<LatLon> testPath2 = testPattern2.getPath();
        assertEquals(214, testPath2.size());
        LatLon firstCoordinate2 = testPath2.get(0);
        assertEquals(49.199658, firstCoordinate2.getLatitude(), TOL);
        assertEquals(-122.949611, firstCoordinate2.getLongitude(), TOL);
    }
}
