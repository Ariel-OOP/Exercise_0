
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;



public class OutputCSVWriter {

	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	//CSV file header
	private static final Object [] FILE_HEADER = {"Time","ID","Lat","Lon","Alt","#WiFi networks","SSID1","MAC1","Frequncy1","Signal1",
			"SSID2","MAC2","Frequncy2","Signal2","SSID3","MAC3","Frequncy3","Signal3","SSID4","MAC4","Frequncy4","Signal4",
			"SSID5","MAC5","Frequncy5","Signal5","SSID6","MAC6","Frequncy6","Signal6","SSID7","MAC7","Frequncy7","Signal7",
			"SSID8","MAC8","Frequncy8","Signal8","SSID9","MAC9","Frequncy9","Signal9","SSID10","MAC10","Frequncy10","Signal10"};

	String files;
	File dir;

	String outputPath;
  
	/**
	 * 
	 * @param outputPath the file destination to output the file and name
	 * @param wigleList the list of all wifi points which has been created
	 */

	public OutputCSVWriter(String files,String outputPath) {
		this.files = files;
		this.outputPath = outputPath;
		dir = new File(files);		//	The current file


		//Deletes file if it exists
		File fileToDelete = new File(outputPath+".csv");
		fileToDelete.delete();
	}
	public List<List<LineOfFinalCSV>> sortAndMergeFiles() {
		List<LineOfFinalCSV> allSortedPoints = new ArrayList<>(); //from all the files together
		List<List<LineOfFinalCSV>> processedFile = new ArrayList<>();
		WigleFileReader wigleFileReader;

		for (File file : dir.listFiles()) {	

			//Incorrect file type-reject
			if (!(file.getName().toLowerCase().endsWith(".csv"))){
				System.out.println(file.getName()+" is an incorrect file type in the folder");
				System.out.println("the file was not added to the csv file error 404");
				//continue;
			}
			else {
				wigleFileReader = new WigleFileReader(file.getPath());
				wigleFileReader.readCsvFile();
				allSortedPoints = wigleFileReader.getWigleList();
				processedFile.add(allSortedPoints);
			}
		}
		return processedFile;
	}

	public void ExportToCSV(List<List<LineOfFinalCSV>> fileAfterSortintAndMerging) {

		FileWriter fileWriter = null;

		CSVPrinter csvFilePrinter = null;

		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			//initialize FileWriter object
			fileWriter = new FileWriter(outputPath);

			//initialize CSVPrinter object 
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			//Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);

			//Write a new student object list to the CSV file

			for (List<LineOfFinalCSV> file : fileAfterSortintAndMerging) {
				for(LineOfFinalCSV line : file)
					csvFilePrinter.printRecord(line.getWifiPoints());
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}
	}
}
