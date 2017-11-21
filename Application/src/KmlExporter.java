
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

import java.io.*;

/**
 * Created by nissan on 11/17/2017.
 */
public class KmlExporter {
    String csvFilePath, outKmlPathAndName;
    Kml kml;
    Document document;

    /**
     *
     * @param csvFilePath the csv file, which is the source for our export
     * @param outKmlPathAndName the destination of the kml path and name to export
     */
    public KmlExporter(String csvFilePath, String outKmlPathAndName) {
        this.csvFilePath = csvFilePath;
        this.outKmlPathAndName = outKmlPathAndName;
    }

    public boolean csvToKml(Filter filter) {
        kml = new Kml();
        document = kml.createAndSetDocument();
        //TODO see if this helps kml header

        BufferedReader br = null;
        String line;
        String[] lineInformation;

        try {
            br = new BufferedReader(new FileReader(csvFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            br.readLine();//Throws away the header
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                lineInformation = line.split(",");//Split each line to parts of properties
                if (filter.checkLineByFilter(lineInformation)) {
                    printPointOnMap(lineInformation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        kml.setFeature(document);

        try {
            kml.marshal(new File(outKmlPathAndName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false; //unsuccessful write
        }
        return true; //successful write

    }
        private void printPointOnMap(String[] CSVLine) {
            String onePoint = "";
            onePoint ="<name><![CDATA[" + CSVLine[0] + "]]></name>"
                    + "<description><![CDATA[#WiFi networks: <b>" + CSVLine[5] + "</b>\n<br>Date: " + CSVLine[0]
                    + "<table  border=\"1\" style=\"font-size:12px;\"> "
                    + "<tr>" +
                    "<td><b>Name</b></td>" +
                    "<td><b>BSSID</b></td>" +
                    "<td><b>Frequency</b></td>" +
                    "<td><b>Signal</b></td>" +
                    "</tr>";

            for (int i = 0; i < Integer.parseInt(CSVLine[5]); i++)
            {//write the property to format of kml
                try {
                    onePoint += "<tr>" + "<td>" + CSVLine[6 + 4 * i] + "</td>" + "<td>" + CSVLine[7 + 4 * i]
                            + "</td>" + "<td>" + CSVLine[8 + 4 * i] + "</td>" + "<td>" + CSVLine[9 + 4 * i]
                            + " </td>" + "</tr>";
                } catch (Exception e) {
                    System.out.println(i + "i,  Integer.parseInt(CSVLine[5]): " + Integer.parseInt(CSVLine[5]) + ",   CSVLine[6 + 4 * i]" + CSVLine[6 + 4 * (i-1)] + ",    Time:   " + CSVLine[0]);
                }
            }
            onePoint += "</table>"
//                    + "]]>"
                   + "</description>";
//                    + "<styleUrl>#"
//                    + theColorOfPoint(CSVLine[9])
//                    + "</styleUrl>"
//                    + "<Point>"
//                    + "<coordinates>"
//                    + CSVLine[3] +","
//                    + CSVLine[2]
//                    + "</coordinates>"
//                    + "</coordinates>"
//                    + "</Point>";


            //TODO test if withId is null wht happens
            document.createAndAddPlacemark().withId(CSVLine[7])
//                    .withStyleUrl("http://maps.google.com/mapfiles/ms/icons/green-dot.png")
                    .withDescription(onePoint)
                    .createAndSetPoint().addToCoordinates(Double.parseDouble(CSVLine[3]),Double.parseDouble(CSVLine[2] ));
    }



//private static String theColorOfPoint(String str) {
//
//    int RXLnumber = Integer.parseInt(str);
//    String color = "";
//
//    if(RXLnumber <= 0 && RXLnumber > -70)
//    {
//        color = "green";
//    }
//    else if(RXLnumber <= -70 && RXLnumber > -80)
//    {
//        color = "yellow";
//    }
//    else
//        color = "red";
//
//
//    return color;
//}

}
