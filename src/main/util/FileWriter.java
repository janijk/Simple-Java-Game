package main.util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWriter {
    private static String filename = "highscore.txt";

    public static void writeInt( int data, long position) throws IOException {
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        writer.seek(position);
        writer.writeInt(data);
        writer.close();
    }

    public static void writeInt( int data) throws IOException {
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        writer.seek(1);
        writer.writeInt(data);
        writer.close();
    }

    public static void writeString( String data, long position) throws IOException {
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        writer.seek(position);
        writer.writeChars(data);
        writer.close();
    }

    public static int readInt(long position) throws IOException {
        int result = 0;
        RandomAccessFile reader = new RandomAccessFile(filename, "r");
        reader.seek(position);
        result = reader.readInt();
        reader.close();
        return result;
    }
    public static int readInt() throws IOException {
        int result = 0;
        RandomAccessFile reader = new RandomAccessFile(filename, "r");
        reader.seek(1);
        result = reader.readInt();
        reader.close();
        return result;
    }

    public static String readString( long position) throws IOException {
        String result = "";
        RandomAccessFile reader = new RandomAccessFile(filename, "r");
        reader.seek(position);
        result = reader.readLine();
        reader.close();
        return result;
    }
}
