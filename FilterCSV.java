import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FilterCSV {
	String userFilter;
	static int choice;
	//static String csvFilePath;

	public FilterCSV(String userFilter,int choice) {
		this.userFilter = userFilter;
		this.choice = choice;
	}
	/**
	 * The function converts the appropriate csv file(from part II!!!) to kml format
	 * @param csvFilePath is the path to the csv file which was generated in Part II 
	 * @param outKmlPathAndName - is the path which the kml file will be exported to
	 * @return boolean if the operation was successful 
	 */
	public boolean csvToKml(String csvFilePath,String outKmlPathAndName){
		//this.csvFilePath = csvFilePath;

		BufferedReader br = null;
		String line;
		String[] lineInformation;

		try {
			PrintWriter writer = new PrintWriter(outKmlPathAndName, "UTF-8");
			writer.print("");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");//The title of kml file
			writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>");


			br = new BufferedReader(new FileReader(csvFilePath));
			br.readLine();//Throws away the header
			//String onePoint = "";
			while ((line = br.readLine()) != null) {
				// use comma as separator
				lineInformation = line.split(",");//Split each line to parts of properties 
				if (CheckLineByFilter(lineInformation)) {
					printPointOnMap(lineInformation,writer);
				}
			} 

			writer.print("</Folder>\n" + "</Document></kml>");

			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param userFilter - the filter that entered by user
	 * @param lineInformation - all properties of current line
	 * @return true, if the user's filter is equal to the corresponding property. else, false.
	 */
	public /*static*/ boolean CheckLineByFilter(String[] lineProperties) {

		boolean lineIsCorrect = false;

		switch(choice) {
		case 0: return true;
		case 1:
			int indexOfComa = userFilter.indexOf(',');
			int indexOfRSymble = userFilter.indexOf('R');

			double Lat = Double.parseDouble(userFilter.substring(2, indexOfComa));
			double Lon = Double.parseDouble(userFilter.substring(indexOfComa + 1, indexOfRSymble));
			double Radus = Double.parseDouble(userFilter.substring(indexOfRSymble + 2, userFilter.length()));

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

				int indexOfESymble = userFilter.indexOf('E');
				long startTime = Long.parseLong(userFilter.substring(2, indexOfESymble-1));
				long endTime = Long.parseLong(userFilter.substring(indexOfESymble + 2, userFilter.length()));

				return setDate(startTime, endTime, dateFromFile);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 3://01-01-2001 00:00 , 31-10-2017 00:00
			return userFilter.equals(lineProperties[1]);
		}

		return lineIsCorrect;
	}
	/**
	 * printPointOnMap will output in table format to the kml file
	 * @param CSVLine the line in which will be written
	 * @param KMLFile 
	 */
	private static void printPointOnMap(String[] CSVLine,PrintWriter KMLFile) {
		PrintWriter writer = KMLFile;
		String onePoint = "";
		onePoint = "\n<Placemark>\n" + "<name><![CDATA[" + CSVLine[0] + "]]></name>\n"
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
				+ theColorOfPoint(CSVLine[9])
				+ "</styleUrl>\r\n"  
				+ "<Point>\r\n"  
				+ "<coordinates>"
				+ CSVLine[3] +","
				+ CSVLine[2] 
						+ "</coordinates>\r\n"  
						+ "\r\n"  
						+ "</Point>\r\n" 
						+ "</Placemark>";

		writer.println(onePoint);
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