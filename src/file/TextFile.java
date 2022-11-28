package file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class TextFile {

    /**Считывает целочисленные данные из файла
     * @param path Путь к файлу
     * @return Список списков этих данных*/
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

    /**Записывает данные в файл
     * @param data Данные для записи в файл
     * @param fileName Путь к файлу*/
    public static void writeData(Object[][] data, String fileName){
        try (FileWriter writer = new FileWriter(fileName, false)) {
            Arrays.stream(data).forEach(bytesRow -> {
                try {
                    Arrays.stream(bytesRow).forEach(val -> {
                        try {
                            writer.write(val + " ");
                        } catch (Exception e){
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
                    writer.append("\n");
                } catch (Exception e){
                    throw new RuntimeException(e.getMessage(), e);
                }
            });
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
