package file;

import javax.imageio.ImageIO;
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
            for(int i = 0; i < 1602; i++){
                List<Integer> datas = new ArrayList<>();
                for(int j = 0; j < 2160; j++){
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
}
