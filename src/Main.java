import file.PictureFile;
import file.TextFile;
import preparation.Input;
import processing.Alghoritms;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {

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

        List<List<Integer>> bitesCircle = PictureFile.readIntegerData("Circle.tiff");
        List<List<Integer>> bitesTriangle = PictureFile.readIntegerData("Triangle.tiff");

        Long[][] biteMatrixTriangle = Input.listOfListIntInArr(bitesTriangle);
        Long[][] biteMatrixCircle = Input.listOfListIntInArr(bitesCircle);

        Integer[][] mask = new Integer[3][3];
        mask[0][0] = 0;mask[0][1] = 1;mask[0][2] = 0;
        mask[1][0] = 0;mask[1][1] = 1;mask[1][2] = 0;
        mask[2][0] = 1;mask[2][1] = 0;mask[2][2] = 1;
        TextFile.writeData(mask, "mask.dat");
        biteMatrixTriangle = Alghoritms.convolutionalNet(biteMatrixTriangle, mask, 10, 10);
        biteMatrixCircle = Alghoritms.convolutionalNet(biteMatrixCircle, mask, 10, 10);

        PictureFile.createJPG(biteMatrixTriangle, "img/tempTriangle.jpg");
        PictureFile.createJPG(biteMatrixCircle, "img/tempCircle.jpg");

        /*List<List<Integer>> bitesCircle = PictureFile.readIntegerData("Circle.tiff");
        Integer[][] biteMatrixCircle = Input.listOfListInArr(bitesCircle, 0);
        Integer[][] allBites = Input.concatenateArr(biteMatrixCircle, biteMatrixTriangle);
        bitesCircle.addAll(bitesTriangle);*/
    }
}