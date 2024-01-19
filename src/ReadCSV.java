import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV {

    public ArrayList<String[]> readCSV() {
    	
    	 ArrayList<String[]> list = new ArrayList<>();
    	 
        try {
            // Specify the path to your CSV file
            String csvFilePath = "C:\\Users\\Ranjeet Saw\\eclipse-workspace\\DHDS_Projects\\CricketJSON\\JSONproject\\Resources\\matchDetails.csv";

            // Create a BufferedReader to read the CSV file
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));

            String line;
           

            // Read each line in the CSV file
            while ((line = reader.readLine()) != null) {
            	
                // Split the line by the comma (or any other delimiter)
                String[] columns = line.split(",");
                list.add(columns);

                // Process each column in the row
//                for (String column : columns) {
//                    // Print the column value
//                    System.out.print(column + "\t");
//                }

                // Move to the next line after printing the columns in a row
//                System.out.println();
            }
            // Close the BufferedReader
            reader.close();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
}
