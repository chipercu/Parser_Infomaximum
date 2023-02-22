package org.fuzzy;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.fuzzy.model.ParsObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.fuzzy.Util.*;

public class Parser {

    private final String file_path;

    private BigInteger MIN_WEIGHT = BigInteger.valueOf(Long.MAX_VALUE);
    private BigInteger MAX_WEIGHT = BigInteger.valueOf(Long.MIN_VALUE);

    private final Map<String, Counter> weightPerGroups = new HashMap<>();

    private final String JSON_EXTENSION = ".json";
    private final String CSV_EXTENSION = ".csv";

    public Parser(String filePath) {
        this.file_path = filePath;
    }

    private boolean checkExtension(File file) {
        String fileName = file.getName();
        return fileName.endsWith(JSON_EXTENSION) || fileName.endsWith(CSV_EXTENSION);
    }

    public void parse() {
        final File file = new File(file_path);
        if (!file.exists()) {
            printFileNotFound();
            return;
        }
        if (checkExtension(file)) {
            if (file.getName().endsWith(JSON_EXTENSION)) {
                jsonParser(file);
            } else if (file.getName().endsWith(CSV_EXTENSION)) {
                parseCSV(file);
            }
            printInfo();
        } else {
            wrongFileExtension();
        }
    }
    private void printInfo(){
        for (Map.Entry<String, Counter> entry : weightPerGroups.entrySet()) {
            final Counter value = entry.getValue();
            printGroupObjectInfo(entry.getKey(), value.count, value.weight);
        }
        printMaxWeight(MAX_WEIGHT);
        printMinWeight(MIN_WEIGHT);
    }

    private void jsonParser(File file){
        try (JsonParser jParser = new JsonFactory().createParser(file)) {
            if (jParser.nextToken() == JsonToken.START_ARRAY) {
                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    ParsObject object = new ParsObject();
                    while (jParser.nextToken() != JsonToken.END_OBJECT) {

                        String fieldname = jParser.getCurrentName();
                        if ("group".equals(fieldname)) {
                            jParser.nextToken();
                            object.setGroup(jParser.getText());
                        }
                        if ("type".equals(fieldname)) {
                            jParser.nextToken();
                            object.setType(jParser.getText());
                        }
                        if ("number".equals(fieldname)) {
                            jParser.nextToken();
                            object.setNumber(jParser.getLongValue());
                        }
                        if ("weight".equals(fieldname)) {
                            jParser.nextToken();
                            object.setWeight(BigInteger.valueOf(jParser.getLongValue()));
                        }
                    }
                    updateInfo(object.getGroup(), object.getWeight());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseCSV(File file) {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()) , StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splited = line.split(",");
                if (splited[0].equals("group")){
                    continue;
                }
                String group = splited[0];
                String type = splited[1];
                long number = Long.parseLong(splited[2]);
                BigInteger weight = BigInteger.valueOf(Long.parseLong(splited[3]));

                updateInfo(group, weight);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo(String group, BigInteger weight){

        if (weight.compareTo(MAX_WEIGHT) > 0){
            MAX_WEIGHT = weight;
        }
        if (weight.compareTo(MIN_WEIGHT) < 0){
            MIN_WEIGHT = weight;
        }

//        MAX_WEIGHT = Math.max(weight, MAX_WEIGHT);
//        MIN_WEIGHT = Math.min(weight, MIN_WEIGHT);

        if (!weightPerGroups.containsKey(group)){
            weightPerGroups.put(group, new Counter(weight));
        }else {
            final Counter counter = weightPerGroups.get(group);
            counter.setCount(counter.getCount() + 1);
            counter.setWeight(weight.add(counter.getWeight()));
            weightPerGroups.put(group, counter);
        }
    }
    private static class Counter{
        int count;
        BigInteger weight;

        public Counter(BigInteger weight) {
            this.count = 1;
            this.weight = weight;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public BigInteger getWeight() {
            return weight;
        }

        public void setWeight(BigInteger weight) {
            this.weight = weight;
        }
    }

}
