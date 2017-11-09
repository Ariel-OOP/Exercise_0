import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
//import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * CSVReader class will automatically create a CSV file from one or many CSV files in a folder.The newly created file takes the at most 10 strongest WIFI receptions at 
 * a certain time with the same location.
 * @author nissan,moshe and shir
 * @since 9-11-2017
 */
public class CSVReader {

	private String folderPath;
	private String outputFileName;
	private final int COLUMNS = 11;			// The number of columns
	private boolean firstTimeHeader = true; //	Flag to check if the titles of columns in the csv file was written
	
	String[][] strMatrix; 					//	Matrix of parameters from txt file
//	String[][] headerMatrix;				//	Array of two lines of file's header 
//	List<String[]> PointsOfOneMinute = new ArrayList<String[]>();	//	List of all WIFI points in given time


	/**
	 * The constructor will setup the object and will delete if the file already exists. From there on it will continue automatically and create a new CSV file with the name given.
	 * @param folderPathInput - is the string which is the folder path an ex. "C:\\Users\\USER\\Desktop\\27.10\\Lenovo"
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

		Scanner stdin;							
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
				//Read the text from current file
				stdin = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ReadHeaderAndData(stdin);
		}
	}

	/**
	 * The function checks whether the file header and data itself is not corrupt or out of order which it cannot be read correctly
	 * @param stdin - is the current file being read
	 */
	private void ReadHeaderAndData(Scanner stdin){

		String[][] headerMatrix = new String[2][COLUMNS];				//	Array of two lines of file's header 

		boolean hasCorrectHeader = true;	//If the method success to read the header and title than /*return true, else return false.*/
		Line header = null, title = null;
		String line_Text;						//	

		//Check if there are the header and titles
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

		//Check if in the header and titles there are all properties

		if(title != null && header != null && title.checkTitle() && header.checkHeader())
			hasCorrectHeader = true;
		else
			hasCorrectHeader = false;

		if (hasCorrectHeader) {
			FillAndSortMatrix(stdin,headerMatrix);
		}
		else
			System.out.println("the file is corrupt");
	}

	/**
	 * The method takes the data and fills an List and then sorts it using at the most 10, strongest receptions.
	 * @param stdin file
	 */
	public void FillAndSortMatrix(Scanner stdin,String[][] headerMatrix){

		String currentLat = "", currentLon = "", currentAlt = "";		//	The lat, lon, alt of a WIFI point
		String currentTime;												//	The time of a WIFI point
		int lineCounter = 0;											//	Number of strMatrix's lines (WIFI points in given time and place)
		String[] OnePoint = null;										//	Array that given from split(",") on each line of csv file
		List<String[]> PointsOfOneMinute = new ArrayList<String[]>();	//	List of all WIFI points in given time and place

		if(stdin.hasNext())
		{
			//Take the first line that is not header or title of the file.
			OnePoint = stdin.nextLine().split(",");		
		}

		//Iterate through all the lines of the file
		while (stdin.hasNext()) {
			
			PointsOfOneMinute = null;
			lineCounter = 0;	

			PointsOfOneMinute = new ArrayList<String[]>();
			currentTime = OnePoint[3];	// the time property located 
			currentLat = OnePoint[6];	// the lat property located 
			currentLon = OnePoint[7];	// the lon property located 
			currentAlt = OnePoint[8];	// the alt property located 

			PointsOfOneMinute.add(OnePoint);
			lineCounter++;

			while (stdin.hasNext()) {

				OnePoint = stdin.nextLine().split(",");
				if(CheckSameParameters(currentLat,currentLon,currentAlt,currentTime,OnePoint))
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

			FileCreater(headerMatrix);
		}
	}

	/**
	 * The method creates a appropriate CSV file from either one or more files after the data was sorted in each file.
	 */
	private void FileCreater(String[][] headerMatrix){

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

			//Print the data of the columns number 0-5
			my_new_str += strMatrix[0][3] +"," + headerMatrix[0][5]+ ","+ strMatrix[0][6] + "," + strMatrix[0][7] + "," + strMatrix[0][8] + ",";
			
			String dataPoints = "";
			int counter_Column_Csv_Final = 0, j=0;
							
			while (counter_Column_Csv_Final < 10 && j < strMatrix.length) {			
				if (!strMatrix[j][COLUMNS-1].contains("GSM")) {
					dataPoints += strMatrix[j][1] + "," + strMatrix[j][0] + "," + convertChannelToFrequncy(strMatrix[j][4]) + "," + strMatrix[j][5] + ",";
					counter_Column_Csv_Final++;
				}
				j++;
			}
			my_new_str += ((10 > counter_Column_Csv_Final) ? counter_Column_Csv_Final : 10 )+ "," + dataPoints;
			if (counter_Column_Csv_Final > 0) {//If in this origin csv line has not any point that don't ptint this line to the new csv file
				out.println(my_new_str);
			}

		} catch (IOException e) {
			//exception handling left as an exercise for the reader
		}
	}
	
