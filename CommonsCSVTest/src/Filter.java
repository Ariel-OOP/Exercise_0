package CommonsCSVTest.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
//Todo remove scanner

/**
 * Created by Nissan on 11/21/2017.
 */
public class Filter {
    enum Choice{
        NOFILTER,LOCATION,TIME,ID;
    }

    private final Choice choice;
    double[] locationObject;
    Date[] timeObject;
    String idObject;
    String dateFormat = "dd-MM-yyyy hh:mm";


    public Filter(int numChoice) {
        switch (numChoice){
            case 0:
                choice =  Choice.NOFILTER;
                break;
            case 1:
                choice = Choice.LOCATION;
                break;
            case 2:
                choice = Choice.TIME;
                break;
            case 3:
                choice = Choice.ID;
                break;
            default:
                choice = Choice.NOFILTER;
                break;
        }
    }

    //TODO fill out
    public boolean setFilter(String input){
        boolean isCorrectInput;

        switch (choice){
            case NOFILTER:
                isCorrectInput=true;
                break;
            case LOCATION:
                isCorrectInput= setLocationFilter(input);
                break;
            case ID:
                isCorrectInput = setIdFilter(input);
                break;
            case TIME:
                isCorrectInput = setTimeFilter(input);
                break;
            default:
                isCorrectInput = false;
                break;
        }
        return isCorrectInput;
    }

    //TODO fix space between , both sides!!!
    private boolean setTimeFilter(String input) {
        String[] splitDates = input.split(",");
        boolean correctFormat = false;
        //TODO fix space between , both sides!!!
        if (splitDates[0].matches("[\\p{Digit}]{2,4}[/-]{1}[\\p{Digit}]{1,2}[/-]{1}[\\p{Digit}]{1,4}"/*Date in format yyyy-(mm or m)-(dd or d)*/
                + "[ ]{1}[\\p{Digit}]{2}[:]{1}[\\p{Digit}]{2}"/*Time in format hh:mm*/)){
            if (splitDates[1].matches("[\\p{Digit}]{2,4}[/-]{1}[\\p{Digit}]{1,2}[/-]{1}[\\p{Digit}]{1,4}"/*Date in format yyyy-(mm or m)-(dd or d)*/
                    + "[ ]{1}[\\p{Digit}]{2}[:]{1}[\\p{Digit}]{2}"/*Time in format hh:mm*/) )
            {
                correctFormat =true;
                timeObject = new Date[2];
                try {
                    Date startDate = new SimpleDateFormat(dateFormat).parse(splitDates[0]);
                    timeObject[0] = startDate;
                    Date endDate = new SimpleDateFormat(dateFormat).parse(splitDates[1]);
                    timeObject[1] = endDate;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        return correctFormat;
    }

    private boolean setIdFilter(String input) {
        idObject = input;
        return true;
    }

    private boolean setLocationFilter(String input) {
        //32.444 44.5000 44
        String[] splitInput = input.split(",");
        boolean correctcoordinates = false;
        double lat = -200;
        double lon = -200;

        if(input.contains(",") && input.matches("[-]?[\\p{Digit}]{1,2}[.]{0,1}[\\p{Digit}]{0,25}"/*Lat*/
                + "[,]{1}[-]?[\\p{Digit}]{1,3}[.]{0,1}[\\p{Digit}]{0,25}[ ]+[\\p{Digit}]+[.]?[\\p{Digit}]{1,6}"/*Lon*/))
        {
            int indexOfComma = input.indexOf(",");
            int indexOfSpace = input.indexOf(" ");

            lat = Double.parseDouble(input.substring(0, indexOfComma));
            lon = Double.parseDouble(input.substring(indexOfComma + 1, indexOfSpace));
            double radius = Double.parseDouble( input.substring(indexOfSpace + 1, input.length()) );

            if((lat >= -90) && (lat <= 90) && (lon >= -180) && (lat <= 180)){
                correctcoordinates = true;
                locationObject = new double[3];
                locationObject[0] = lat;
                locationObject[1] = lon;
                locationObject[2] = radius;
            }
        }

        return correctcoordinates;


    }


    /**
     * The method checks the current line in the csv file if it complies with the filter
     * @param lineProperties - all properties of current line
     * @return true, if the user's filter is equal to the corresponding property. else, false.
     */
    public boolean checkLineByFilter(String[] lineProperties) {

        boolean lineIsCorrect = false;

        switch(choice) {
            case NOFILTER: return true;
            case LOCATION:
                return LineFilters.distFrom(locationObject[0],locationObject[1],Double.parseDouble(lineProperties[2]),Double.parseDouble(lineProperties[3]),locationObject[3]);
            case TIME:
                String strDateFromFile = "";
                String dateFormatFile = "dd/MM/yyyy hh:mm";
                long dateFromFile;
                try {
                    String year = lineProperties[0].substring(0, 4), month = lineProperties[0].substring(5,7), day = lineProperties[0].substring(8,10);
                    String time = lineProperties[0].substring(11, lineProperties[0].length() - 3);

                    strDateFromFile = day + "/" + month + "/" + year + " " + time;
                    dateFromFile = new SimpleDateFormat(dateFormatFile).parse(strDateFromFile).getTime();

                    return LineFilters.isDateInBounds(timeObject[0], timeObject[1], dateFromFile);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case ID://01-01-2001 00:00 , 31-10-2017 00:00
                return idObject.equals(lineProperties[1]);
        }

        return lineIsCorrect;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("enter 0,1");
        int inputChoice = Integer.parseInt(stdin.nextLine());
        Filter filter = new Filter(inputChoice);
        if (filter.setFilter(stdin.nextLine()) )
           System.out.println("succes");
        else
            System.out.println("failure");

    }
}
