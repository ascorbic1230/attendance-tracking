package org.example.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppUtil {
    private static final String logoImgPath = "./assets/images/logo.png";
    private static final String appNameVn = "Phần mềm điểm danh";
    public static String username = null;

    public static Image getAppLogo() {
        return new ImageIcon(logoImgPath).getImage();
    }

    public static String getAppNameVn() {
        return appNameVn;
    }

    public static Date getNow() {
        return new Date();
    }

    // For test only
//    public static Date getNow() {
//        String str = "12/04/2022 08:00";
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date date = new Date();
//        try {
//            date = formatter.parse(str);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return date;
//    }

    public static String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1 -> { return "Chủ nhật"; }
            case 2 -> { return "Thứ hai"; }
            case 3 -> { return "Thứ ba"; }
            case 4 -> { return "Thứ tư"; }
            case 5 -> { return "Thứ năm"; }
            case 6 -> { return "Thứ sáu"; }
            case 7 -> { return "Thứ bảy"; }
            default -> { return null; }
        }
    }

    public static int getDayOfWeekInteger(String dayOfWeek) {
        if ("Chủ nhật".equals(dayOfWeek)) {
            return 1;
        } else if ("Thứ hai".equals(dayOfWeek)) {
            return 2;
        } else if ("Thứ ba".equals(dayOfWeek)) {
            return 3;
        } else if ("Thứ tư".equals(dayOfWeek)) {
            return 4;
        } else if ("Thứ năm".equals(dayOfWeek)) {
            return 5;
        } else if ("Thứ sáu".equals(dayOfWeek)) {
            return 6;
        } else if ("Thứ bảy".equals(dayOfWeek)) {
            return 7;
        }
        return -1;
    }

    public static void createCsvTemplate(String dirPath) throws IOException {
        String[] header = {"MSSV", "Họ tên"};
        List<String[]> csvData = new ArrayList<>();
        csvData.add(header);

        String filePath = dirPath + "/template.csv";

        OutputStream os = new FileOutputStream(filePath);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

        os.write(239);
        os.write(187);
        os.write(191);

        try (CSVWriter writer = new CSVWriter(out)) {
            writer.writeAll(csvData);
        }
    }

    public static List<String[]> readCsvFile(String filePath) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSameTime(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        if (start1.compareTo(start2) <= 0 && end1.compareTo(start2) >= 0)
            return true;
        else return start1.compareTo(end2) <= 0 && end1.compareTo(end2) >= 0;
    }
}
