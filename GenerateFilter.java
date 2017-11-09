import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
/**
 * The class GenerateFilter will generate a object which has a appropriate filter which can be accessed through the method .getFIlter() 
 * @author nissan,moshe and shir
 * @since 9-11-2017
 * @version 0.9.9 Final Release Beta which is an Alpha of the Gamma
 */
public class GenerateFilter{
	private int choice; // choose which type of filter to use automatically
	private static String userFilter=null; //global variable to track the filter
	/**
	 * the constructor generates an object by the choice from our menu by which it will ask for the corresponding filter and check for a valid input
	 * @param choice is the type of filter to be used, see menu
	 */
	public GenerateFilter(int choice) {
		this.choice = choice;
		inputFilter();
	}
		
	/**
	 * The function calls to the relevant function to get the correct input and verifies its input
	 * @return boolean if the input was typed in the right syntax
	 */
	public boolean inputFilter(){
		boolean verifyInput;
		switch(choice) {
		case 0:
			break;
		case 1:
			verifyInput = getCoordinatesFilter();
			break;
		case 2:
			verifyInput = getTimeFilter();
			break;
		case 3:
			verifyInput = getIDFilter();
			break;

		}

//		return userFilter;
		return true;
	}
	

/**
 * This method is responsible for the reception of the coordinates filter.
 * @return boolean is the filter was verified
 */
	private static boolean getCoordinatesFilter() {
		Scanner stdin = new Scanner(System.in);
		String coordinateFilter = "";

		boolean correctCordintion = true;
		//System.out.println("\nEnter coordinate (by format Lat,Lon)");
		String lat_lon;
		int indexOfComma = 0;
		double lat,lon;
		boolean correctcoordinates = false;
		do {
			lat = -200;
			lon = -200;
			
			System.out.println("\nEnter coordinate (by format Lat,Lon)");		
			lat_lon = stdin.next();
			
			if(lat_lon.contains(",") && lat_lon.matches("[-]?[\\p{Digit}]{1,2}[.]{0,1}[\\p{Digit}]{0,25}"/*Lat*/
					+ "[,]{1}[-]?[\\p{Digit}]{1,3}[.]{0,1}[\\p{Digit}]{0,25}"/*Lon*/))
			{
				indexOfComma = lat_lon.indexOf(",");
				
				lat = Double.parseDouble(lat_lon.substring(0, indexOfComma));
				lon = Double.parseDouble(lat_lon.substring(indexOfComma + 1, lat_lon.length()));
			}
			if((lat >= -90) && (lat <= 90) && (lon >= -180) && (lat <= 180))
				correctcoordinates = true;
				
		} while (!correctcoordinates);
		
		//System.out.println("\nEnter the radus from the coordinate (by meters)");
		int radius;
		do {
			System.out.println("\nEnter the radus from the coordinate (by meters) (more zero)");
			radius = -1;
			radius = Integer.parseInt(stdin.next());
		} while (radius <= 0);
		//TODO need to verify the text 
		coordinateFilter = "C:" + lat_lon + "R:" + radius;
		userFilter=coordinateFilter;  //Moshe I added this!!!!
		
//		return coordinateFilter;
		//TODO return boolean is verified
		stdin.close();
		return true;
	}
	

/**
 * This method is responsible for the reception of the time filter.
 * @return the time filter
 */
	private static boolean getTimeFilter() {
	
		String timeFilter = "";
		long startTime = 0,endTime = 0;

		try {
			startTime = inputValidTime();
			endTime = inputValidTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timeFilter = "S:" + startTime + " E:" + endTime;
		userFilter = timeFilter; //Moshe i added this
//		return timeFilter;
		return true;
	}

	/**
	 * This function asks to input the time and will continue to ask if not in the right format
	 * @return a long which is the time converted im ms from 1970
	 * @throws ParseException if incorrect date
	 */
	public static long inputValidTime() throws ParseException{
		String str;
		Scanner scanner = new Scanner(System.in);
		String dateFormat = "dd-MM-yyyy hh:mm";
		System.out.println("enter date in the following format -> dd-MM-yyyy hh:mm ");

		do{
			str = scanner.nextLine();
			if (str.matches("[\\p{Digit}]{2,4}[/-]{1}[\\p{Digit}]{1,2}[/-]{1}[\\p{Digit}]{1,4}"/*Date in format yyyy-(mm or m)-(dd or d)*/
					+ "[ ]{1}[\\p{Digit}]{2}[:]{1}[\\p{Digit}]{2}"/*Time in format hh:mm*/))
				break;
			System.out.println("incorrect start date, may you forgot to input the time");
		}while(true);
		
		//scanner.close();
		return new SimpleDateFormat(dateFormat).parse(str).getTime();
	}
	
	/**
	 * This method is responsible for the reception of the ID filter.
	 * @return the ID filter .
	 */
	private static boolean getIDFilter() {

		Scanner stdin = new Scanner(System.in);
		String IDFilter = "";

		System.out.println("\nEnter ID of device that sample ");
		IDFilter = "display=" + stdin.nextLine();

//		return IDFilter;
		userFilter=IDFilter;
		stdin.close();
		return true;
	}
	
	public String getFilter(){
		return userFilter;
	}

}