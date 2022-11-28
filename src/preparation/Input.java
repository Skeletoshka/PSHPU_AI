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

    /**Преобразование матрицы LONG в матрицу Integer
     * @param data Входные данные
     */
    public static Integer[][] longInArr(Long[][] data){
        Integer[][] outputData = new Integer[data.length][data[0].length];
        for(int i = 0; i < data.length; i++){
            Long[] temp = data[i];
            for(int j = 0; j < temp.length; j++){
                outputData[i][j]=Math.toIntExact(temp[j])%2;
            }
        }
        return outputData;
    }

    /**Добавление в массив результата этого массива для обучения
     * @param data Входной массив
     * @param res Результат массива
     * */
    public static Integer[] addRes(Integer[] data, int res){
        Integer[] outputData = new Integer[data.length + 1];
        for(int i = 0; i < data.length + 1; i++){
            if(i < data.length) {
                outputData[i] = data[i];
            }else{
                outputData[i] = res;
            }
        }
        return outputData;
    }

    /**Преобразование списка из списков Long в матрицу Long
     * @param data Входной список списков*/
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

    /**Преобразование списка из списков Integer в матрицу Long
     * @param data Входной список списков*/
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

    /**Преобразование матрицы Long в список списков Long
     * @param data Входная матрица*/
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

    /**Преобразование матрицы Integer в список списков Integer
     * @param data Входная матрица*/
    public static List<List<Integer>> arrInListOfList(Integer[][] data){
        List<List<Integer>> outputData = new ArrayList<>();
        for(int i = 0; i < data.length; i++){
            List<Integer> line = new ArrayList<>();
            for(int j = 0; j < data[0].length; j++){
                line.add(data[i][j]);
            }
            outputData.add(line);
        }
        return outputData;
    }

    /**Преобразование матрицы Integer в массив Integer
     * @param data входная матрица*/
    public static Integer[] matrixToArray(Integer[][] data){
        Integer[] outputArray = new Integer[data.length * data[0].length];
        int count = 0;
        for (Integer[] datum : data) {
            for (Integer value : datum) {
                outputArray[count] = value;
                count++;
            }
        }
        return outputArray;
    }

    /**Соединяет 2 матрицы в 1 матрицу
     * @param arr1 Матрица 1
     * @param arr2 Матрица 2*/
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

    /**Соединяет 2 массива в 1 матрицу
     * @param arr1 Матрица 1
     * @param arr2 Матрица 2*/
    public static Integer[][] concatenateArr(Integer[] arr1, Integer[] arr2){
        Integer[][] outputData = new Integer[2][arr1.length];
        for(int i = 0; i < arr1.length; i++){
            outputData[0][i] = arr1[i];
            outputData[1][i] = arr2[i];
        }
        return outputData;
    }
}
