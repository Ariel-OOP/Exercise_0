
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author ashraf_sarhan
 *
 */
public class CsvFileReader {
	
	//CSV file header
    private static final String [] FILE_HEADER_MAPPING = {"MAC","SSID","AuthMode","FirstSeen","Channel","RSSI","CurrentLatitude","CurrentLongitude","AltitudeMeters","AccuracyMeters","Type"};
	
	//WIFI attributes
	private static final String WIFI_MAC = "MAC";
	private static final String WIFI_SSID = "SSID";
	private static final String WIFI_AuthMode = "AuthMode";
	private static final String WIFI_FirstSeen = "FirstSeen"; 
	private static final String WIFI_Channel = "Channel";
	private static final String WIFI_RSSI = "RSSI";
	private static final String WIFI_Lat = "CurrentLatitude";
	private static final String WIFI_Lon = "CurrentLongitude";
	private static final String WIFI_Alt = "AltitudeMeters"; 
	private static final String WIFI_Accuracy = "AccuracyMeters";
	private static final String WIFI_Type = "Type";
	
	private static String[] firstLine_DeviceAttributes = {};
	
	public static void readCsvFile(String fileName) {

		FileReader fileReader = null;
		
		CSVParser csvFileParser = null;
		
		//Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
     
        try {
        	
        	//Create a new list of student to be filled by CSV file data 
        	List<WIFISample> allWifiPoints = new ArrayList();
            
            //initialize FileReader object
            fileReader = new FileReader(fileName);
            
            BufferedReader inStream = new BufferedReader(fileReader);
            firstLine_DeviceAttributes = inStream.readLine().split(",");
            
            //initialize CSVParser object
            csvFileParser = new CSVParser(inStream, csvFileFormat);
            
            //Get a list of CSV file records
            List csvRecords = csvFileParser.getRecords(); 
            
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
            	CSVRecord record = (CSVRecord) csvRecords.get(i);
            	//Create a new WIFISample object and fill his data
            	WIFISample wifiSample = new WIFISample(record.get(WIFI_MAC),record.get(WIFI_SSID),record.get(WIFI_FirstSeen),
            			record.get(WIFI_Channel),record.get(WIFI_RSSI),record.get(WIFI_Lat),record.get(WIFI_Lon),
            			record.get(WIFI_Alt),record.get(WIFI_Type));
            	
                allWifiPoints.add(wifiSample);	
			}
            
            //Print the new student list
            for (WIFISample wifiSamp : allWifiPoints) {
				System.out.println(wifiSamp.toString());
			}
        } 
        catch (Exception e) {
        	System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
            	System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }

	}

}