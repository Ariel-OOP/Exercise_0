
import java.util.Date;

/**
 * Created by Nissan on 11/21/2017.
 */
public class LineFilters {
    public static boolean distFrom(double lat1, double lng1, double lat2, double lng2, double radius) {
        //

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (double) (earthRadius * c);

        return dist <= radius;
    }

    public static boolean isDateInBounds(Date startDate, Date endDate, long inputDate){
        long test = startDate.getTime();
        long test2= inputDate-startDate.getTime();
        long test3= endDate.getTime()-inputDate;
        if ( (inputDate-startDate.getTime())>=0 && (endDate.getTime()-inputDate)>=0 )
            return true;
        return false;
    }

    public static void printInput(int choice){
        switch (choice){
            case 1: System.out.println("enter the LOCATION in the following format: \n32.009,43.99 55");
                    break;
            case 2: System.out.println("enter the TIME in following format:" +
                    " \n" + "(Start Time\t ,\t\t EndTime)\n"+
                    "15-12-2001 12:12,12-12-2023 12:12");
                     break;
            case 3: System.out.println("enter the ID with a space:");
                    break;
            case 0:
                     System.out.println("No filter, press enter to continue");
                     break;
            default: System.out.println("incorrect input, NO Filter has been defaulted\n" +
                    "press enter to continue");
        }
    }
}
