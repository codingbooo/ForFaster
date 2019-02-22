package codingbo.plugin.forfaster.step1;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bob on 18.5.29.
 */
public class Step1 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Step1InputDialog inputDialog = new Step1InputDialog();
        inputDialog.setTitle("ForFaster_Step1");
        inputDialog.pack();
        inputDialog.setLocationRelativeTo(null);
        inputDialog.setVisible(true);
    }

    public static void main(String[] args) {
        ImageUtils.Companion.bigBang("C:\\Users\\bob\\Desktop\\test\\icon.png",
                "C:\\Users\\bob\\Desktop\\test", "mipmap", "ic_launch.png", true, new ImageUtils.Callback() {
                    @Override
                    public void finish() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        ImageUtils.Companion.deleteOldFiles();
                        ImageUtils.Companion.revoke();
                    }

                    @Override
                    public void failed(@Nullable String msg) {

                    }
                });

//        File file = new File("C:\\Users\\bob\\Desktop\\test\\demo");
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write("Hello World".getBytes());
//
//            // 拼dos命令
//            // attrib的祥细功能介绍请在DOS内输入 " attrib /? " 查看
//            String sets = "attrib +H \"" + file.getAbsolutePath() + "\"";
//            // 输出命令串
//            System.out.println(sets);
//            // 运行命令串
//            Runtime.getRuntime().exec(sets);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
