package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static String Encrypt(String text, int key){
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            encrypted.append((char) (text.charAt(i) + key));
        }
        return encrypted.toString();
    }
    public static String Decrypt(String text, int key){
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            decrypted.append((char) (text.charAt(i) - key));
        }
        return decrypted.toString();
    }
    public static void Logic(String[] args) {
        String operation = "";
        int key = 0;
        String data = "";
        boolean toFile = false;
        String filePath = "";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    operation = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    File file = new File(args[i + 1]);
                    try (Scanner sc = new Scanner(file)) {
                        data = sc.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error reading from the file at: "+args[i+1]);
                    }
                    break;
                case "-out":
                    toFile = true;
                    filePath = args[i+1];
                    break;
            }
        }
        String message = "";
        switch (operation) {
            case "enc":
                message = Encrypt(data, key);
                break;
            case "dec":
                message = Decrypt(data, key);
                break;
            default:
                System.out.println("Oops something went wrong :c");
                break;
        }
        if (toFile){
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(message);
            } catch (IOException e) {
                System.out.println("Error writing to file at: " + filePath);
            }
        }
        System.out.println(message);

    }
    public static void main(String[] args) {
            Logic(args);
    }
}
