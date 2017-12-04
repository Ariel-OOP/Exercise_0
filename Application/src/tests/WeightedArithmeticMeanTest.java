import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nissan on 12/4/2017.
 */
public class WeightedArithmeticMeanTest {

    @Test
    public void getWAMTest() throws Exception {
        WeightedArithmeticMean weightedArithmeticMean = new WeightedArithmeticMean();
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Lat(),   );
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Lon(),   );
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Alt(),   );
    }
}