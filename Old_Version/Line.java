package Old_Version;

/**
 * The class is responsible to check the correctness of a line from a file.
 * This includes the header/titles of the file and line itself.The functions make heavy use of ReGex.
 * @author nissan,moshe and Shir
 * @since 9-11-2017
 */
public class Line {
	private String line;
	
	/**
	 * The constructor receives a string of one line which is usually from the file.
	 * @param line - String which is the line from the file
	 */
	public Line(String line) {
		this.line = line;
	}
	
	/**
	 * This method check if the pattern of each line (except for header-lines!) is correct
	 * @return If the pattern is correct the method return true, else the method return false
	 */
	public boolean checkPattern()
	{
		boolean patternCorrect = false;
		
		patternCorrect = this.line.matches("(\\p{XDigit}{2}[:]){5}(\\p{XDigit}{2})"/*MAC*/
				+ "[,]{1}[\\p{Graph} ]*"/*SSID*/
				+ "[,]{1}[\\p{Graph} ]*"/*Encryption*/
				+ "[,]{1}[\\p{Digit}]{2,4}[/-]{1}[\\p{Digit}]{1,2}[/-]{1}[\\p{Digit}]{1,4}"/*Date in format yyyy-(mm or m)-(dd or d)*/
				+ "[ ]{1}[\\p{Digit}]{2}[:]{1}[\\p{Digit}]{2}[:]{0,1}[\\p{Digit}]{0,2}"/*Time in format hh:mm:ss*/
				+ "[,]{1}[\\p{Digit}]{1,2}"/*Chanel*/
				+ "[,]{1}[-]{1}[\\p{Digit}]{1,3}"/*RSSI*/
				+ "[,]{1}[-]?[\\p{Digit}]{1,2}[.]{0,1}[\\p{Digit}]{0,25}"/*Lat*/
				+ "[,]{1}[-]?[\\p{Digit}]{1,3}[.]{0,1}[\\p{Digit}]{0,25}"/*Lon*/
				+ "[,]{1}[-]?[\\p{Digit}]{1,5}[.]{0,1}[\\p{Digit}]{0,25}"/*Alt*/
				+ "[,]{1}[\\p{Digit}]{1,2}[.]{0,1}[\\p{Digit}]{0,25}"/*Accuracy Meters*/
				+ "[,]{1}[\\p{Graph}]*"/*Type*/
				);
		
		return patternCorrect;
	}
	/**
	 * This method checks if in the header there are:  WigleWifi...,appRelease=...,model=...,release=...,device=...,display=...,board=...,brand=...
	 * @return The method return true if all the parameters appear, else the method return false.
	 */
	public boolean checkHeader()
	{
		boolean headerCorrect = false;
		String[] arrayLine = this.line.split(",");
		
		headerCorrect = arrayLine[0].contains("WigleWifi") && 
				arrayLine[1].contains("appRelease=") && 
				arrayLine[2].contains("model=") && 
				arrayLine[3].contains("release=") && 
				arrayLine[4].contains("device=") && 
				arrayLine[5].contains("display=") && 
				arrayLine[6].contains("board=") &&
				arrayLine[7].contains("brand=");
		
		return headerCorrect;
	}
	
	
/**
 * This method checks if in the title there are: MAC,SSID,AuthMode,FirstSeen,Channel,RSSI,CurrentLatitude,CurrentLongitude,AltitudeMeters,AccuracyMeters,Type
 * @return The method return true if all the parameters appear, else the method return false.
 */
	public boolean checkTitle()
	{
		boolean titleCorrect = false;
		String[] arrayLine = this.line.split(",");
		
		titleCorrect = arrayLine[0].contains("MAC") && 
				arrayLine[1].contains("SSID") && 
				arrayLine[2].contains("AuthMode") && 
				arrayLine[3].contains("FirstSeen") && 
				arrayLine[4].contains("Channel") && 
				arrayLine[5].contains("RSSI") && 
				arrayLine[6].contains("CurrentLatitude") &&
				arrayLine[7].contains("CurrentLongitude") &&
				arrayLine[8].contains("AltitudeMeters") &&
				arrayLine[9].contains("AccuracyMeters") &&
				arrayLine[10].contains("Type");
		
		return titleCorrect;
	}
}
