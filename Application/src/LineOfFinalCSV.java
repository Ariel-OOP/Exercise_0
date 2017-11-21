package Application.src;
import java.util.ArrayList;
import java.util.List;

public class LineOfFinalCSV {

	List<String> WifiPoints;
	List<String> WifiPointsTimePlace;

	public LineOfFinalCSV() {
		WifiPoints = new ArrayList();
		WifiPointsTimePlace = new ArrayList();
	}

	public void addPoint(WIFISample point)
	{
		WifiPointsTimePlace.clear();
		//TODO change to time
		WifiPointsTimePlace.add(point.getWIFI_FirstSeen());
		WifiPointsTimePlace.add(point.getWIFI_Device());
		WifiPointsTimePlace.add(point.getWIFI_Lat());
		WifiPointsTimePlace.add(point.getWIFI_Lon());
		WifiPointsTimePlace.add(point.getWIFI_Alt());

		WifiPoints.add(point.getWIFI_SSID());
		WifiPoints.add(point.getWIFI_MAC());
		WifiPoints.add(point.getWIFI_Frequency());
		WifiPoints.add(point.getWIFI_RSSI());
	}

	public List<String> getWifiPoints() {

		//In order to get the number of points we need to divide by 4 because for each point there are 4 attributes to show
		WifiPointsTimePlace.add("" + (WifiPoints.size()/4));
		
		for(String wifi : WifiPoints)
			WifiPointsTimePlace.add(wifi);

		return WifiPointsTimePlace;
	}

	public String toString()
	{
		return "" + WifiPoints.size();
	}

}
