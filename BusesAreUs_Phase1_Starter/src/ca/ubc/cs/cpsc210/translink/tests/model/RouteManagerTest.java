package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the RouteManager
 */
public class RouteManagerTest {

    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testBoring() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
    }

    @Test
    public void testRouteManager() {
        // new route is created
        Route testRoute = RouteManager.getInstance().getRouteWithNumber("002", "MACDONALD/DOWNTOWN");
        assertEquals(0, testRoute.getPatterns().size());
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
    }
}
