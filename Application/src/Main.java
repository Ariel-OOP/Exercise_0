
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nissan on 11/21/2017.
 */
public class Main {

    public static void main(String[] args) {
        String folderPath="resources";

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter(folderPath,"testOutputCSV.csv");
        List<List<LineOfFinalCSV>> processedFile =  outputCSVWriter.sortAndMergeFiles();
        outputCSVWriter.ExportToCSV(processedFile);

        Scanner stdin = new Scanner(System.in);
        char c = printMenu();
        int inputChoice = Integer.parseInt(c+"");
        Filter filter = new Filter(inputChoice);
        do{
            LineFilters.printInput(inputChoice);
        }while (!filter.setFilter(stdin.nextLine()));

        KmlExporter kmlExporter = new KmlExporter("testOutputCSV.csv","resources\\helloKML1.kml");
        if(kmlExporter.csvToKml(filter) )
            System.out.println("successful export");
        else
            System.out.println("failure to export");
    }

    private static char printMenu() {
        char c;
        Scanner stdin = new Scanner(System.in);
        System.out.println("=========================================\nThis is a filter of points.\n"
                + "* If you don't want any filter enter 0\n"
                + "* If you want to filter by coordinates enter 1\n"
                + "* If you want to filter by time enter 2\n"
                + "* If you want to filter by ID enter 3\n"
                + "===============================================\nEnter a number: ");

        do {
            c = stdin.nextLine().charAt(0);
            if (c>51 || c<48) System.out.println("Not valid choice, please choose again\n");
        }while(c>58 || c<48);
        return c;
    }
}
