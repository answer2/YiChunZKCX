package dev.answer.yichunzkcx.util;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.Enumeration;

public class FileTool {

    public static final String TAG = "FileUntil";

    // 输出文件
    public static void InputData(String path, String data) {
        try {
            FileOutputStream foss = new java.io.FileOutputStream(path);
            foss.write((data).getBytes());
            foss.close();
        } catch (java.io.FileNotFoundException e) {
            Log.d(TAG + "_InputData", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d(TAG + "_InputData", e.getMessage());
        }
    };

    public static void InputData(String path, byte[] data) {
        try {
            FileOutputStream foss = new java.io.FileOutputStream(path);
            foss.write((data));
            foss.close();
        } catch (java.io.FileNotFoundException e) {
            Log.d(TAG + "_InputData", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d(TAG + "_InputData", e.getMessage());
        }
    };

    // 读取文本文件中的内容
    public static String ReadDataFile(File file) {
        return ReadData(file.getAbsolutePath());
    }

    public static String ReadData(String strFilePath) {
        String path = strFilePath;
        String content = ""; // 文件内容字符串
        // 打开文件
        File file = new File(path);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d(TAG, "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    // 分行读取
                    while ((line = buffreader.readLine()) != null) {
                       content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d(TAG + "_ReadData", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d(TAG + "_ReadData", e.getMessage());
            }
        }
        return content;
    }

    
    /**
     * 解压Assets中的文件
     *
     * @param assetName       压缩包文件名
     * @param outputDirectory 输出目录
     * @throws IOException
     */
    public static void unZipAssets(Context context,String assetName, String outputDirectory) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        //打开压缩文件
        try {
            inputStream = context.getAssets().open(assetName);
        } catch (IOException e) {
            return;
        }

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[4096];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                //String name = zipEntry.getName();
                //name = name.substring(0, name.length() - 1);
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                                + zipEntry.getName());
                //创建该文件
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }
    
    
    public static void unApk(Context context,String dir, String extDir) throws IOException {
        int i = dir.length() + 1;
        ZipFile zip = new ZipFile(context.getApplicationInfo().publicSourceDir);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.indexOf(dir) != 0)
                continue;
            String path = name.substring(i);
            if (entry.isDirectory()) {
                File f = new File(extDir + File.separator + path);
                if (!f.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    f.mkdirs();
                }
            } else {
                String fname = extDir + File.separator + path;
                File ff = new File(fname);
                File temp = new File(fname).getParentFile();
                if (!temp.exists()) {
                    if (!temp.mkdirs()) {
                        throw new RuntimeException("create file " + temp.getName() + " fail");
                    }
                }
                try {
                    if (ff.exists() && entry.getSize() == ff.length() && getFileMD5(zip.getInputStream(entry)).equals(getFileMD5(ff)))
                        continue;
                } catch (NullPointerException ignored) {
                }
                FileOutputStream out = new FileOutputStream(extDir + File.separator + path);
                InputStream in = zip.getInputStream(entry);
                byte[] buf = new byte[4096];
                int count = 0;
                while ((count = in.read(buf)) != -1) {
                    out.write(buf, 0, count);
                }
                out.close();
                in.close();
            }
        }
        zip.close();
    }

    
    
    // 复制asset文件到sd卡
    public static void assetsToSD(Context context, String InFileName, String OutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(OutFileName);
        myInput = context.getAssets().open(InFileName);
        byte[] buffer = new byte[4096];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

    // 复制文件
    public static void copyFile(String from, String to) {
        try {
            copyFile(new FileInputStream(from), new FileOutputStream(to));
        } catch (IOException e) {
            Log.i("lua", e.getMessage());
        }
    }

    public static boolean copyFile(InputStream in, OutputStream out) {
        try {
            int byteread = 0;
            byte[] buffer = new byte[4096];
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            Log.i(TAG + "_copyDir", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean copyDir(String from, String to) {
        return copyDir(new File(from), new File(to));
    }

    public static boolean copyDir(File from, File to) {
        boolean ret = true;
        File p = to.getParentFile();
        if (!p.exists()) p.mkdirs();
        if (from.isDirectory()) {
            File[] fs = from.listFiles();
            if (fs != null && fs.length != 0) {
                for (File f : fs) ret = copyDir(f, new File(to, f.getName()));
            } else {
                if (!to.exists()) ret = to.mkdirs();
            }
        } else {
            try {
                if (!to.exists()) to.createNewFile();
                ret = copyFile(new FileInputStream(from), new FileOutputStream(to));
            } catch (IOException e) {
                Log.i(TAG + "_copyDir", e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    // 删除文件

    public static boolean rmDir(File dir) {
        if (dir.isDirectory()) {
            File[] fs = dir.listFiles();
            for (File f : fs) rmDir(f);
        }
        return dir.delete();
    }

    public static void rmDir(File dir, String ext) {
        if (dir.isDirectory()) {
            File[] fs = dir.listFiles();
            for (File f : fs) rmDir(f, ext);
            dir.delete();
        }
        if (dir.getName().endsWith(ext)) dir.delete();
    }

    // 计算文件的 MD5 值

    public static String getFileMD5(String file) {
        return getFileMD5(new File(file));
    }

    public static String getFileMD5(File file) {
        try {
            return getFileMD5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String getFileMD5(InputStream in) {
        byte buffer[] = new byte[4096];
        int len;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
