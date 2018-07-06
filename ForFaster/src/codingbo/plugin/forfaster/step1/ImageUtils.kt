package codingbo.plugin.forfaster.step1

import com.intellij.util.ui.UIUtil
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Image
import java.awt.RenderingHints
import java.awt.geom.RoundRectangle2D
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
            var tag = UIUtil.createImage(size, size, BufferedImage.TYPE_INT_ARGB)
            tag.graphics.drawImage(image, 0, 0, null)
            tag = makeRoundedCorner(tag, (size / 3).toFloat())
            tag.graphics.dispose()
            ImageIO.write(tag, "PNG", File(path))
        }


        /**
         * 生成圆角图标
         */
        private fun makeRoundedCorner(image: BufferedImage, cornerRadius: Float): BufferedImage {
            val w = image.width
            val h = image.height
            val output = UIUtil.createImage(w, h, BufferedImage.TYPE_INT_ARGB)
            val g2 = output.createGraphics()
            g2.composite = AlphaComposite.Src
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.color = Color.WHITE
            g2.fill(RoundRectangle2D.Float(0F, 0F, w.toFloat(), h.toFloat(),
                    cornerRadius, cornerRadius))
            g2.composite = AlphaComposite.SrcAtop
            // 使用 setRenderingHint 设置抗锯齿
            g2.drawImage(image, 0, 0, null)
            g2.dispose()
            return output
        }
    }

}