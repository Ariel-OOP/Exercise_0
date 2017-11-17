
/**
 * @author Moshe
 * @see https://examples.javacodegeeks.com/core-java/apache/commons/csv-commons/writeread-csv-files-with-apache-commons-csv-example/
 */
public class WIFISample {
	
	//WIFI attributes
	private String WIFI_MAC;
	private String WIFI_SSID;
	private String WIFI_AuthMode;
	private String WIFI_FirstSeen; 
	private String WIFI_Channel;
	private String WIFI_Frequency;
	private String WIFI_RSSI;
	private String WIFI_Lat;
	private String WIFI_Lon;
	private String WIFI_Alt; 
	private String WIFI_Accuracy;
	private String WIFI_Type;
	
	//Device attributes
	private String WIFI_Device;
	
	public WIFISample(String wIFI_MAC, String wIFI_SSID, String wIFI_FirstSeen, String wIFI_Channel, String wIFI_RSSI,
			String wIFI_Lat, String wIFI_Lon, String wIFI_Alt, String wIFI_Type) {
		WIFI_MAC = wIFI_MAC;
		WIFI_SSID = wIFI_SSID;
		WIFI_FirstSeen = wIFI_FirstSeen;
		WIFI_Channel = wIFI_Channel;
		WIFI_RSSI = wIFI_RSSI;
		WIFI_Lat = wIFI_Lat;
		WIFI_Lon = wIFI_Lon;
		WIFI_Alt = wIFI_Alt;
		WIFI_Type = wIFI_Type;
		WIFI_Frequency = convertChannelToFrequency();
	}

	private String convertChannelToFrequency() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getWIFI_Device() {
		return WIFI_Device;
	}

	public void setWIFI_Device(String wIFI_Device) {
		WIFI_Device = wIFI_Device;
	}

	public String getWIFI_MAC() {
		return WIFI_MAC;
	}


	public void setWIFI_MAC(String wIFI_MAC) {
		WIFI_MAC = wIFI_MAC;
	}


	public String getWIFI_SSID() {
		return WIFI_SSID;
	}


	public void setWIFI_SSID(String wIFI_SSID) {
		WIFI_SSID = wIFI_SSID;
	}


	public String getWIFI_FirstSeen() {
		return WIFI_FirstSeen;
	}


	public void setWIFI_FirstSeen(String wIFI_FirstSeen) {
		WIFI_FirstSeen = wIFI_FirstSeen;
	}


	public String getWIFI_Channel() {
		return WIFI_Channel;
	}


	public void setWIFI_Channel(String wIFI_Channel) {
		WIFI_Channel = wIFI_Channel;
	}


	public String getWIFI_RSSI() {
		return WIFI_RSSI;
	}


	public void setWIFI_RSSI(String wIFI_RSSI) {
		WIFI_RSSI = wIFI_RSSI;
	}


	public String getWIFI_Lat() {
		return WIFI_Lat;
	}


	public void setWIFI_Lat(String wIFI_Lat) {
		WIFI_Lat = wIFI_Lat;
	}


	public String getWIFI_Lon() {
		return WIFI_Lon;
	}


	public void setWIFI_Lon(String wIFI_Lon) {
		WIFI_Lon = wIFI_Lon;
	}


	public String getWIFI_Alt() {
		return WIFI_Alt;
	}


	public void setWIFI_Alt(String wIFI_Alt) {
		WIFI_Alt = wIFI_Alt;
	}


	public String getWIFI_Type() {
		return WIFI_Type;
	}


	public void setWIFI_Type(String wIFI_Type) {
		WIFI_Type = wIFI_Type;
	}


	@Override
	public String toString() {
		return "WIFISample [WIFI_MAC=" + WIFI_MAC + ", WIFI_SSID=" + WIFI_SSID + ", WIFI_Channel=" + WIFI_Channel
				+ ", WIFI_RSSI=" + WIFI_RSSI + "]";
	}

	
	
	
}