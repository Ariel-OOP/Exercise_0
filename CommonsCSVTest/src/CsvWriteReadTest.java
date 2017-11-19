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
			
		//String folderPath="C:\\Users\\nissa\\Desktop\\Year 2\\OOP\\WigleFromPhone";
				String folderPath="C:\\Users\\Moshe\\Desktop\\data\\27.10\\Simple_1";

		OutputCSVWriter outputCSVWriter = new OutputCSVWriter(folderPath,"testOutputCSV");
		outputCSVWriter.createCSV();


	}

}