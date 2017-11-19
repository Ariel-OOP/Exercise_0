import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OutputCSVWriter {
	
	List<WIFISample> wigleList = new ArrayList<>();
	String outputPath;

	/**
	 * 
	 * @param outputPath the file destination to output the file and name
	 * @param wigleList the list of all wifi points which has been created
	 */
	public OutputCSVWriter(String outputPath,List<WIFISample> wigleList) {
		this.wigleList = wigleList;
		this.outputPath = outputPath;
		
		//Deletes file if it exists
		File fileToDelete = new File(outputPath+".csv");
		fileToDelete.delete();
	}
	
	//return boolean if successful
	public boolean createCSV() {
	
		//Sorting 10 best wifi signals
		Collections.sort(wigleList, new Comparator<WIFISample>() {
			@Override
			public int compare(WIFISample wifi1, WIFISample wifi2) {
				return wifi1.getWIFI_RSSI().compareTo(wifi2.getWIFI_RSSI());
			}
		});
		
		
		for(WIFISample wifiSamp : wigleList) {
			System.out.println(wifiSamp.toString());
		}
		
		//TODO change return
		return true;
		//fill matrix from list

//
//		strMatrix = new String[lineCounter][COLUMNS]; //Makes string matrix for wifi parameters
//		strMatrix = PointsOfOneMinute.toArray(new String[lineCounter][11]);

	}
	
	
	
	
	
	
	
	
}
