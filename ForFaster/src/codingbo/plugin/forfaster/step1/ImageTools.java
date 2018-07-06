package codingbo.plugin.forfaster.step1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTools {

    public static void makeCircularImg1(String srcFilePath, String circularImgSavePath, int targetSize, int cornerRadius) throws IOException {
        BufferedImage bi1 = ImageIO.read(new File(srcFilePath));

        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                .getHeight());

        Graphics2D g2 = image.createGraphics();
        image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
        g2 = image.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        g2.dispose();

        try {
            ImageIO.write(image, "PNG", new File(circularImgSavePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String makeCircularImg(String srcFilePath, String circularImgSavePath, int targetSize, int cornerRadius) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(srcFilePath));
        Image instance = bufferedImage.getScaledInstance(targetSize, targetSize, Image.SCALE_DEFAULT);
        bufferedImage.getGraphics().drawImage(instance, 0,0,null);

        BufferedImage circularBufferImage = roundImage(bufferedImage, targetSize, cornerRadius);


        circularBufferImage.getGraphics().dispose();

        ImageIO.write(circularBufferImage, "png", new File(circularImgSavePath));
        return circularImgSavePath;
    }

    private static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }

}