	/**
	 * the method converts the channel to the corrisponding frequency
	 * @source: Yitzchak Sharon & Meirav
	 * @param string - which is the channel
	 * @return String - which is the frequency in mhz
	 */
	private String convertChannelToFrequncy(String string) {
		
		int i;
		String channelString = "";
		int channel = Integer.parseInt(string);
		
		if(channel >= 1 && channel <= 14)
		{
			i = (channel - 1) * 5 + 2412;
			channelString = i + "";
		}else if(channel >= 36 && channel <= 165)
		{
			i = (channel - 34) * 5 + 5170;
			channelString = i + "";
		}	

		
		return channelString;
	}

	/**
	 * Compares between the previous line and the current line to check if they have the same time and location.
	 * @param currentLat - from the previous line store to compare if it equals the current line
	 * @param currentLon - from the previous line 
	 * @param currentAlt - from the previous line 
	 * @param currentTime - from the previous line 
	 * @param line - the current line 
	 * @return true if the time and locationa are the same otherwise returns false.
	 */
	private boolean CheckSameParameters(String currentLat, String currentLon, String currentAlt, String currentTime, String[] line) {

		boolean latIsEqual = currentLat.equals(line[6]);
		boolean lonIsEqual = currentLon.equals(line[7]);
		boolean altIsEqual = currentAlt.equals(line[8]);;
		boolean timesIsEqual = currentTime.equals(line[3]);

		return latIsEqual && lonIsEqual && altIsEqual && timesIsEqual;
	}
	
//	/**
//	 * 
//	 * @param currentLat
//	 * @param string
//	 * @return
//	 */
//	private boolean CheckIfInOneLat(String currentLat, String string) {
//		boolean latIsEqual;
//
//		latIsEqual = currentLat.equals(string);
//
//		return latIsEqual;
//	}
//	
//	/**
//	 * 
//	 * @param currentLon
//	 * @param string
//	 * @return
//	 */
//	private boolean CheckIfInOneLon(String currentLon, String string) {
//		boolean lonIsEqual;
//
//		lonIsEqual = currentLon.equals(string);
//
//		return lonIsEqual;
//	}
//	
//	
//	/**
//	 * 
//	 * @param currentAlt
//	 * @param string
//	 * @return
//	 */
//	private boolean CheckIfInOneAlt(String currentAlt, String string) {
//		boolean altIsEqual;
//
//		altIsEqual = currentAlt.equals(string);
//
//		return altIsEqual;
//	}
//
//	/**
//	 * 
//	 * @param currentTime
//	 * @param string
//	 * @return
//	 */
//	private boolean CheckIfInOneTime(String currentTime, String string) {
//
//		boolean timesIsEqual;
//
//		timesIsEqual = currentTime.equals(string);
//
//		return timesIsEqual;
//	}
}


