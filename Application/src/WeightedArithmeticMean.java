import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nissan on 12/3/2017.
 */

public class WeightedArithmeticMean {

    private String mac;
    private final int k=3;

    public WeightedArithmeticMean(String mac) {
        this.mac = mac;
    }

    /**
     *
     * @return returns a WIFIWeight object which contains the Weighted Arithmetic Mean of the given mac in the constructor
     */
    public WIFIWeight getWAM() {
        HashRouters<String, WIFISample> HashRouters = new HashRouters();
        List<WIFISample> kMacList = getKBest(mac,k);
        //TODO: change WIFISamples to doubles,int etc....
        List<WIFIWeight> wifiWeights = new ArrayList<WIFIWeight>();
        //final sum
        WIFIWeight sum,wSum;

        for(WIFISample ws: kMacList){
            double weightOfOne = Double.parseDouble(ws.getWIFI_RSSI());
            weightOfOne= 1/(weightOfOne*weightOfOne);
            wifiWeights.add(new WIFIWeight( ws.getWIFI_MAC()
                    ,calcWeight(ws.getWIFI_Lat(),weightOfOne )
                    ,calcWeight(ws.getWIFI_Lon(),weightOfOne )
                    ,calcWeight(ws.getWIFI_Alt(),weightOfOne)
                    ,Integer.parseInt(ws.getWIFI_RSSI())
                    ,weightOfOne ));
        }

        sum = new WIFIWeight(wifiWeights.get(0).getWIFI_MAC() ,0.0,0.0,0.0,0,0.0);

        for(WIFIWeight ww: wifiWeights){
            sum.setWIFI_Lat(  ww.getWIFI_Lat()+sum.getWIFI_Lat()   );
            sum.setWIFI_Lon(  ww.getWIFI_Lon()+sum.getWIFI_Lon()   );
            sum.setWIFI_Alt(  ww.getWIFI_Alt()+sum.getWIFI_Alt()  );
            sum.setWIFI_Weight( ww.getWIFI_Weight()+sum.getWIFI_Weight() );
        }

        wSum = new WIFIWeight(sum.getWIFI_MAC(),sum.getWIFI_Lat()*sum.getWIFI_Weight()
                ,sum.getWIFI_Lon()*sum.getWIFI_Weight()
                ,sum.getWIFI_Alt()*sum.getWIFI_Weight(),0,0.0);

        return wSum;

    }
    private double calcWeight(String str,Double weight){
        return  Double.parseDouble(str)*weight ;
    }

}

