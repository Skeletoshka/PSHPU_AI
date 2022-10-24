import file.TextFile;
import preparation.Input;
import processing.Alghoritms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    //100*100 квадрат/треугольник. Используя маски свернуть до n*n(10*10/5*5) (пуллинг, маска)

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
    }
}