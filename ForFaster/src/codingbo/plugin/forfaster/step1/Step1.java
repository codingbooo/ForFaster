package codingbo.plugin.forfaster.step1;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.Nullable;

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
        ImageUtils.Companion.bigBang("C:\\result\\test.png",
                "C:\\result", "mipmap", "ic_launch.png", new ImageUtils.Callback() {
                    @Override
                    public void finish() {

                    }

                    @Override
                    public void failed(@Nullable String msg) {

                    }
                });
    }
}
