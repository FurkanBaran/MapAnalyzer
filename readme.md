
# Map Analyzer

## Overview

This project is a map analyzer that finds the fastest route between two points using Dijkstra's algorithm and analyzes the map's road network. It can also generate a barely connected map using a minimum spanning tree approach.

## Usage

To run the project, compile and execute the `MapAnalyzer` class. You can provide input and output file names as arguments. The program will read the input file, process the map, and write the results to the output file.

```sh
java MapAnalyzer <input file> <output file>
```

## Input File Format
The input file should contain:

1. The start and end points in the first line, separated by a tab.
2. Each subsequent line should represent a road, with the format: point1 point2 distance id, separated by tabs.


## Output File Format
The output file will contain:

1. The fastest route from the start point to the end point, including the distance.
2. The roads of the barely connected map.
3. The fastest route on the barely connected map.
4. Analysis of the construction material usage and fastest route ratios between the original and barely connected maps.


## Example
### Input (i1.txt)
```txt
Ankara	İstanbul
Bolu	İstanbul	1	7
Ankara	Bolu	1	3
İzmir	İstanbul	1	5
Ankara	İzmir	1	1
Bursa	İstanbul	1	6
Bursa	Bolu	1	4
Ankara	Bursa	1	2
```

### Output (o1.txt)
```txt
Fastest Route from Ankara to İstanbul (2 KM):
Ankara	İzmir	1	1
İzmir	İstanbul	1	5
Roads of Barely Connected Map is:
Ankara	İzmir	1	1
Ankara	Bursa	1	2
Ankara	Bolu	1	3
İzmir	İstanbul	1	5
Fastest Route from Ankara to İstanbul on Barely Connected Map (2 KM):
Ankara	İzmir	1	1
İzmir	İstanbul	1	5
Analysis:
Ratio of Construction Material Usage Between Barely Connected and Original Map: 0.57
Ratio of Fastest Route Between Barely Connected and Original Map: 1.00
```

## Classes and Methods
- `MapAnalyzer`: The main class to analyze the map.
- `Road`: A class representing a road with two endpoints, distance, and an ID.
- `RoadMap`: A class representing the road map and the analysis operations.
    - `loadFromFile(String filename)`: Loads the map data from the input file.
    - `analyzeAndWriteResults(String outputFile)`: Analyzes the map and writes the results to the output file.
    - `findFastestRoute(String start, String end, List<Road> roads)`: Finds the fastest route using Dijkstra's algorithm.
    - `findBarelyConnectedMap()`: Generates a barely connected map using a minimum spanning tree approach.
- `UnionFind`: A class implementing the Union-Find data structure for cycle detection in the barely connected map.