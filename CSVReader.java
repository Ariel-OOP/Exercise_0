import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

	String folderPath;
	String outputFileName;
	final int COLUMNS = 11;					// The number of columns
//	File dir = new File(folderPath);		//	The current file
	boolean firstTimeHeader = true; 		//	Flag to check if the titles of columns in the csv file was written		
	int lineCounter=0;						//	Number of strMatrix's lines
	String[][] strMatrix; 					//	Matrix of parameters from txt file
	//Scanner stdin;							//
	String line_Text;						//	
	String[][] headerMatrix;				//	Array of two lines of file's header 
	String[] OnePoint = null;				//	Array that given from split(",") on each line of csv file
//	String CurrentTime;						//	The time of a WIFI point
	List<String[]> PointsOfOneMinute = new ArrayList<String[]>();	//	List of all WIFI points in given time


	/**
	 * @param folderPathInput ex. "C:\\Users\\Moshe\\Desktop\\data\\27.10\\Lenovo"
	 * @param outputFileName the name of the file to output to csv format ex. wifiStrength 
	 */
	public CSVReader(String folderPathInput,String outputFileName) {

		folderPath = folderPathInput;
		this.outputFileName = outputFileName;
	
		//Delete old csv file if exists
		File fileToDelete = new File(outputFileName+".csv");
		fileToDelete.delete();
		//Read the folder
		ReadFilesInFolder();		
	}

	/**
	 * reads the given folder given in the constructor and reads through the files
	 */
	public void ReadFilesInFolder(){
		//Look through files in current folder
		
		Scanner stdin;							//
		File dir = new File(folderPath);		//	The current file
		for (File file : dir.listFiles()) {

			stdin = null;	//Gave null so it would be initialized

			try {
				//Incorrect file type-reject
				if (!(file.getName().toLowerCase().endsWith(".csv"))){
					System.out.println(file.getName()+" is an incorrect file type in the folder");
					System.out.println("the file was not added to the csv file");
					continue;
				}

				stdin = new Scanner(file);//Read the text from current file
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//TODO I hope it goes here
			ReadHeaderAndData(stdin);
		}
	}

	/**
	 * 
	 * @param stdin
	 */
	public void ReadHeaderAndData(Scanner stdin){
		
		headerMatrix = new String[2][COLUMNS];
		boolean hasCorrectHeader = true;	//If the method success to read the header and title than /*return true, else return false.*/
		Line header = null, title = null;
		//Split each line of csv file by ',' and insert to array

		if(lineCounter >= 0)
		{//if missing part or all from title, this file is invalid and the program continue to the next file in the folder
			if(stdin.hasNext())
			{
				line_Text = stdin.nextLine();
				headerMatrix[0] = line_Text.split(",");
				header = new Line(line_Text);
			}
			else
				hasCorrectHeader = false;
			if(stdin.hasNext())
			{
				line_Text = stdin.nextLine();
				headerMatrix[1] = line_Text.split(",");
				title = new Line(line_Text);
			}
			else
				hasCorrectHeader = false;
		}
		//check if in the title has all properties
		
		if(title != null && header != null && title.checkTitle() && header.checkHeader())
			hasCorrectHeader = true;
		else
			hasCorrectHeader = false;
		
		if (hasCorrectHeader) {
			FillAndSortMatrix(stdin);
		}
		else
			System.out.println("the file is corrupt");
	}

	/**
	 * 
	 */
	public void FillAndSortMatrix(Scanner stdin){

		String currentLat = "", currentLon = "", currentAlt = "";
		String currentTime;						//	The time of a WIFI point
		
		if(stdin.hasNext())
		{
			//take the first line that is not title of the file.
			OnePoint = stdin.nextLine().split(",");		
		}

		while (stdin.hasNext()) {
			lineCounter = 0;
			//Iterate through all the lines of the file

			PointsOfOneMinute = new ArrayList<String[]>();
			currentTime = OnePoint[3]; // the time property located 
			currentLat = OnePoint[6];
			currentLon = OnePoint[7];
			currentAlt = OnePoint[8];
			
			PointsOfOneMinute.add(OnePoint);
			lineCounter++;

			while (stdin.hasNext()) {
				
				OnePoint = stdin.nextLine().split(",");
				if(CheckIfInOneLat(currentLat,currentLon,currentAlt,currentTime,OnePoint))
				{
					//Look for number of lines in current minute
					lineCounter++;
					PointsOfOneMinute.add(OnePoint);		
				}
				else
					break;	
			} 


			//Sorting 10 best wifi signals
			Collections.sort(PointsOfOneMinute, new Comparator<String[]>() {
				@Override
				public int compare(final String[] entry1, final String[] entry2) {
					//TODO To copy source from internet
					final String rxl1 = entry1[5];
					final String rxl2 = entry2[5];
					return rxl1.compareTo(rxl2);
				}
			});
			//fill matrix from list
			
			strMatrix = new String[lineCounter][COLUMNS]; //Makes string matrix for wifi parameters
			strMatrix = PointsOfOneMinute.toArray(new String[lineCounter][11]);
			
			FileCreater();
		}
	}

	/**
	 * 
	 */
	public void FileCreater(){

		try(FileWriter fw = new FileWriter(outputFileName + ".csv", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)){

			String my_new_str = "";
			if (firstTimeHeader) {
				//Print the titles of the new csv file
				String headStr = "Time,ID,Lat,Lon,Alt,#WiFi networks";
				for(int j = 0; j < 10; j++)
				{
					headStr += ",SSID" + (j+1) + ",MAC" + (j+1) + ",Frequncy" + (j+1) + ",Signal" + (j+1);
				}
				out.println(headStr);
				firstTimeHeader = false;
			}

			my_new_str += strMatrix[0][3] +"," + headerMatrix[0][5]+ ","+ strMatrix[0][6] + "," + strMatrix[0][7] + "," + strMatrix[0][8] + "," + ((10 > strMatrix.length) ? strMatrix.length : 10 )+ "," ;

			int counter_Column_Csv_Final = 0, j=0;

			while (counter_Column_Csv_Final < 10 && j < strMatrix.length) {
				if (!strMatrix[j][COLUMNS-1].contains("GSM")) {
					my_new_str += strMatrix[j][1] + "," + strMatrix[j][0] + "," + strMatrix[j][4] + "," + strMatrix[j][5] + ",";
					counter_Column_Csv_Final++;
				} 
				j++;
			}
			out.println(my_new_str);

			lineCounter=0;
		} catch (IOException e) {
			//exception handling left as an exercise for the reader
		}

		PointsOfOneMinute = null;
		lineCounter = 0;	
	}

	private boolean CheckIfInOneLat(String currentLat, String currentLon, String currentAlt, String currentTime, String[] line) {
		
		//String[] arrayLine = line.split(",");
		
		boolean latIsEqual = CheckIfInOneLat(currentLat,line[6]);
		boolean lonIsEqual = CheckIfInOneLon(currentLon,line[7]);
		boolean altIsEqual = CheckIfInOneAlt(currentAlt,line[8]);;
		boolean timesIsEqual = CheckIfInOneLon(currentTime,line[3]);

		return latIsEqual && lonIsEqual && altIsEqual && timesIsEqual;
	}

	private boolean CheckIfInOneLat(String currentLat, String string) {
		boolean latIsEqual;

		latIsEqual = currentLat.equals(string);

		return latIsEqual;
	}
	
	private boolean CheckIfInOneLon(String currentLon, String string) {
		boolean lonIsEqual;

		lonIsEqual = currentLon.equals(string);

		return lonIsEqual;
	}

	private boolean CheckIfInOneAlt(String currentAlt, String string) {
		boolean altIsEqual;

		altIsEqual = currentAlt.equals(string);

		return altIsEqual;
	}
	
	/**
	 * 
	 * @param currentTime
	 * @param string
	 * @return
	 */
	private boolean CheckIfInOneTime(String currentTime, String string) {

		boolean timesIsEqual;

		String[] Time1 = currentTime.split(":");
		String[] Time2 = string.split(":");

		timesIsEqual = Time1[0].equals(Time2[0]) && Time1[1].equals(Time2[1]);

		return timesIsEqual;
	}



//	/**
//	 * 
//	 * @param headerMatrix
//	 * @return
//	 */
//	public static boolean fileVerifier(String[][] header_Matrix)
//	{
//		boolean correct_Header = true;
//
//		if(!(header_Matrix[0][0].contains("WigleWifi") && 
//				header_Matrix[0][1].contains("appRelease") &&
//				header_Matrix[0][2].contains("model") &&
//				header_Matrix[0][3].contains("release") &&
//				header_Matrix[0][4].contains("device") &&
//				header_Matrix[0][5].contains("display") &&
//				header_Matrix[0][6].contains("board") &&
//				header_Matrix[0][7].contains("brand")))
//		{
//			correct_Header = false;
//		}
//
//		if(!(header_Matrix[1][0].equals("MAC") && 
//				header_Matrix[1][1].equals("SSID") &&
//				header_Matrix[1][2].equals("AuthMode") &&
//				header_Matrix[1][3].equals("FirstSeen") &&
//				header_Matrix[1][4].equals("Channel") &&
//				header_Matrix[1][5].equals("RSSI") &&
//				header_Matrix[1][6].equals("CurrentLatitude") &&
//				header_Matrix[1][7].equals("CurrentLongitude") &&
//				header_Matrix[1][8].equals("AltitudeMeters") &&
//				header_Matrix[1][9].equals("AccuracyMeters") &&
//				header_Matrix[1][10].equals("Type")))
//		{
//			correct_Header = false;
//		}
//
//		return correct_Header;
//	}

}


