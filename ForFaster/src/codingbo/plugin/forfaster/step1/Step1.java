package codingbo.plugin.forfaster.step1;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by bob on 18.5.29.
 */
public class Step1 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Step1InputDialog inputDialog = new Step1InputDialog();
        inputDialog.setOkListener(ImageUtils.Companion::bigBang);
        inputDialog.setTitle("ForFaster_Step1");
        inputDialog.pack();
        inputDialog.setLocationRelativeTo(null);
        inputDialog.setVisible(true);
    }

    public static void main(String[] args) {
        ImageUtils.Companion.bigBang("C:\\Users\\bob\\Desktop\\result\\109779420.png",
                "C:\\Users\\bob\\Desktop\\result", "mipmap", "ic_launch.png");

    }
}
