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

		Scanner stdin;			
		String folderPath="C:\\Users\\nissa\\Desktop\\Year 2\\OOP\\WigleFromPhone";
		//		String folderPath="C:\\Users\\Moshe\\Desktop\\data\\27.10\\Simple_1";
		File dir = new File(folderPath);		//	The current file
		for (File file : dir.listFiles()) {

			stdin = null;	//Gave null so it would be initialized
			WigleFileReader wigleFileReader;
			try {
				//Incorrect file type-reject
				if (!(file.getName().toLowerCase().endsWith(".csv"))){
					System.out.println(file.getName()+" is an incorrect file type in the folder");
					System.out.println("the file was not added to the csv file error 404");
					continue;
				}
				//Read the text from current file
				stdin = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("\nRead CSV file:");
			wigleFileReader = new WigleFileReader(file.toString());
			wigleFileReader.readCsvFile();	
			
		}

		String fileName = "FromWIFIWigle.csv";
		/*System.getProperty("user.home")+*/

		//		System.out.println("Write CSV file:");
		//		CsvFileWriter.writeCsvFile(fileName);


	}

}