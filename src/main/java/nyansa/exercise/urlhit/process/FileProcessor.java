package nyansa.exercise.urlhit.process;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nyansa.exercise.urlhit.utils.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TODO: Write README.md with assumptions
// File is assumed to be valid
// Allocating 8MB of memory for the map at each iteration

@Getter
@Setter
@Log4j2
public class FileProcessor {
    private String fileName;
    private long recordCounter;
    private Properties properties;
    private Map<String, Map<String, Long>> result;

    public FileProcessor(String fileName, Properties properties) {
        this.fileName = fileName;
        this.properties = properties;
        recordCounter = 0L;
        result = new HashMap<>();
    }

    /*
     * 1. Read each line up READ_LIMIT amount of records at a time
     * 2. Process each record and write to temp files after every 100K records
     * 3. Write any remaining records to temp file
     * 4. Write output to the console
     * 5. clean up all the temp files created
     */
    public void process() {
        log.trace("start file processing");

        try (Stream<String> records = Files.lines(Paths.get(fileName))) {
            log.info("processing records from the file: " + fileName);

            records.forEach(record -> {
                try {
                    processRecord(record);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writeToFiles();
            writeOutputToConsole();
            cleanup();

            log.info("completed processing all the records");
        } catch (IOException e) {
            log.error("could not process the records in the input file. ", e);
        }

        log.trace("end file processing");
    }

    /*
     * For each key in the result map create a file with key as the ame
     * Write the nested map to the file
     * If the file already exists, update the map with the current count for each URL
     */
    private void writeToFiles() throws IOException {
        log.trace("start writing to temp files");
//        try {
        log.debug("total number of files being written: " + result.size());
        log.info("writing result map to temp files");

        for (Map.Entry<String, Map<String, Long>> entry : result.entrySet()) {
            String filePath = properties.getProperty("temppath") + entry.getKey();
            Map<String, Long> currentUrlCountMap = entry.getValue();

            if (Files.exists(Paths.get(filePath))) {
                log.debug("updating the map from the existing file: " + filePath);

                // Read the file into a temporary map
                Map<String, Long> tempMap = populateTempMap(Paths.get(filePath));

                // Merge two maps
                tempMap.forEach((key1, value1) -> currentUrlCountMap.merge(key1, value1,
                        (key, value) -> value1 + value));
            }

            String mapAsString = currentUrlCountMap.keySet().stream()
                    .map(key -> key + "," + currentUrlCountMap.get(key))
                    .collect(Collectors.joining("\n"));

            log.debug("writing to the file: " + filePath);
            // write/rewrite back to the original file
            Files.write(Paths.get(filePath), mapAsString.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        log.trace("end writing to temp files");
    }


    /*
     * For reach record
     * Split the line
     * Get the GMT time
     * Update the result map
     * For every READ_LIMIT amount of records, write the content of the map to file
     */
    private void processRecord(String record) throws IOException {
        log.trace("start processing record: " + record);

        if (!record.isEmpty()) {
            String[] recordFields = record.split("\\|");
            if (recordFields.length == 2) {
                String GMTDate = Util.getGMTDate(recordFields[0]);
                String url = recordFields[1];
                if (!GMTDate.isEmpty() && !url.isEmpty()) {
                    log.debug("processing record: " + record);

                    // if the result map does not have an entry for the date yet then create one
                    if (!result.containsKey(GMTDate)) {
                        Map<String, Long> urlCountMap = new HashMap<>();
                        urlCountMap.put(url, 1L);
                        result.put(GMTDate, urlCountMap);
                    } else {
                        // Update url hit count if it is already encountered for the given date else initialize to 1
                        result.get(GMTDate).merge(url, 1L, Long::sum);
                    }

                    recordCounter++;

                    log.debug("total records processed: " + recordCounter);

                    if (recordCounter % Long.parseLong(properties.getProperty("readlimit")) == 0) {
                        log.info("writing to file and resetting the result map");
                        writeToFiles();
                        result = new HashMap<>();
                    }
                } else {
                    log.warn("record is invalid. " + record);
                }
            } else {
                log.warn("record is invalid. " + record);
            }
        }

        log.trace("end processing record: " + record);
    }

    /*
     * Sort the temp files in ascending order based on time
     * Populated each file into a map in value sorted order
     * Print the output to console
     */
    private void writeOutputToConsole() {
        log.trace(" start writing output to console");
        log.info("processing all the temp files and writing output");

        List<File> tempFiles = Arrays.asList(Paths.get(properties.getProperty("temppath")).toFile().listFiles());
        Collections.sort(tempFiles);
        log.debug("Total number of distinct dates in the input: " + tempFiles.size());

        for (File f : tempFiles) {
            System.out.println(f.getName().replaceAll("-", "\\/"));
            try {
                Map<String, Long> tempMap = populateTempMap(f.toPath());
                tempMap = Util.sortByValue(tempMap);
                tempMap.forEach((k, v) -> System.out.println((k + " " + v)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.trace(" end writing output to console");
    }

    private void cleanup() {
        log.trace("start cleanup of temp files");
        Arrays.stream(new File(properties.getProperty("temppath")).listFiles()).forEach(File::delete);
        log.trace("end cleanup of temp files");
    }


    private Map<String, Long> populateTempMap(Path p) throws IOException {
        log.trace("start populating map from the file: " + p.toFile().toString());
        Map<String, Long> tempMap = new HashMap<>();
        try (Stream<String> existingRecords = Files.lines(p)) {
            existingRecords.forEach(existingRecord -> {
                String[] recordFields = existingRecord.split("\\,");
                tempMap.put(recordFields[0], Long.parseLong(recordFields[1]));
            });
        }
        log.trace("end populating map");
        return tempMap;
    }
}
