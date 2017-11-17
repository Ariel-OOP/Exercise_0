/**
 * @author moshe and nissan
 *
 */
public class CsvWriteReadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String fileName = "FromWIFIWigle.csv";
		/*System.getProperty("user.home")+*/
		
//		System.out.println("Write CSV file:");
//		CsvFileWriter.writeCsvFile(fileName);
		
		System.out.println("\nRead CSV file:");
		CsvFileReader csvFileReader = new CsvFileReader();
		csvFileReader.readCsvFile(fileName);

	}

}