package com.yang.springframework.beans.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于java.io.File和java.nio.file.Path句柄的Resource实现，目标为文件系统。
 * 支持解析为File和URL。实现了扩展的WritableResource接口。
 * <p>注意：自Spring Framework 5.0起，此Resource实现使用NIO.2 API进行读写交互。
 * 从5.1开始，可以使用Path句柄构造，此时所有文件系统交互将通过NIO.2执行，仅在调用getFile()时回退到File。
 */
public class FileSystemResource implements Resource {
    private final File file;

    private final String path;

    /**
     * 根据File句柄创建一个新的FileSystemResource。
     * <p>注意：通过createRelative创建相对资源时，相对路径将应用于同一目录级别：
     * 例如new File("C:/dir1")，相对路径"dir2" → "C:/dir2"！
     * 如果希望相对路径在指定根目录下构建，请使用带有文件路径的构造函数，
     * 在根路径后添加末尾斜杠："C:/dir1/"，这表示将该目录作为所有相对路径的根目录。
     * @param file 一个File句柄
     */
    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    /**
     * 根据文件路径创建一个新的 FileSystemResource。
     * <p>注意：通过 createRelative 创建相对资源时，此处指定的资源基本路径是否以斜杠结尾会产生影响。
     * 以 "C:/dir1/" 为例，相对路径将在该根目录下构建：
     * 例如相对路径 "dir2" → "C:/dir1/dir2"。而以 "C:/dir1" 为例，
     * 相对路径将应用于同一目录级别：相对路径 "dir2" → "C:/dir2"。
     * @param path 文件路径
     */
    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
    }

    /**
     * 此实现为基础文件打开一个 NIO 文件流。
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    public String getPath() {
        return path;
    }
}
