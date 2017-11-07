import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Ex0_3 {
//
	static String csvFilePath= "C:\\Users\\Moshe\\Desktop\\data\\wifiStrenght.csv";
	static int choiceFilter; //Which filter

	public static void main(String[] args) {

		BufferedReader br = null;
		String line;
		String[] lineInformation;
		String filterOfUser = "";

		filterOfUser = getFilter();

		try {
			PrintWriter writer = new PrintWriter("C:\\Users\\Moshe\\Desktop\\google_earth_WIFI.kml", "UTF-8");
			writer.print("");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");//The title of kml file
			writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>");

			br = new BufferedReader(new FileReader(csvFilePath));
			br.readLine();//Throws away the header

			while ((line = br.readLine()) != null) {
				// use comma as separator
				lineInformation = line.split(",");//Split each line to parts of properties
				if (CheckLineByFilter(filterOfUser,lineInformation)) {
					for (int i = 0; i < Integer.parseInt(lineInformation[5]); i++) {//write the property to format of kml 
						writer.println("<Placemark>\n" + "<name><![CDATA[" + lineInformation[6 + 4 * i] + "]]></name>\n"
								+ "<description><![CDATA[BSSID: <b>" + lineInformation[7 + 4 * i]
										+ "</b><br/>Capabilities: <b>[" + " Encryption " + "]</b><br/>Frequency: <b>"
										+ lineInformation[8 + 4 * i] + "</b><br/>Timestamp: <b>" + " [Timestamp] "
										+ "</b><br/>Date: <b>" + lineInformation[0] + "</b>]]></description><styleUrl>#"
										+ theColorOfPoint(lineInformation[9 + 4 * i]) + "</styleUrl>\n"
										+ "<Point>\n<coordinates>" + FixLatLonPoint(lineInformation[3], i) + ","
										+ FixLatLonPoint(lineInformation[2], i) + "</coordinates></Point>\n</Placemark>");
					} 
				}
			} 

			writer.print("</Folder>\n" + "</Document></kml>");

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Success!! ");
	}
	/**
	 * This method is responsible for the reception of the filter
	 * @return
	 */
	private static String getFilter() {

		String userFilter = "";
		Scanner stdin = new Scanner(System.in);
		char c;

		System.out.println("This is a filter of points.\n"
				+ "* If you don't want any filter enter 0\n"
				+ "* If you want to filter by coordinates enter 1\n"
				+ "* If you want to filter by time enter 2\n"
				+ "* If you want to filter by ID enter 3\n"
				+ "===============================================\nEnter a number: ");

		do {
			c = stdin.nextLine().charAt(0);
			if (c>51 || c<48) System.out.println("Not valid choice, please choose again\n");
		}while(c>51 || c<48);

		choiceFilter = Integer.parseInt(c+"");

		switch(choiceFilter) {
		case 0:
			break;
		case 1:
			userFilter = getCoordinatesFilter();
			break;
		case 2:
			userFilter = getTimeFilter();
			break;
		case 3:
			userFilter = getIDFilter();
			break;

		}

		return userFilter;
	}

	/**
	 * This method is responsible for the reception of the ID filter.
	 * @return the ID filter
	 */
	private static String getIDFilter() {

		Scanner stdin = new Scanner(System.in);
		String IDFilter = "";

		System.out.println("\nEnter ID of device that sample ");
		IDFilter = "display=" + stdin.nextLine();

		return IDFilter;
	}

	/**
	 * This method is responsible for the reception of the time filter.
	 * @return the time filter
	 */
	private static String getTimeFilter() {
	
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

		return timeFilter;
	}

	/**
	 * 
	 * @return
	 * @throws ParseException
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

		return new SimpleDateFormat(dateFormat).parse(str).getTime();
	}

	/**
	 * This method is responsible for the reception of the coordinates filter.
	 * @return the coordinates filter
	 */
	private static String getCoordinatesFilter() {
		Scanner stdin = new Scanner(System.in);
		String coordinateFilter = "";

		System.out.println("\nEnter coordinate (by format Lat,Lon)");
		String lat_lon = stdin.next();
		System.out.println("\nEnter the radus from the coordinate (by meters)");
		String radius = stdin.next();

		coordinateFilter = "C:" + lat_lon + "R:" + radius;

		return coordinateFilter;
	}

	/**
	 * 	
	 * @param filterOfUser - the filter that entered by user
	 * @param lineInformation - all properties of current line
	 * @return true, if the user's filter is equal to the corresponding property. else, false.
	 */
	private static boolean CheckLineByFilter(String filterOfUser, String[] lineProperties) {

		boolean lineIsCorrect = false;
		
		switch(choiceFilter) {
		case 0: return true;
		case 1:
			int indexOfComa = filterOfUser.indexOf(',');
			int indexOfRSymble = filterOfUser.indexOf('R');

			double Lat = Double.parseDouble(filterOfUser.substring(2, indexOfComa));
			double Lon = Double.parseDouble(filterOfUser.substring(indexOfComa + 1, indexOfRSymble));
			double Radus = Double.parseDouble(filterOfUser.substring(indexOfRSymble + 2, filterOfUser.length()));

			return distFrom(Lat,Lon,Double.parseDouble(lineProperties[2]),Double.parseDouble(lineProperties[3]),Radus);

		case 2:
			String strDateFromFile = "";
			String dateFormat = "dd/MM/yyyy hh:mm";
			long dateFromFile;
			boolean isDateWithinBounds = false;
			
			try {
				String year = lineProperties[0].substring(0, 4), month = lineProperties[0].substring(5,7), day = lineProperties[0].substring(8,10);
				String time = lineProperties[0].substring(11, lineProperties[0].length() - 3);
				
				strDateFromFile = day + "/" + month + "/" + year + " " + time;
				dateFromFile = new SimpleDateFormat(dateFormat).parse(strDateFromFile).getTime();

				int indexOfESymble = filterOfUser.indexOf('E');
				long startTime = Long.parseLong(filterOfUser.substring(2, indexOfESymble-1));
				long endTime = Long.parseLong(filterOfUser.substring(indexOfESymble + 2, filterOfUser.length()));
				
				return setDate(startTime, endTime, dateFromFile);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 3://01-01-2001 00:00 , 31-10-2017 00:00
			return filterOfUser.equals(lineProperties[1]);
		}

		return lineIsCorrect;
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param fileDate
	 * @return
	 */
	public static boolean setDate(long startDate, long endDate, long fileDate){
		if ( (fileDate-startDate)>=0 && (endDate-fileDate)>=0 )
			return true;
		return false;
	}

	/**
	 * This method calculate the distance between two points that given as Lat,Lon.
	 * WEB Source: https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	 * @param lat1 - Latitude of first point
	 * @param lng1 - Longitude of first point
	 * @param lat2 - Latitude of second point
	 * @param lng2 - Longitude of first point
	 * @param radius - the maximal distance between the two points
	 * @return true, if the second point located in the area of the radius. else false
	 */
	private static boolean distFrom(double lat1, double lng1, double lat2, double lng2, double radius) {
		//

		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = (double) (earthRadius * c);

		return dist <= radius;
	}

	/**
	 * 
	 * @param coordinate - Coordinate of one point
	 * @param i - the number of current point in given minute
	 * @return
	 */
	private static String FixLatLonPoint(String coordinate,int i) {

		double newCoordinate = Double.parseDouble(coordinate) + (i * 0.00000);

		return "" + newCoordinate;
	}

	/**
	 * 
	 * @param str - String that describe RXL of one point
	 * @return - the color that given to this point according to Strength of RXL
	 */
	private static String theColorOfPoint(String str) {

		int RXLnumber = Integer.parseInt(str);
		String color = "";

		if(RXLnumber <= 0 && RXLnumber > -70)
		{
			color = "green";
		}
		else if(RXLnumber <= -70 && RXLnumber > -80)
		{
			color = "yellow";
		}
		else
			color = "red";


		return color;
	}

}
