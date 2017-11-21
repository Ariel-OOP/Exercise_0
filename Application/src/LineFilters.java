
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
        if ( (inputDate-startDate.getTime())>=0 && (endDate.getTime()-inputDate)>=0 )
            return true;
        return false;
    }

}
