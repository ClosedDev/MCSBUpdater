package io.github.closeddev;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static final String appdata = System.getenv("APPDATA");
    public static final String MCSBPath = appdata + "/MCServerBuilder";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("MCSB Updater v0.1");
        System.out.println("Checking Files...");
        System.out.println("Target Path:" + args[0]);
        final String targetPath = args[0];
        System.out.println("Target Version:" + args[1]);

        System.out.println("Starting Update...");
        makeDir(MCSBPath + "/Temp");
        makeDir(MCSBPath + "/Temp/MCSB");
        Downloader.fileDown("https://github.com/ClosedDev/MCServerBuilder/releases/latest/download/MCSB.jar", MCSBPath + "/Temp/MCSB.jar");

        File file = new File(MCSBPath + "/Temp/MCSB.jar");
        File newFile = new File(targetPath + "/MCSB.jar");

        if(newFile.exists()){
            newFile.delete();
        }

        // 2. FileInputStream, FileOutputStream 준비
        FileInputStream input = new FileInputStream(file);
        FileOutputStream output = new FileOutputStream(newFile);

        // 3. 한번에 read하고, write할 사이즈 지정
        byte[] buf = new byte[1024];

        // 4. buf 사이즈만큼 input에서 데이터를 읽어서, output에 쓴다.
        int readData;
        while ((readData = input.read(buf)) > 0) {
            output.write(buf, 0, readData);
        }

        // 5. Stream close
        input.close();
        output.close();

        file.delete();
        System.out.println("Update success.");
        System.exit(0);
    }
    public static void makeDir(String path) throws InterruptedException {
        File d = new File(path);

        if(!d.isDirectory()){
            if(!d.mkdirs()){
                System.out.println("An error occurred while creating the folder.");
            }
        }
    }
}