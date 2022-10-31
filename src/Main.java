import file.PictureFile;
import file.TextFile;
import preparation.Input;
import processing.Alghoritms;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Main {
    //100*100 квадрат/треугольник. Используя маски свернуть до n*n(10*10/5*5) (пуллинг, маска)

    public static void main(String[] args) {
        //Получим на вход матрицу значений
        /*List<List<Integer>> data = TextFile.readIntegerData("InputData.txt");
        List<Double> eps = new ArrayList<>();
        Alghoritms.reversErrorDistribution(data, null, eps, 5);
        try (FileWriter writer = new FileWriter("b.dat", false)) {
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
        Alghoritms.reversErrorDistributionResult(data, null);*/

        List<List<Integer>> bitesTriangle = PictureFile.readIntegerData("Triangle.tiff");
        Integer[][] biteMatrixTriangle = Input.listOfListInArr(bitesTriangle, 1);
        List<List<Integer>> bitesCircle = PictureFile.readIntegerData("Circle.tiff");
        Integer[][] biteMatrixCircle = Input.listOfListInArr(bitesCircle, 0);
        Integer[][] allBites = Input.concatenateArr(biteMatrixCircle, biteMatrixTriangle);
        bitesCircle.addAll(bitesTriangle);

        List<List<Integer>> bitesWidow = PictureFile.readIntegerData("img/widow.tiff");
        Integer[][] biteMatrixWidow = Input.listOfListInArr(bitesWidow, 0);

        try (FileWriter writer = new FileWriter("widow.dat", false)) {
            Arrays.stream(biteMatrixWidow).forEach(bytesRow -> {
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