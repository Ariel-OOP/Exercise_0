import java.util.Scanner;

public class MainEx0_3 {

	public static void main(String[] args) {
		int choice;
		String inputCSVPath ="C:\\Users\\Moshe\\Desktop\\data\\wifiStrenght.csv";
		String outputKmlpath = "C:\\Users\\Moshe\\Desktop\\google_earth_WIFI.kml";
		
		choice=getUserChoice();
		GenerateFilter filter = new GenerateFilter(choice);
		FilterCSV lineFromCSV = new FilterCSV(filter.getFilter() , choice);
		if(  lineFromCSV.csvToKml( inputCSVPath , outputKmlpath)  )
				System.out.println("success");
		else
			System.out.println("failure");


		
	}
	
	
	
	public static int getUserChoice(){
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
		
		//stdin.close();
		return Integer.parseInt(c+"");
	}
	

}