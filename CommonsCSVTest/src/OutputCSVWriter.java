import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OutputCSVWriter {
	
	String inputFolder;
	String outputPath;

	/**
	 * 
	 * @param outputPath the file destination to output the file and name
	 * @param wigleList the list of all wifi points which has been created
	 */
	public OutputCSVWriter(String inputFolder,String outputPath) {
		this.inputFolder = inputFolder;
		this.outputPath = outputPath;
		
		//Deletes file if it exists
		File fileToDelete = new File(outputPath+".csv");
		fileToDelete.delete();
	}
	
	//return boolean if successful
	public void createCSV() {
		List<WIFISample> allSortedPoints = new ArrayList<>(); //from all the files together
		File dir = new File(inputFolder);		//	The current file
		for (File file : dir.listFiles()) {

			WigleFileReader wigleFileReader;
			
				//Incorrect file type-reject
				if (!(file.getName().toLowerCase().endsWith(".csv"))){
					System.out.println(file.getName()+" is an incorrect file type in the folder");
					System.out.println("the file was not added to the csv file error 404");
					continue;
				}
				wigleFileReader = new WigleFileReader(file.getName());
				wigleFileReader.readCsvFile();
				allSortedPoints.addAll(wigleFileReader.getWigleList());		
		}
		

	}
	
	
	
	
	
	
	
	
}
