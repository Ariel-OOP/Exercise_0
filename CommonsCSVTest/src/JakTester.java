import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;

/**
 * Created by nissan on 11/17/2017.
 */
public class JakTester {
    public static void main(String[] args) {
        String[] strMat = {"nissan","WIFIOpen","9/9/9 13:34"};
        String description = "<name><![CDATA[" + strMat[0] + "]]></name>\n"
                + "<description><![CDATA[#WiFi networks: <b>" + strMat[2] + "</b>\n<br>Date: " + strMat[1]
                + "<table  border=\"1\" style=\"font-size:12px;\"> "
                + "<tr>\r\n" +
                "<td><b>Name</b></td>\r\n" +
                "<td><b>BSSID</b></td>\r\n" +
                "<td><b>Frequency</b></td>\r\n" +
                "<td><b>Signal</b></td>\r\n" +
                "</tr>"
                +"<tr>\r\n" + "<td>" + strMat[1] + "</td>\r\n" + "<td>" + strMat[2]
                + "</td>\r\n" + "<td>" + strMat[0] + "</td>\r\n" + "<td>" + strMat[2]
                + " </td>\r\n" + "</tr>";


        final Kml kml = new Kml();
        Document document = kml.createAndSetDocument();

        document.createAndAddPlacemark().withId("Wifi 1").withDescription(description)
                .createAndSetPoint().addToCoordinates(-20.3978398, -43.5146653);

        document.createAndAddPlacemark().withId("Wifi 2").withDescription(description)
                .createAndSetPoint().addToCoordinates(-20.388, -43.52);

        kml.setFeature(document);
        try {
            kml.marshal(new File("HelloKML.kml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
