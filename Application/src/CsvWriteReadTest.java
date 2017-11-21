package Application.src;

import java.util.List;


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

		OutputCSVWriter outputCSVWriter = new OutputCSVWriter(folderPath,"testOutputCSV.csv");
		List<List<LineOfFinalCSV>> processedFile =  outputCSVWriter.sortAndMergeFiles();
		outputCSVWriter.ExportToCSV(processedFile);
	}

}