package file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PictureFile {

    /**Преобразование картинки в Список списков Integer
     * @param path Путь к изображению*/
    public static List<List<Integer>> readIntegerData(String path, int size){
        File img = new File(path);
        List<List<Integer>> bites = new ArrayList<>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
            Image image = ImageIO.read(img).getScaledInstance(size, size, Image.SCALE_DEFAULT);
            BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            bi.getGraphics().drawImage(image, 0, 0, null);
            for(int i = 0; i < bi.getHeight(); i++){
                List<Integer> datas = new ArrayList<>();
                for(int j = 0; j < bi.getWidth(); j++){
                    datas.add(bi.getRGB(j, i));
                }
                bites.add(datas);
            }
            ImageIO.write(bi, "tiff", baos);
            baos.close();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return bites;
    }

    /**Создание изображения из матрицы байтов
     * @param data Данные
     * @param path Путь к файлу
     */
    public static void createJPG(Long[][] data, String path){
        try {
            BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<data.length; i++) {
                for(int j=0; j<data[0].length; j++) {
                    image.setRGB(j,i,data[i][j].intValue());
                }
            }
            File output = new File(path);
            ImageIO.write(image, "jpg", output);
        }
        catch(Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
