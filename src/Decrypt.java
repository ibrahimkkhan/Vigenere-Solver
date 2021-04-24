package src;
/* Mohammed Khan */
import java.util.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.*;// Import the FileWriter class
import java.util.concurrent.TimeUnit;
// Import this class to handle errors

public class Decrypt {
    static ArrayList<String> hex = new ArrayList<String>();
    static ArrayList<HashMap<String, Integer>> allFreqsLength = new ArrayList<HashMap<String, Integer>>();
    static HashMap<Integer, HashMap<String, Integer>> highestFrequenciesLength = new HashMap<Integer, HashMap<String, Integer>>();
    static HashMap<Integer, HashMap<String, Integer>> allFrequenciesCipher = new HashMap<Integer, HashMap<String, Integer>>();
    static HashMap<Integer, HashMap<String, Integer>> highestFrequenciesCipher = new HashMap<Integer, HashMap<String, Integer>>();
    static int finalKeyLength = 0;
    static String finalKey = "";

    public static void main(String[] args) {
        readfile();
        arrangeData();
        findLength(); // key length is 7
        findHighFreqAtIndexCipher();
        findKey(32); // works with ' ' (can be tried with 101 i.e, ASCII 'e' or other frequent ASCII)
        System.out.println("Key in decimal - " + finalKey);

    }

    public static void readfile() {
        try {
            File myObj = new File("ctext.txt");
            Scanner myReader = new Scanner(myObj);
            String data = "";
            int counter = 0;
            data = myReader.next();

            for (int i = 0; i < data.length(); i += 2) {
                hex.add("" + data.charAt(i) + data.charAt(i + 1));
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void arrangeData() {
        allFreqsLength.add(new HashMap<String, Integer>());

        for (int i = 0; i < hex.size(); i = i + 1) {
            helperArrangeData(0, i);
            if (i % 2 == 0) {

                helperArrangeData(1, i);

            }
            if (i % 3 == 0) {

                helperArrangeData(2, i);

            }
            if (i % 4 == 0) {

                helperArrangeData(3, i);

            }
            if (i % 5 == 0) {

                helperArrangeData(4, i);

            }
            if (i % 6 == 0) {

                helperArrangeData(5, i);

            }
            if (i % 7 == 0) {

                helperArrangeData(6, i);

            }
            if (i % 8 == 0) {

                helperArrangeData(7, i);

            }
            if (i % 9 == 0) {

                helperArrangeData(8, i);

            }
            if (i % 10 == 0) {

                helperArrangeData(9, i);

            }
            if (i % 11 == 0) {

                helperArrangeData(10, i);

            }
            if (i % 12 == 0) {

                helperArrangeData(11, i);

            }

            if (i % 13 == 0) {

                helperArrangeData(12, i);

            }
            if (i % 14 == 0) {

                helperArrangeData(13, i);

            }
            if (i % 15 == 0) {

                helperArrangeData(14, i);

            }
        }

    }

    static void helperArrangeData(int listOfAllFreqsIndex, int i) {
        if (allFreqsLength.size() <= listOfAllFreqsIndex) {
            HashMap<String, Integer> m = new HashMap<>();
            allFreqsLength.add(listOfAllFreqsIndex, m);
        }

        if (allFreqsLength.get(listOfAllFreqsIndex).containsKey(hex.get(i))) {
            allFreqsLength.get(listOfAllFreqsIndex).put(hex.get(i),
                    allFreqsLength.get(listOfAllFreqsIndex).get(hex.get(i)) + 1);
        } else {
            allFreqsLength.get(listOfAllFreqsIndex).put(hex.get(i), 1);
        }

    }

    static void findLength() {
        int mainMax = 0;
        String main_corres = "";
        for (int i = 0; i < allFreqsLength.size(); i++) {
            int max = 0;
            String corres = "";
            for (String s : allFreqsLength.get(i).keySet()) {
                if (allFreqsLength.get(i).get(s) > max) {
                    max = allFreqsLength.get(i).get(s);
                    corres = s;
                }
            }
            HashMap<String, Integer> temp = new HashMap<String, Integer>();
            temp.put(corres, max);
            highestFrequenciesLength.put(i, temp);
            if (max > mainMax) {
                finalKeyLength = i + 1;
                mainMax = max;
                main_corres = corres;

            }
        }

        finalKeyLength = 7;
    }

    static void findHighFreqAtIndexCipher() {
        for (int i = 0; i < finalKeyLength; i++) {
            for (int j = i; j < hex.size(); j += finalKeyLength) {
                String s = hex.get(j);
                if (allFrequenciesCipher.size() <= i) {
                    HashMap<String, Integer> temp = new HashMap<String, Integer>();
                    allFrequenciesCipher.put(i, temp);
                }

                if (allFrequenciesCipher.get(i).containsKey(s)) {
                    allFrequenciesCipher.get(i).put(s, allFrequenciesCipher.get(i).get(s) + 1);
                } else {
                    allFrequenciesCipher.get(i).put(s, 1);
                }

            }
        }

        for (int i = 0; i < allFrequenciesCipher.size(); i++) {
            int max = 0;
            String corres = "";
            for (String s : allFrequenciesCipher.get(i).keySet()) {
                if (allFrequenciesCipher.get(i).get(s) > max) {
                    max = allFrequenciesCipher.get(i).get(s);
                    corres = s;
                }
            }
            HashMap<String, Integer> temp = new HashMap<String, Integer>();
            temp.put(corres, max);
            highestFrequenciesCipher.put(i, temp);
        }
    }

    static void findKey(int numRaise) {
        Character[] array = new Character[hex.size()];

        for (int i = 0; i < finalKeyLength; i++) {
            Map.Entry<String, Integer> entry = highestFrequenciesCipher.get(i).entrySet().iterator().next();
            String key = entry.getKey();
            char c0Key = key.charAt(0);
            char c1Key = key.charAt(1);
            int numKey = Integer.parseInt("" + c0Key + c1Key, 16);
            numKey = myXOR(numKey, numRaise);
            // char c0c1Key = (char) numKey;
            finalKey += Integer.toString(numKey) + " ";
            

            for (int j = i; j < hex.size(); j += finalKeyLength) {
                int temp = 0;
                String s = hex.get(j);

                char c0 = s.charAt(0);
                char c1 = s.charAt(1);
                int num = Integer.parseInt("" + c0 + c1, 16);

                num = myXOR(num, numKey);

                char c0c1 = (char) num;
                array[j] = c0c1;
            }
        }

        try {
            FileWriter myWriter = new FileWriter("ptext.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            for (int b = 0; b < array.length; b++) {
                bw.write(array[b]);
            }

            bw.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    static int myXOR(int x, int y) {

        // Initialize result
        int res = 0;

        // Assuming 32-bit Integer
        for (int i = 31; i >= 0; i--) {

            // Find current bits in x and y
            int b1 = ((x & (1 << i)) == 0) ? 0 : 1;
            int b2 = ((y & (1 << i)) == 0) ? 0 : 1;

            // If both are 1 then 0 else xor is same as OR
            int xoredBit = ((b1 & b2) != 0) ? 0 : (b1 | b2);

            // Update result
            res <<= 1;
            res |= xoredBit;
        }
        return res;
    }

}