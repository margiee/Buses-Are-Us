package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the StopParser
 */

// TODO: Write more tests

public class StopParserTest {
    @Before
    public void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testStopParserNormal() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();
        assertEquals(8524, StopManager.getInstance().getNumStops());
    }

    @Test
    public void testStopParser() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();

/*            "Name": "WB DAVIE ST FS BIDWELL ST",
                "AtStreet": "BIDWELL ST",
                "WheelchairAccess": 1,
                "BayNo": "N",
                "Latitude": 49.28646,
                "Longitude": -123.14043,
                "StopNo": 50001,
                "OnStreet": "DAVIE ST",
                "Routes": "C23",
                "City": "VANCOUVER"*/

        Stop testStop = StopManager.getInstance().getStopWithNumber(50001);
        assertEquals("WB DAVIE ST FS BIDWELL ST", testStop.getName());
        Set<Route> routes = testStop.getRoutes();
        assertFalse(routes.isEmpty());
        LatLon testLocation = new LatLon(49.28646, -123.14043);
        assertEquals(testLocation, testStop.getLocn());
    }
}
