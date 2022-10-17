package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextFile {
    public static List<List<Integer>> readIntegerData(String path){
        List<List<Integer>> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line = reader.readLine();
            List<Integer> lineData;
            while(line != null) {
                if (!Pattern.matches(".*[a-zA-Zа-яА-Я]+.*", line)) {
                    lineData = new ArrayList<>();
                    String[] numbers = line.split(" ");
                    for (String number : numbers) {
                        lineData.add(Integer.parseInt(number));
                    }
                    data.add(lineData);
                }
                line = reader.readLine();
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return data;
    }
}
