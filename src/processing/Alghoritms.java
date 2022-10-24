package processing;

import preparation.Input;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Alghoritms {
    final static int x0 = 1;
    final static int xLast = 1;
    final static int alpha = 1;
    //100*100 квадрат/треугольник. Используя маски свернуть до n*n(10*10/5*5) (пуллинг, маска)
    //Скорость обучения
    final static double nu = 0.95;

    static Double[][] dataW;
    static double func(double value){
        return 1/(1 + Math.exp((-1) * alpha * value));
    }

    /**Метод обратного распределения ошибки
     * @param data Входные данные
     * @param nuCustom Скорость обучения
     * @param eps Выходные данные (эпсилон)
     * @param numEpoch Количество эпох обучения
     * */
    public static void reversErrorDistribution(List<List<Integer>> data, Double nuCustom, List<Double> eps, int numEpoch){
        if(nuCustom == null){
            nuCustom = nu;
        }
        //Получим количество переменных
        int xN = data.get(0).size();
        dataW = Input.randMatrix(xN, -1, 1);
        Double finalNuCustom = nuCustom;
        for(int i = 0; i < numEpoch; i++) {
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
            eps.add(Math.sqrt(valEp.get()/8));
        }
    }

    /**Метод для проверки, как обучилась нейросеть
     * @param data Входные данные
     * @param nuCustom скорость обучения. Использовать из алгоритма обучения!
     * */
    public static void reversErrorDistributionResult(List<List<Integer>> data, Double nuCustom) {
        if (nuCustom == null) {
            nuCustom = nu;
        }
        //Получим количество переменных
        int xN = data.get(0).size();
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
            System.out.println(func(s[s.length - 1]));
        });
    }
}
