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

    public static List<List<Integer>> readIntegerData(String path){
        File img = new File(path);
        List<List<Integer>> bites = new ArrayList<>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
            BufferedImage image = ImageIO.read(img);
            int[] els = new int[1];
            for(int i = 0; i < 100; i++){
                List<Integer> datas = new ArrayList<>();
                for(int j = 0; j < 100; j++){
                    datas.add(image.getData().getPixel(i, j, els)[0]);
                }
                bites.add(datas);
            }
            ImageIO.write(image, "tiff", baos);
            baos.close();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return bites;
    }

    public static void createJPG(Long[][] data, String path){
        try {
            BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<data.length; i++) {
                for(int j=0; j<data[0].length; j++) {
                    long a = data[i][j];
                    int c = (int)(a%2);
                    Color newColor = c==0?new Color(0,0,0):new Color(255, 255, 255);
                    image.setRGB(j,i,newColor.getRGB());
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
