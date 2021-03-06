package codingbo.plugin.forfaster.step1

import com.intellij.diff.comparison.isAlpha
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

    interface Callback {
        fun finish()
        fun failed(msg: String?)
    }

    companion object {
        var oldFiles: ArrayList<File> = ArrayList()
        var newFiles: ArrayList<File> = ArrayList()
        private const val oldSuffix: String = "_old"
        /**
         * 图标爆炸
         * 将指定图标文件 保存为 分辨率分别为 192, 144, 96, 72, 48的文件
         */
        fun bigBang(inPath: String, outPath: String, outDirPrefix: String, outFileName: String, cutRound: Boolean, cb: Callback) {
            try {
                oldFiles.clear()
                val file = File(inPath)
                file.canRead()
                val rawImage = ImageIO.read(file)
                val map = mapOf(192 to "xxxhdpi", 144 to "xxhdpi", 96 to "xhdpi", 72 to "hdpi", 48 to "mdpi")
                map.forEach {
                    //                    val fileAbsPath = "$outPath/$outDirPrefix-${it.value}/$outFileName"
                    val fileAbsPath = "$outPath${File.separator}$outDirPrefix-${it.value}${File.separator}$outFileName"
                    val originFile = saveOriginFile(fileAbsPath)
                    if (originFile != null) {
                        oldFiles.add(originFile)
                    }
                    val saveFile = scaleAndSave(rawImage, it.key, fileAbsPath, cutRound)
                    if (saveFile != null) {
                        newFiles.add(saveFile)
                    }
                }
                System.out.println(oldFiles.size)
                cb.finish()
            } catch (e: Exception) {
                e.printStackTrace()
                cb.failed(e.message)
            }
        }

        /**
         * 删除老文件
         */
        fun deleteOldFiles() {
            oldFiles.map {
                it.delete()
            }
        }

        /**
         * 撤销操作
         */
        fun revoke() {
            newFiles.map { it.delete() }
            oldFiles.map {
                fileRename(it, it.name.replace(oldSuffix, ""))
            }
        }

        /**
         * 保存原文件
         */
        fun saveOriginFile(fileAbsPath: String): File? {
            val file = File(fileAbsPath)
            if (file.exists()) {
                return fileRename(file, "${file.name}$oldSuffix")
            }
            return null
        }

        /**
         * 文件改名
         */
        fun fileRename(file: File, toFileName: String): File {
            if (file.exists()) {
                val originFile = File("${file.parentFile.absolutePath}/$toFileName")
                if (originFile.exists()) {
                    originFile.delete()
                }
                if (file.renameTo(originFile)) {
                    return originFile
                }
                System.out.println("failed to rename")
            }
            return file
        }

        /**
         * 缩放和保存
         */
        fun scaleAndSave(rawImage: Image, size: Int, path: String, cutRound: Boolean): File? {
            val file = File(path)
            file.parentFile.mkdirs()
            println("$size : $path")
            val image = rawImage.getScaledInstance(size, size, Image.SCALE_SMOOTH)
            var tag = UIUtil.createImage(size, size, BufferedImage.TYPE_INT_ARGB)
            tag.graphics.drawImage(image, 0, 0, null)

            if (cutRound) {
                tag = makeRoundedCorner(tag, (size / 3).toFloat())
            }
            tag.graphics.dispose()
            ImageIO.write(tag, "PNG", file)
            return file
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