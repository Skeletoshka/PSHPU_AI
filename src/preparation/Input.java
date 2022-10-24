package preparation;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Input {

    /**Заполнение квадратной матрицы рандомными числами
     * @param xN разрядность матрицы
     * @param xMin минимальный элемент
     * @param xMax минимальный элемент
     * */
    public static Double[][] randMatrix(int xN, int xMin, int xMax){
        Double[][] dataW = new Double[xN][xN];
        Random rnd = new Random();
        //Заполним значения w
        for(int i = 0; i < xN; i++){
            for(int j = 0; j < xN; j++){
                dataW[i][j] = (double) ((rnd.nextInt(xMax * 100) - abs(xMin) * 100) / 100);
            }
        }
        return dataW;
    }
}
