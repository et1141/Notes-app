package com.example.notes;

import android.content.Context;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

class CSVManager {
    private final Context context;

    public CSVManager(Context context) {
        this.context = context;
    }

    public List<List<String>> readCSVFromFile(String fileName) {
        List<List<String>> listOfLists = new ArrayList<>();
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream);

            CSVReader csvReader = new CSVReaderBuilder(reader).build();

            List<String[]> allData = csvReader.readAll();

            for (String[] row : allData) {
                listOfLists.add(new ArrayList<>(List.of(row)));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return listOfLists;
    }

    public void writeCSVToFile(String fileName, List<List<String>> list) {
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(',')
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapeChar(CSVWriter.NO_ESCAPE_CHARACTER)
                    .build();

            for (List<String> row : list) {
                String[] rowArray = row.toArray(new String[0]);
                csvWriter.writeNext(rowArray);
            }

            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
