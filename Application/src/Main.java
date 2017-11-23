
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nissan on 11/21/2017.
 */
public class Main {

    public static void main(String[] args) {
        String folderPath="FileResources";

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter(folderPath,"testOutputCSV.csv");
        List<List<WifiPointsTimePlace>> processedFile =  outputCSVWriter.sortAndMergeFiles();
        outputCSVWriter.ExportToCSV(processedFile);

        Scanner stdin = new Scanner(System.in);
        System.out.println("This is a filter of points.\n"
                + "* If you don't want any filter enter 0\n"
                + "* If you want to filter by coordinates enter 1\n"
                + "* If you want to filter by time enter 2\n"
                + "* If you want to filter by ID enter 3\n"
                + "===============================================\nEnter a number: ");
        int inputChoice = Integer.parseInt(stdin.nextLine());
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
}
