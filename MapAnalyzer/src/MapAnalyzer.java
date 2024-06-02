import java.io.*;

/**
 * Main class to analyze the map.
 */
public class MapAnalyzer {
    /**
     * Main method to analyze the map.
     *
     * @param args command line arguments. Should contain the input file and output file paths.
     * @throws IOException if an I/O error occurs.
     */


        public static void main(String[] args) throws IOException {
            if (args.length != 2) {
                System.err.println("Usage: java MapAnalyzer <input file> <output file>");
                System.exit(1);
            }
            
            String inputFile = args[0];
            String outputFile = args[1];

            RoadMap roadMap = new RoadMap();
            roadMap.loadFromFile(inputFile);
            roadMap.analyzeAndWriteResults(outputFile);
        }

          
    }
