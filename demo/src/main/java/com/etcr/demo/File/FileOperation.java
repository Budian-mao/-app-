package com.etcr.demo.File;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileOperation {

    public boolean writefile(String filepath, String words) {
        byte[] b = words.getBytes();
        try {
            OutputStream out = new FileOutputStream(filepath);
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String readfile(String filepath) {
        File file = new File(filepath);
        try (FileReader reader = new FileReader(file);
             BufferedReader b = new BufferedReader(reader)) {

            StringBuilder sb = new StringBuilder();
            String s = "";
            while ((s = b.readLine()) != null) {
                sb.append(s + "\n");
            }
            b.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
