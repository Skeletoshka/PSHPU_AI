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
        List<List<Integer>> data = TextFile.readIntegerData("InputData.txt");
        List<Double> eps = new ArrayList<>();
        Alghoritms.reversErrorDistribution(data, null, eps, 50000);
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
        Alghoritms.reversErrorDistributionResult(data, null);

        List<List<Integer>> bitesCircle = PictureFile.readIntegerData("Circle.tiff");
        List<List<Integer>> bitesCircle2 = PictureFile.readIntegerData("Circle_2.tiff");
        List<List<Integer>> bitesCircle3 = PictureFile.readIntegerData("Circle_3.tiff");
        List<List<Integer>> bitesCircle4 = PictureFile.readIntegerData("Circle_4.tiff");
        List<List<Integer>> bitesTriangle = PictureFile.readIntegerData("Triangle.tiff");
        List<List<Integer>> bitesTriangle2 = PictureFile.readIntegerData("Triangle_2.tiff");
        List<List<Integer>> bitesTriangle3 = PictureFile.readIntegerData("Triangle_3.tiff");
        List<List<Integer>> bitesTriangle4 = PictureFile.readIntegerData("Triangle_4.tiff");

        Long[][] biteMatrixTriangle = Input.listOfListIntInArr(bitesTriangle);
        Long[][] biteMatrixTriangle2 = Input.listOfListIntInArr(bitesTriangle2);
        Long[][] biteMatrixTriangle3 = Input.listOfListIntInArr(bitesTriangle3);
        Long[][] biteMatrixTriangle4 = Input.listOfListIntInArr(bitesTriangle4);
        Long[][] biteMatrixCircle = Input.listOfListIntInArr(bitesCircle);
        Long[][] biteMatrixCircle2 = Input.listOfListIntInArr(bitesCircle2);
        Long[][] biteMatrixCircle3 = Input.listOfListIntInArr(bitesCircle3);
        Long[][] biteMatrixCircle4 = Input.listOfListIntInArr(bitesCircle4);

        Integer[][] mask = new Integer[3][3];
        mask[0][0] = 0;mask[0][1] = 1;mask[0][2] = 0;
        mask[1][0] = 0;mask[1][1] = 1;mask[1][2] = 0;
        mask[2][0] = 1;mask[2][1] = 0;mask[2][2] = 1;
        TextFile.writeData(mask, "mask.dat");
        biteMatrixTriangle = Alghoritms.convolutionalNet(biteMatrixTriangle, mask, 10, 10);
        biteMatrixTriangle2 = Alghoritms.convolutionalNet(biteMatrixTriangle2, mask, 10, 10);
        biteMatrixTriangle3 = Alghoritms.convolutionalNet(biteMatrixTriangle3, mask, 10, 10);
        biteMatrixTriangle4 = Alghoritms.convolutionalNet(biteMatrixTriangle4, mask, 10, 10);
        biteMatrixCircle = Alghoritms.convolutionalNet(biteMatrixCircle, mask, 10, 10);
        biteMatrixCircle2 = Alghoritms.convolutionalNet(biteMatrixCircle2, mask, 10, 10);
        biteMatrixCircle3 = Alghoritms.convolutionalNet(biteMatrixCircle3, mask, 10, 10);
        biteMatrixCircle4 = Alghoritms.convolutionalNet(biteMatrixCircle4, mask, 10, 10);

        PictureFile.createJPG(biteMatrixTriangle, "img/tempTriangle.jpg");
        PictureFile.createJPG(biteMatrixCircle, "img/tempCircle.jpg");

        Integer[] biteArrTriangle = Input.matrixToArray(Input.longInArr(biteMatrixTriangle));
        Integer[] biteArrTriangle2 = Input.matrixToArray(Input.longInArr(biteMatrixTriangle2));
        Integer[] biteArrTriangle3 = Input.matrixToArray(Input.longInArr(biteMatrixTriangle3));
        Integer[] biteArrTriangle4 = Input.matrixToArray(Input.longInArr(biteMatrixTriangle4));
        Integer[] biteArrCircle = Input.matrixToArray(Input.longInArr(biteMatrixCircle));
        Integer[] biteArrCircle2 = Input.matrixToArray(Input.longInArr(biteMatrixCircle2));
        Integer[] biteArrCircle3 = Input.matrixToArray(Input.longInArr(biteMatrixCircle3));
        Integer[] biteArrCircle4 = Input.matrixToArray(Input.longInArr(biteMatrixCircle4));

        Integer[][] concArr = Input.concatenateArr(Input.addRes(biteArrTriangle, 0), Input.addRes(biteArrCircle, 1));
        Integer[][] concArr2 = Input.concatenateArr(Input.addRes(biteArrTriangle2, 0), Input.addRes(biteArrCircle2, 1));
        Integer[][] concArr3 = Input.concatenateArr(concArr, concArr2);
        concArr = Input.concatenateArr(Input.addRes(biteArrTriangle3, 0), Input.addRes(biteArrCircle3, 1));
        concArr2 = Input.concatenateArr(Input.addRes(biteArrTriangle4, 0), Input.addRes(biteArrCircle4, 1));
        concArr = Input.concatenateArr(concArr, concArr2);
        concArr3 = Input.concatenateArr(concArr, concArr3);
        eps = new ArrayList<>();
        Alghoritms.reversErrorDistribution(Input.arrInListOfList(concArr3), null, eps, 50000);
        try (FileWriter writer = new FileWriter("a.dat", false)) {
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

        Alghoritms.reversErrorDistributionResult(Input.arrInListOfList(concArr3), null);

        //Тест на не обучаемых данных
        List<List<Integer>> bitesTest = PictureFile.readIntegerData("Test.tiff");
        Long[][] biteMatrixTest = Input.listOfListIntInArr(bitesTest);
        biteMatrixTest = Alghoritms.convolutionalNet(biteMatrixTest, mask, 10, 10);
        Integer[] biteArrTest = Input.matrixToArray(Input.longInArr(biteMatrixTest));
        Alghoritms.reversErrorDistributionResult(Input.arrInListOfList(Input.concatenateArr(Input.addRes(biteArrTest, 0), Input.addRes(biteArrTest, 0))), null);

    }
}