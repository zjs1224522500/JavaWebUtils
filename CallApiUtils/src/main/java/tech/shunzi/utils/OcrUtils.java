package tech.shunzi.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class OcrUtils {

    public static void scanEnChar(String path) throws Exception {
        File imageFile = new File(path);
        BufferedImage image = ImageIO.read(imageFile);
        image = convertImage(image);
        ITesseract instance = new Tesseract();
        String result = instance.doOCR(image);
        System.out.println(result);
    }

    public static void scanZhChar(String path) throws Exception {
        File imageFile = new File(path);
        BufferedImage image = ImageIO.read(imageFile);
        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        String result = instance.doOCR(image);
        System.out.println(result);
    }

    public static BufferedImage convertImage(BufferedImage image) throws Exception {
        image = ImageHelper.convertImageToGrayscale(image);
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
}
