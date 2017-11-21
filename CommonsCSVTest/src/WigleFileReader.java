package CommonsCSVTest.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author moshe, nissan

 */
public class WigleFileReader {

	//CSV file header
	private  final String [] FILE_HEADER_MAPPING = {"MAC","SSID","AuthMode","FirstSeen","Channel","RSSI","CurrentLatitude","CurrentLongitude","AltitudeMeters","AccuracyMeters","Type"};
	//A list of all wifi points
	private List<WIFISample> allWifiPoints; 
	//WIFI attributes
	private final String WIFI_MAC = "MAC";
	private final String WIFI_SSID = "SSID";
	private final String WIFI_AuthMode = "AuthMode";
	private final String WIFI_FirstSeen = "FirstSeen"; 
	private final String WIFI_Channel = "Channel";
	private final String WIFI_RSSI = "RSSI";
	private final String WIFI_Lat = "CurrentLatitude";
	private final String WIFI_Lon = "CurrentLongitude";
	private final String WIFI_Alt = "AltitudeMeters"; 
	private final String WIFI_Accuracy = "AccuracyMeters";
	private final String WIFI_Type = "Type";

	private String[] firstLine_DeviceAttributes = {};

	private String fileName = "";

	/**
	 * 
	 * @param fileName
	 */
	public WigleFileReader(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * readCsvFile reads a wigle file and will automatically sort the file file by time,location and RSSI
	 */
	public void readCsvFile() {

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		//Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		try {

			//Create a new list of wifi points to be filled by CSV file data
			//each element is a WIFi sample
			allWifiPoints = new ArrayList();

			//initialize FileReader object
			fileReader = new FileReader(fileName);

			//Read line of device attributes
			BufferedReader inStream = new BufferedReader(fileReader);
			firstLine_DeviceAttributes = inStream.readLine().split(",");

			//initialize CSVParser object
			csvFileParser = new CSVParser(inStream, csvFileFormat);

			//Get a list of CSV file records
			List csvRecords = csvFileParser.getRecords(); 

			//Read the CSV file records starting from the second record to skip the header
			CSVRecord record = (CSVRecord) csvRecords.get(0);

			WIFISample wifiSample = new WIFISample(record.get(WIFI_MAC),record.get(WIFI_SSID),record.get(WIFI_FirstSeen),
					record.get(WIFI_Channel),record.get(WIFI_RSSI),record.get(WIFI_Lat),record.get(WIFI_Lon),
					record.get(WIFI_Alt),record.get(WIFI_Type));

			allWifiPoints.add(wifiSample);
			WIFISample prev = allWifiPoints.get(0); 
			List<WIFISample> PointsOfOneMinute = new ArrayList<>();

			for (int i = 2; i < csvRecords.size(); i++) {
				record = (CSVRecord) csvRecords.get(i);
				//Create a new WIFISample object and fill his data


				wifiSample = new WIFISample(record.get(WIFI_MAC),record.get(WIFI_SSID),record.get(WIFI_FirstSeen),
						record.get(WIFI_Channel),record.get(WIFI_RSSI),record.get(WIFI_Lat),record.get(WIFI_Lon),
						record.get(WIFI_Alt),record.get(WIFI_Type));

				//Compares the previous lan, lat and time to the current WIFI sample // Mybe add Compares alt
				if (wifiSample.getWIFI_Lat().equals(prev.getWIFI_Lat()) && wifiSample.getWIFI_Lon().equals(prev.getWIFI_Lon()) && wifiSample.getWIFI_FirstSeen().equals(prev.getWIFI_FirstSeen()) ) {
					PointsOfOneMinute.add(wifiSample);
				}else {
					//Sorting 10 best wifi signals

					Collections.sort(PointsOfOneMinute, new Comparator<WIFISample>() {
						@Override
						public int compare(WIFISample wifi1, WIFISample wifi2) {
							return wifi1.getWIFI_RSSI().compareTo(wifi2.getWIFI_RSSI());
						}
					});

					int tenOrLessPoints=0;
					for(WIFISample wifiSamp : PointsOfOneMinute) {
						if (tenOrLessPoints<=10) {
							allWifiPoints.add(wifiSamp);
							tenOrLessPoints++;
						}
					}
					prev = wifiSample;
					PointsOfOneMinute.clear();
					PointsOfOneMinute.add(wifiSample);		
				}

			}

			//Print the new wifi list
			for (WIFISample wifiSamp : allWifiPoints) {
				System.out.println(wifiSamp.toString() +"\nSignal: "+wifiSamp.getWIFI_RSSI() +" Time:"+wifiSamp.getWIFI_FirstSeen() );
				System.out.println("\n device:"+ wifiSamp.getWIFI_Device());
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

	/*
	 * getWigleList will return the final sorted list of wifi includes more than 10
	 */
	public List<WIFISample> getWigleList() {
		return allWifiPoints;
	}

}