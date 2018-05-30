package codingbo.plugin.forfaster.step1

/**
 * Created by bob on 18.5.29.
 */
task myTask {
    def fromPath = 'D://test/'
    def toPath = 'D://test2/'
    def toFileName = 'ic_launch3.png'
    def fromFileList = files(
            "${fromPath}192.png",
            "${fromPath}144.png",
            "${fromPath}96.png",
            "${fromPath}72.png",
            "${fromPath}48.png")

    def copyFile = {
        fromDir, formFileName, toDir ->
            copy {
                from fromDir
                into toDir
                rename {
                    String fn -> fn.replace(formFileName, toFileName)
                }
            }
    }
    println "copy start."
    fromFileList.each {
        def f = it
        switch (f.path) {
            case ~/.*192.*/:
                println f.path
                copyFile(f.path, f.name, "${toPath}/xxxdpi/")
                break
            case ~/.*144.*/:
                println f.path
                copyFile(f.path, f.name, "${toPath}/xxdpi/")
                break
            case ~/.*96.*/:
                println f.path
                copyFile(f.path, f.name, "${toPath}/xdpi/")
                break
            case ~/.*72.*/:
                println f.path
                copyFile(f.path, f.name, "${toPath}/hdpi/")
                break
            case ~/.*48.*/:
                println f.path
                copyFile(f.path, f.name, "${toPath}/mdpi/")
                break
        }
    }
    println "copy finish."
}