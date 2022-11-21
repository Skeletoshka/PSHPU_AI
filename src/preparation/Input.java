package preparation;

import java.util.ArrayList;
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

    public static Integer[][] listOfListInArr(List<List<Integer>> data, int res){
        Integer[][] outputData = new Integer[data.size()][data.get(0).size() + 1];
        for(int i = 0; i < data.size(); i++){
            List<Integer> temp = data.get(i);
            for(int j = 0; j < temp.size() + 1; j++){
                if(j < temp.size()) {
                    outputData[i][j] = temp.get(j);
                }else{
                    outputData[i][j] = res;
                }
            }
        }
        return outputData;
    }
    public static Integer[][] listOfListInArr(List<List<Integer>> data){
        Integer[][] outputData = new Integer[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            List<Integer> temp = data.get(i);
            for(int j = 0; j < temp.size(); j++){
                outputData[i][j] = temp.get(j);
            }
        }
        return outputData;
    }

    public static Long[][] listOfListLongInArr(List<List<Long>> data){
        Long[][] outputData = new Long[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            List<Long> temp = data.get(i);
            for(int j = 0; j < temp.size(); j++){
                outputData[i][j] = temp.get(j);
            }
        }
        return outputData;
    }

    public static Long[][] listOfListIntInArr(List<List<Integer>> data){
        Long[][] outputData = new Long[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            List<Integer> temp = data.get(i);
            for(int j = 0; j < temp.size(); j++){
                outputData[i][j] = (long) temp.get(j);
            }
        }
        return outputData;
    }

    public static List<List<Long>> arrInListOfList(Long[][] data){
        List<List<Long>> outputData = new ArrayList<>();
        for(int i = 0; i < data.length; i++){
            List<Long> line = new ArrayList<>();
            for(int j = 0; j < data[0].length; j++){
                line.add(data[i][j]);
            }
            outputData.add(line);
        }
        return outputData;
    }

    public static Integer[][] concatenateArr(Integer[][] arr1, Integer[][] arr2){
        Integer[][] outputData = new Integer[arr1.length + arr2.length][arr1[0].length];
        for(int i = 0; i < arr1.length; i++){
            for(int j = 0; j < arr1[0].length; j++){
                outputData[i][j] = arr1[i][j];
                outputData[i + arr1.length][j] = arr2[i][j];
            }
        }
        return outputData;
    }
}
