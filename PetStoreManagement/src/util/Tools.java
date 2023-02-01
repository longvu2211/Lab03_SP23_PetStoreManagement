package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tools {
    private static Scanner sc = new Scanner(System.in);
    public static int getAnInteger(String inputMsg, String errorMsg) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getADouble(String inputMsg, String errorMsg) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    // For using in ui.Menu
    public static int getAnInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        int n, tmp;
        // if lower > upper then swap (lower, upper)
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < lowerBound || n > upperBound)
                    throw new Exception();
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    // Enter a non-blanked inputted string
    public static String getString(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim();
            if (id.length() == 0 || id.isEmpty())
                System.out.println(errorMsg);
            else
                return id;
        }
    }

    public static String getStringFormat(String inputMsg, String errorMsg, String format) {
        String id;
        boolean match;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim();
            match = id.matches(format);
            if (id.length() == 0 || id.isEmpty() || match == false) {
                System.out.println(errorMsg);
            } else
                return id;
        }
    }
    // Read data from a file
    public static List<String> readLineFromFile(String fileName) {
        List listTxt = new ArrayList<>();
        File f = new File(fileName);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty())
                        listTxt.add(line);
                }
                br.close();
                fr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return listTxt;
    }
    // Save data to a file
    public static void writeListToFile(String fName, List<String> content) {
        try {
            File f = new File(fName);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);

            for (String x : content) {
                pw.println(x);
            }
            pw.close();
            fw.close();
            // System.out.println("Saved successfully!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
