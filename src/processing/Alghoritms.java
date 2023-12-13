package processing;

import file.TextFile;
import preparation.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Alghoritms {
    //Значение скрытого нейрона
    final static int x0 = 1;
    final static int xLast = 1;
    final static int alpha = 1;
    //Скорость обучения
    final static double nu = 0.95;

    //Веса
    public static Double[][] dataW;
    //Сигмоида
    static double func(double value){
        return 1/(1 + Math.exp((-1) * alpha * value));
    }

    /**Метод обратного распределения ошибки
     * @param data Входные данные
     * @param nuCustom Скорость обучения
     * @param eps Выходные данные (эпсилон)
     * @param numEpoch Количество эпох обучения
     * */
    public static void reversErrorDistribution(List<List<Long>> data, Double nuCustom, List<Double> eps, int numEpoch){
        if(nuCustom == null){
            nuCustom = nu;
        }
        //Получим количество переменных
        int xN = data.get(0).size();
        dataW = Input.randMatrix(xN, 0, 100);
        Double finalNuCustom = nuCustom;
        for(int i = 0; i < numEpoch; i++) {
            //Значение эпохи (выходное)
            AtomicReference<Double> valEp = new AtomicReference<>(0.0);
            data.forEach(row -> {
                AtomicReference<Integer> numRow = new AtomicReference<>(0);
                AtomicReference<Integer> numCol = new AtomicReference<>(0);
                //Создадим массив из сумм
                double[] s = new double[xN];
                //посчитаем S0...Sn
                //Для начала посчитаем первое слагаемое для каждой суммы
                for(int k = 0; k < dataW.length; k++){
                    s[k] += x0 * dataW[k][0];
                }
                //Остальные слагаемые суммы
                for(int k = 0; k < row.size()-1; k++){
                    for (int j = 0; j < dataW.length; j++) {
                        s[j] += dataW[j][k+1] * row.get(k);
                    }
                }
                //Посчитаем последнюю сумму
                numRow.set(dataW.length - 1);
                s[numRow.get()] = dataW[numRow.get()][0] * xLast;
                //Досчитаем последнюю сумму
                for(int k = 0; k < row.size()-1; k++){
                    s[numRow.get()] += dataW[numRow.get()][k+1] * func(s[k]);
                }
                //Дельта выхода
                double delEx = row.get(row.size() - 1) - func(s[s.length - 1]);
                //Дельта последняя
                double delLast = func(s[s.length - 1]) * (1 - func(s[s.length - 1]))  * delEx;
                //Посчитаем дельты для каждого столбца, кроме последнего
                List<Double> del = new ArrayList<>();
                for(int k = 0; k < s.length-1; k++){
                    del.add(func(s[k]) * (1 - func(s[k])) * delLast * dataW[xN - 1][k+1]);
                }
                del.add(delLast);
                numCol.set(0);
                del.forEach(value -> {
                    if (numCol.get() != del.size() - 1) {
                        //Длина не для последнего нейрона
                        AtomicReference<Integer> numEl = new AtomicReference<>(0);
                        for (Double val : dataW[numCol.get()]) {
                            if (numEl.get() != 0) {
                                dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * row.get(numEl.get());
                            } else {
                                dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * x0;
                            }
                            numEl.set(numEl.get() + 1);
                        }
                    } else {
                        //Длина для последнего нейрона
                        AtomicReference<Integer> numEl = new AtomicReference<>(0);
                        for (Double val : dataW[numCol.get()]) {
                            if (numEl.get() != 0) {
                                dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * func(s[numEl.get()]);
                            } else {
                                dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * xLast;
                            }
                            numEl.set(numEl.get() + 1);
                        }
                    }
                    numCol.set(numCol.get() + 1);
                });
                valEp.set(valEp.get() + Math.pow(func(s[s.length-1]) - row.get(row.size()-1),2));
            });
            eps.add(Math.sqrt(valEp.get()/data.size()));
            System.out.println("End epoch №" + i);
        }
        TextFile.writeData(dataW, "a.dat");
    }

    /**Метод для проверки, как обучилась нейросеть
     * @param data Входные данные
     * @param nuCustom скорость обучения. Использовать из алгоритма обучения!
     * */
    public static void reversErrorDistributionResult(List<List<Long>> data, Double nuCustom) {
        if (nuCustom == null) {
            nuCustom = nu;
        }
        dataW = Input.readWeight("a.dat");
        //Получим количество переменных
        int xN = dataW.length;
        Double finalNuCustom = nuCustom;
        AtomicReference<Double> valEp = new AtomicReference<>(0.0);
        data.forEach(row -> {
            AtomicReference<Integer> numRow = new AtomicReference<>(0);
            AtomicReference<Integer> numCol = new AtomicReference<>(0);
            //Создадим массив из сумм
            double[] s = new double[xN];
            //посчитаем S0...Sn
            //Для начала посчитаем первое слагаемое для каждой суммы
            for (int k = 0; k < dataW.length; k++) {
                s[k] += x0 * dataW[k][0];
            }
            for (int k = 0; k < row.size() - 1; k++) {
                for (int j = 0; j < dataW.length; j++) {
                    s[j] += dataW[j][k + 1] * row.get(k);
                }
            }
            //Посчитаем последнюю сумму
            numRow.set(dataW.length - 1);
            s[numRow.get()] = dataW[numRow.get()][0] * xLast;
            //Досчитаем последнюю сумму
            for (int k = 0; k < row.size() - 1; k++) {
                s[numRow.get()] += dataW[numRow.get()][k + 1] * func(s[k]);
            }
            //Дельта выхода
            double delEx = row.get(row.size() - 1) - func(s[s.length - 1]);
            //Дельта последняя
            double delLast = func(s[s.length - 1]) * (1 - func(s[s.length - 1])) * delEx;
            //Посчитаем дельты для каждого столбца, кроме последнего
            List<Double> del = new ArrayList<>();
            for (int k = 0; k < s.length - 1; k++) {
                del.add(func(s[k]) * (1 - func(s[k])) * delLast * dataW[xN - 1][k + 1]);
            }
            del.add(delLast);
            numCol.set(0);
            del.forEach(value -> {
                if (numCol.get() != del.size() - 1) {
                    //Длина не для последнего нейрона
                    AtomicReference<Integer> numEl = new AtomicReference<>(0);
                    for (Double val : dataW[numCol.get()]) {
                        if (numEl.get() != 0 && numEl.get() != row.size()) {
                            dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * row.get(numEl.get());
                        } else {
                            dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * x0;
                        }
                        numEl.set(numEl.get() + 1);
                    }
                } else {
                    //Длина для последнего нейрона
                    AtomicReference<Integer> numEl = new AtomicReference<>(0);
                    for (Double val : dataW[numCol.get()]) {
                        if (numEl.get() != 0) {
                            dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * func(s[numEl.get()]);
                        } else {
                            dataW[numCol.get()][numEl.get()] = val + finalNuCustom * value * xLast;
                        }
                        numEl.set(numEl.get() + 1);
                    }
                }
                numCol.set(numCol.get() + 1);
            });
            System.out.println(func(s[s.length - 1]));
        });
    }

    /**Алгоритм свертывания нейросети
     * @param matrix Матрица битов черно-белого изображения
     * @param mask Маска преобразования
     * @param n Количество строк исходного изображения
     * @param m Количество столбцов исходного изображения
     * @return Свернутая матрица*/
    public static Long[][] convolutionalNet(Long[][] matrix, Integer[][] mask, int n, int m){
        List<List<Long>> dataMatrix = Input.arrInListOfList(matrix);
        List<List<Long>> outputMatrix=null;
        do {
            Long[][] tempMatrix;
            if(outputMatrix==null) {
                //Первая итерация - не было свертывания
                tempMatrix = matrix;
                outputMatrix = dataMatrix;
            }else{
                //после первой итерации - используем данные от свертывания
                tempMatrix=Input.listOfListLongInArr(outputMatrix);
                dataMatrix=outputMatrix;
            }
            matrix = new Long[outputMatrix.size() + 2][outputMatrix.get(0).size() + 2];
            //огораживаем нулями картинку для сохранения размерности от свертывания
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (i == 0 || j == 0 || i == matrix.length - 1 || j == matrix[0].length - 1) {
                        matrix[i][j] = 0l;
                    } else {
                        matrix[i][j] = tempMatrix[i - 1][j - 1];
                    }
                }
            }
            outputMatrix = new ArrayList<>();
            //Прогоняем маску перемножая соответствующие элементы и суммируя произведение
            for (int i = 0; i < dataMatrix.size() - mask.length; i++) {
                List<Long> line = new ArrayList<>();
                for (int j = 0; j < dataMatrix.get(0).size() - mask[0].length; j++) {
                    long sum = 0L;
                    for (int iIn = i; iIn < i + mask.length; iIn++) {
                        for (int jIn = j; jIn < j + mask[0].length; jIn++) {
                            sum += matrix[iIn][jIn] * mask[iIn - i][jIn - j];
                        }
                    }
                    line.add(sum);
                }
                outputMatrix.add(line);
            }
            //Используем пуллинг для матрицы
            outputMatrix=maxPulling(outputMatrix, 2);
        }while(outputMatrix.size() > n || outputMatrix.get(0).size() > m );
        return Input.listOfListLongInArr(outputMatrix);
    }

    /**Алгоритм пуллинга на основе максимального элемента. Из вложенных не пересекающихся матриц n*m берется максимальный элемент
     * @param matrix - матрица до преобразования
     * @param n - во сколько раз уменьшить матрицу
     * @return Новая матрица*/
    private static List<List<Long>> maxPulling(List<List<Long>> matrix, int n){
        List<List<Long>> outputMatrix = new ArrayList<>();
        Long[][] tempMatrix = new Long[n][n];
        for(int i = 0; i < matrix.size(); i+=n){
            List<Long> line = new ArrayList<>();
            for(int j = 0; j < matrix.get(0).size(); j+=n){
                //Заносим данные в временную матрицу
                for(int iIn = i; iIn < i+tempMatrix.length; iIn++){
                    for(int jIn = j; jIn < j + tempMatrix[0].length; jIn++){
                        tempMatrix[iIn-i][jIn-j] = matrix.get(i).get(j);
                    }
                }
                //Ищем максимальный элемент этой матрицы и добавляем его
                line.add(maxInArr(tempMatrix));
            }
            outputMatrix.add(line);
        }
        return outputMatrix;
    }

    /**Метод для поиска наибольшего элемента во входящей матрице
     * @param matrix Матрица
     * @return Максимальный элемент*/
    private static Long maxInArr(Long[][] matrix){
        long max = matrix[0][0];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] > max){
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }
}
