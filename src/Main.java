import file.PictureFile;
import file.TextFile;
import preparation.Input;
import processing.Alghoritms;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static String testPath = "archive\\test";
    private static String trainPath = "archive\\train";
    private static String preparePath = "archive";

    public static void main(String[] args) throws IOException {
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
        Map<String, Integer> trainData = Input.readCsv(preparePath + "\\train.csv");
        Arrays.stream(files)
                .forEach(file -> {
                    if(file.listFiles() == null || file.listFiles().length == 0){
                        throw new RuntimeException(String.format("Отсутствуют изображения в каталоге %s", file.getPath()));
                    }
                    Arrays.stream(file.listFiles()).parallel().forEach(image -> {
                        try {
                            //Получим байты изображения для свёртывания
                            Long[][] imgBytes = Input.listOfListIntInArr(PictureFile.readIntegerData(image.getPath(), 1000));
                            imgBytes = Alghoritms.convolutionalNet(imgBytes, mask, 50, 50);
                            List<List<Integer>> convImageBytes = Input.arrInListOfListInteger(imgBytes);
                            String path = image.getPath().substring(preparePath.length() + 1);
                            Long[] bytes = Input.addRes(Input.matrixToArray(convImageBytes),
                                    trainData.get(path.replace("\\", "/")));
                            data.add(Arrays.asList(bytes));
                            System.out.printf("File %s has been processed%n", image.getPath());
                        }catch (Exception e){
                            System.out.printf("File %s has been error%n", image.getPath());
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
        });
        List <Double> eps = new ArrayList<>();
        //Обучаем нейросеть
        Alghoritms.reversErrorDistribution(data, null, eps, 20);
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
        TextFile.writeData(Alghoritms.dataW, "a.dat");
        files = new File(trainPath).listFiles((dir, name) -> !name.endsWith(".txt"));
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
                    Arrays.stream(file.listFiles()).parallel().forEach(image -> {
                        try {
                            //Получим байты изображения для свёртывания
                            Long[][] imgBytes = Input.listOfListIntInArr(PictureFile.readIntegerData(image.getPath(), 1000));
                            imgBytes = Alghoritms.convolutionalNet(imgBytes, mask, 50, 50);
                            List<List<Integer>> convImageBytes = Input.arrInListOfListInteger(imgBytes);
                            Long[] bytes = Input.matrixToArray(convImageBytes);
                            data.add(Arrays.asList(bytes));
                            System.out.printf("File %s has been processed%n", image.getPath());
                        }catch (Exception e){
                            System.out.printf("File %s has been error%n", image.getPath());
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
                });
        Alghoritms.reversErrorDistributionResult(data, null);

        //Тест на не обучаемых данных
        /*List<List<Integer>> bitesTest = PictureFile.readIntegerData("Test.tiff");
        Long[][] biteMatrixTest = Input.listOfListIntInArr(bitesTest);
        biteMatrixTest = Alghoritms.convolutionalNet(biteMatrixTest, mask, 10, 10);
        Integer[] biteArrTest = Input.matrixToArray(Input.longInArr(biteMatrixTest));
        Alghoritms.reversErrorDistributionResult(Input.arrInListOfList(Input.concatenateArr(Input.addRes(biteArrTest, 0),
                Input.addRes(biteArrTest, 0))), null);*/
        System.out.println((new Date().getTime() - date.getTime())/1000);
    }
}