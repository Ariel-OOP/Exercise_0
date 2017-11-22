# Exercise_0
## Part Old_Version.MainEx0- Part II <br />
Reads CSV file from wigle app :iphone:, sorts the 10 strongest wifi points. <br />
It is recommended to change **pathFolder** to the folder :file_folder: which contains the csv files. <br />

## Part Old_Version.MainEx0_3- Part III <br />
Reads the CSV from part II and exports to KML format .<br />
It is recommended to change **inputCSVPath** to where the file was exported in Part II. <br />
It is recommended to change **outputKmlpath** to where you would like to export :floppy_disk: the KML file. <br />


# General Description <br />
This project is written in java. The program is divided into two parts,<br />
<br />
**The First part,** receives a CSV type file that was exported from the Wigle app :iphone:. The file contains WIFI points that were sampled and identify each point (For example: time, location, MAC address, signal strenght and ect.) and sorts each time period and location its points by its strength and takes the 10 strongest. At the end of the program it exports a new CSV file which each a row shows at the most 10 WIFI points with the following parameters SSID,BSSID, Frequncy, Signal. Each row is the same time and location.

**The Second part,** recevies the file that was exported from the first part (or similiar file) and filters it using three criteria: time,location and ID. <br />

1.  Time, the program asks the user to enter two dates, a start date/time and a end date/time. The program filters
the WIFI points in that period of time from the CSV file.
2.  Location, the program asks the user to enter coordinates (Lat,Lon) and radius. The program filters the WIFI points in
the given radius of the coordinates from the CSV file.
3. ID, the program asks the user to enter the manufacturer model and will give WIFI points from the corresponding deice.
<br />
After the filteration is complete the program will export a KML file (it is similar to an XML file) with all the related WIFI points. The KML file can be opened using Google Earth.


