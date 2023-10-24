package com.example.teacherslink;

import java.io.*;

public class StateManager {

    // Private constructor to prevent instantiation
    private StateManager() {}

    public static void saveState(Object object, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Object loadState(String filename) {
        Object object = null;
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            object = in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return object;
    }
}
