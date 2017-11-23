import org.junit.Test;

import static org.junit.Assert.*;

public class WIFISampleTest {

    //WIFISample wifiSample = new WIFISample("a","b","c","d","e","f","g","h","i","j");

    @Test
    public void testToString() throws Exception {
        WIFISample wifiSample = new WIFISample("a","b","c","d","e","f","g","h","i","j");
        assertEquals("a,b,c,e", wifiSample.toString());
    }

}