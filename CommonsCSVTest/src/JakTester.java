package CommonsCSVTest.src;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.text.Normalizer;

/**
 * Created by nissan on 11/17/2017.
 */
public class JakTester {
    String csvFilePath, outKmlPathAndName;
    Kml kml;
    Document document;

    public JakTester(String csvFilePath, String outKmlPathAndName) {
        this.csvFilePath = csvFilePath;
        this.outKmlPathAndName = outKmlPathAndName;
    }

    public boolean csvToKml(Filter filter) {
        kml = new Kml();
        document = kml.createAndSetDocument();

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
            kml.marshal(new File("HelloKML.kml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false; //unsuccessful write
        }
        return true; //successful write

    }
        private void printPointOnMap(String[] CSVLine) {
            String onePoint = "";
            onePoint ="<name><![CDATA[" + CSVLine[0] + "]]></name>\n"
                    + "<description><![CDATA[#WiFi networks: <b>" + CSVLine[5] + "</b>\n<br>Date: " + CSVLine[0]
                    + "<table  border=\"1\" style=\"font-size:12px;\"> "
                    + "<tr>\r\n" +
                    "<td><b>Name</b></td>\r\n" +
                    "<td><b>BSSID</b></td>\r\n" +
                    "<td><b>Frequency</b></td>\r\n" +
                    "<td><b>Signal</b></td>\r\n" +
                    "</tr>";

            for (int i = 0; i < Integer.parseInt(CSVLine[5]); i++)
            {//write the property to format of kml
                try {
                    onePoint += "<tr>\r\n" + "<td>" + CSVLine[6 + 4 * i] + "</td>\r\n" + "<td>" + CSVLine[7 + 4 * i]
                            + "</td>\r\n" + "<td>" + CSVLine[8 + 4 * i] + "</td>\r\n" + "<td>" + CSVLine[9 + 4 * i]
                            + " </td>\r\n" + "</tr>";
                } catch (Exception e) {
                    System.out.println(i + "i,  Integer.parseInt(CSVLine[5]): " + Integer.parseInt(CSVLine[5]) + ",   CSVLine[6 + 4 * i]" + CSVLine[6 + 4 * (i-1)] + ",    Time:   " + CSVLine[0]);
                }
            }
            onePoint += "</table>\r\n"
                    + "]]></description><styleUrl>#"
                  //TODO:  + theColorOfPoint(CSVLine[9])
                    + "</styleUrl>\r\n"
                    + "<Point>\r\n"
                    + "<coordinates>"
                    + CSVLine[3] +","
                    + CSVLine[2]
                    + "</coordinates>\r\n"
                    + "\r\n"
                    + "</Point>\r\n";

            document.createAndAddPlacemark().withId("Wifi 1").withDescription(onePoint)
                    .createAndSetPoint().addToCoordinates(-20.3978398, -43.5146653);
    }


//    public static void main(String[] args) {
//        String csvPath = "finalOutput.csv";
//
//        String[] strMat = {"nissan","WIFIOpen","9/9/9 13:34"};
//        String description = "<name><![CDATA[" + strMat[0] + "]]></name>\n"
//                + "<description><![CDATA[#WiFi networks: <b>" + strMat[2] + "</b>\n<br>Date: " + strMat[1]
//                + "<table  border=\"1\" style=\"font-size:12px;\"> "
//                + "<tr>\r\n" +
//                "<td><b>Name</b></td>\r\n" +
//                "<td><b>BSSID</b></td>\r\n" +
//                "<td><b>Frequency</b></td>\r\n" +
//                "<td><b>Signal</b></td>\r\n" +
//                "</tr>"
//                +"<tr>\r\n" + "<td>" + strMat[1] + "</td>\r\n" + "<td>" + strMat[2]
//                + "</td>\r\n" + "<td>" + strMat[0] + "</td>\r\n" + "<td>" + strMat[2]
//                + " </td>\r\n" + "</tr>";
//
//
//        final Kml kml = new Kml();
//        Document document = kml.createAndSetDocument();
//
//        document.createAndAddPlacemark().withId("Wifi 1").withDescription(description)
//                .createAndSetPoint().addToCoordinates(-20.3978398, -43.5146653);
//
//        document.createAndAddPlacemark().withId("Wifi 2").withDescription(description)
//                .createAndSetPoint().addToCoordinates(-20.388, -43.52);
//
//        kml.setFeature(document);
//        try {
//            kml.marshal(new File("HelloKML.kml"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
