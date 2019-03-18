package com.demo.matrixToImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 18:07
 * @version: V1.0
 * @detail: 二维码生成工具
 **/
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter()

    {
    }

    public static BufferedImage toBufferedImage(
            BitMatrix matrix)

    {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0;
             x < width;
             x++) {
            for (int y = 0;
                 y < height;
                 y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK :
                        WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(
            BitMatrix matrix, String
            format,
            File file)
            throws IOException

    {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(
            BitMatrix matrix, String
            format,
            OutputStream stream)
            throws IOException

    {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 获取二维码图片，并保存在服务器上
     * @param content     二维码内容
     * @param filePath    二维码保存路径
     * @param format      图片格式
     * @throws Exception ex
     */
    public static void getQrCodeImage(String content, String filePath,String format) throws Exception {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        //生成二维码
        File outputFile = new File(filePath + "." + format);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }

    public static void main(String args[]) throws IOException, WriterException {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode("http://www.baidu.com",
                BarcodeFormat.QR_CODE, width, height, hints);
        //生成二维码
        File outputFile = new File("D:" +File.separator + ".gif");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }
}
