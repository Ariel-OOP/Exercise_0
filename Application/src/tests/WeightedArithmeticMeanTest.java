import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nissan on 12/4/2017.
 */
public class WeightedArithmeticMeanTest {

    @Test
    public void getWAMTest() throws Exception {
        WeightedArithmeticMean weightedArithmeticMean = new WeightedArithmeticMean("00:22:b0:75:7d:eb");
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Lat(), 32.16800543);
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Lon(), 34.80451717);
        assertEquals(weightedArithmeticMean.getWAM().getWIFI_Alt(),  33.01544196);
    }
}