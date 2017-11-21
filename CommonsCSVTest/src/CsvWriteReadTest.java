package CommonsCSVTest.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author moshe and nissan
 *
 */
public class CsvWriteReadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		String folderPath="C:\\Users\\nissa\\Desktop\\Year 2\\OOP\\WigleFromPhone\\WigleWifi_20171109085533.csv";
//				String folderPath="C:\\Users\\Moshe\\Desktop\\data\\27.10\\Simple_1";

//		OutputCSVWriter outputCSVWriter = new OutputCSVWriter(folderPath,"testOutputCSV");
//		outputCSVWriter.createCSV();

		WigleFileReader wigleFileReader = new WigleFileReader(folderPath);
		System.out.println(wigleFileReader.getWigleList().get(3) );

		PredicateTester predicateTester = new PredicateTester();
//		System.out.println(predicateTester.filterWifi(, predicateTester.filterDistance()));


	}

}