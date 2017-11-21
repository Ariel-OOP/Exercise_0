
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
        System.out.println("This is a filter of points.\n"
                + "* If you don't want any filter enter 0\n"
                + "* If you want to filter by coordinates enter 1\n"
                + "* If you want to filter by time enter 2\n"
                + "* If you want to filter by ID enter 3\n"
                + "===============================================\nEnter a number: ");
        int inputChoice = Integer.parseInt(stdin.nextLine());
        Filter filter = new Filter(inputChoice);
        if (filter.setFilter(stdin.nextLine()) )
            System.out.println("succesfully added filter");
        else
            System.out.println("failure to add filter");
        KmlExporter kmlExporter = new KmlExporter("testOutputCSV.csv","resources\\helloKML1.kml");
        System.out.println(kmlExporter.csvToKml(filter) );

    }
}
