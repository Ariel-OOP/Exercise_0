import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nissan on 11/22/2017.
 */
public class KmlExporterTest {
    @Before
    public void setUp() throws Exception {
        Filter filterLocation = new Filter(1);
        filterLocation.setFilter("32.009,43.99 55");

        Filter filterTime = new Filter(2);
        Filter filterId = new Filter(3);
    }

    @Test
    public void csvToKml() throws Exception {

    }

}