package codingbo.plugin.forfaster.step1

import com.intellij.util.ui.UIUtil
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageUtils {

    companion object {
        /**
         * 图标爆炸
         * 将指定图标文件 保存为 分辨率分别为 192, 144, 96, 72, 48的文件
         */
        fun bigBang(inPath: String, outPath: String, outDirPrefix: String, outFileName: String) {


            val file = File(inPath)
            file.canRead()
            val rawImage = ImageIO.read(file)
            val map = mapOf(192 to "xxxhdpi", 144 to "xxhdpi", 96 to "xhdpi", 72 to "hdpi", 48 to "mdpi")
            map.forEach {
                scaleAndSave(rawImage, it.key, "$outPath/$outDirPrefix-${it.value}/$outFileName")
            }
        }

        /**
         * 缩放和保存
         */
        private fun scaleAndSave(rawImage: Image, size: Int, path: String) {
            File(path).parentFile.mkdirs()
            println("$size : $path")
            val image = rawImage.getScaledInstance(size, size, Image.SCALE_DEFAULT)
            val tag = UIUtil.createImage(size, size, BufferedImage.TYPE_INT_RGB)
            tag.graphics.drawImage(image, 0, 0, null)
            tag.graphics.dispose()
            ImageIO.write(tag, "PNG", File(path))
        }
    }

}