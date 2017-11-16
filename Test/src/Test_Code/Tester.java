package Test_Code;

public class Tester {

	public static void main(String[] args) {
		String s = "42501_13111_496642,Partner,UNKNOWN;il,2017-10-27 16:35:53,0,-113,32.16691159,34.80894877,13,4.550999641418457,GSM";
				//
		
		System.out.println(s.matches("(\\p{XDigit}{2}[:]){5}(\\p{XDigit}{2})"/*MAC*/
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
				));
		// https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html	
	}
}
