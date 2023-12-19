import file.PictureFile;
import file.TextFile;
import preparation.Input;
import processing.Alghoritms;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static String testPath = "dataset\\test";
    private static String trainPath = "dataset\\train";

    public static void main(String[] args) {
        Date date = new Date();
        //Создаем маску
        Integer[][] mask = new Integer[3][3];
        mask[0][0] = 0;mask[0][1] = 1;mask[0][2] = 0;
        mask[1][0] = 0;mask[1][1] = 1;mask[1][2] = 0;
        mask[2][0] = 1;mask[2][1] = 0;mask[2][2] = 1;
        //Получим все каталоги с изображениями для обучения
        File[] files = new File(trainPath).listFiles((dir, name) -> !name.endsWith(".txt"));
        if(files == null || files.length == 0){
            throw new RuntimeException("Отсутствует тестовое множество");
        }
        List<List<Long>> data = new ArrayList<>();
        /*Arrays.stream(files)
                .forEach(file -> {
                    if(file.listFiles() == null || file.listFiles().length == 0){
                        throw new RuntimeException(String.format("Отсутствуют изображения в каталоге %s", file.getPath()));
                    }
                    Arrays.stream(file.listFiles()).forEach(image -> {
                        try {
                            //Получим байты изображения для свёртывания
                            Long[][] imgBytes = Input.listOfListIntInArr(PictureFile.readIntegerData(image.getPath()));
                            imgBytes = Alghoritms.convolutionalNet(imgBytes, mask, 25, 25);
                            List<List<Integer>> convImageBytes = Input.arrInListOfListInteger(imgBytes);
                            Long[] bytes = Input.addRes(Input.matrixToArray(convImageBytes),
                                    Integer.parseInt(file.getPath().charAt(file.getPath().length() - 1) + ""));
                            data.add(Arrays.asList(bytes));
                            PictureFile.createJPG(imgBytes, "tmp.jpg");
                            System.out.printf("File %s has been processed%n", image.getPath());
                        }catch (Exception e){
                            System.out.printf("File %s has been error%n", image.getPath());
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
        });
        List <Double> eps = new ArrayList<>();
        //Обучаем нейросеть
        Alghoritms.reversErrorDistribution(data, null, eps, 100, false);
        try (FileWriter writer = new FileWriter("error.dat", false)) {
            eps.forEach(val -> {
                try {
                    writer.write(val.toString().replace('.', ','));
                    writer.append("\n");
                } catch (Exception e){
                    throw new RuntimeException(e.getMessage(), e);
                }
            });
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        TextFile.writeData(Alghoritms.dataW, "a.dat");*/
        files = new File(testPath).listFiles((dir, name) -> !name.endsWith(".txt"));
        if(files == null || files.length == 0){
            throw new RuntimeException("Отсутствует тестовое множество");
        }
        data.clear();
        //Проверка
        Arrays.stream(files)
                .forEach(file -> {
                    if(file.listFiles() == null || file.listFiles().length == 0){
                        throw new RuntimeException(String.format("Отсутствуют изображения в каталоге %s", file.getPath()));
                    }
                    Arrays.stream(file.listFiles()).forEach(image -> {
                        try {
                            //Получим байты изображения для свёртывания
                            Long[][] imgBytes = Input.listOfListIntInArr(PictureFile.readIntegerData(image.getPath()));
                            imgBytes = Alghoritms.convolutionalNet(imgBytes, mask, 25, 25);
                            List<List<Integer>> convImageBytes = Input.arrInListOfListInteger(imgBytes);
                            Long[] bytes = Input.addRes(Input.matrixToArray(convImageBytes),
                                    Integer.parseInt(file.getPath().charAt(file.getPath().length() - 1) + ""));
                            data.add(Arrays.asList(bytes));
                            PictureFile.createJPG(imgBytes, "tmp.jpg");
                            System.out.printf("File %s has been processed%n", image.getPath());
                        }catch (Exception e){
                            System.out.printf("File %s has been error%n", image.getPath());
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
                });
        Alghoritms.reversErrorDistribution(data, null, new ArrayList<>(), 1, true);
        System.out.println((new Date().getTime() - date.getTime())/1000);
    }
}