package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Arrivals
 */
public class ArrivalsTest {
    Route r;
    Arrival a;

    @Before
    public void setup() {
        r = RouteManager.getInstance().getRouteWithNumber("43");
        a = new Arrival(23, "Home", r);
    }

    @Test
    public void testConstructor() {
        assertEquals(23, a.getTimeToStopInMins());
        assertEquals(r, a.getRoute());
    }

    @Test
    public void testGetterFunctions() {
        assertEquals("Home", a.getDestination());
        assertEquals(" ", a.getStatus());
    }

    @Test
    public void testCompareTo() {
        Route r2 = RouteManager.getInstance().getRouteWithNumber("20");
        Arrival a2 = new Arrival (10, "Store", r2);
        assertEquals(13, a.compareTo(a2));
    }

    @Test
    public void testSetStatus() {
        assertEquals(" ", a.getStatus());
        a.setStatus("+");
        assertEquals("+", a.getStatus());
    }
}
