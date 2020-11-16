package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

interface Strategy {
    public String Encrypt(String text, int key);
    public String Decrypt(String text, int key);
}

class ConcreteStrategyShift implements Strategy{

    @Override
    public String Encrypt(String text, int key){
        StringBuilder encrypted = new StringBuilder();
        char c;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) == ' ') {
                encrypted.append(' '); }
            if (text.charAt(i) >= 65 && text.charAt(i) <= 90) {
                if (text.charAt(i) + key > 90) {
                    c = (char) (text.charAt(i) - 26);
                    encrypted.append((char) (c + key));
                } else {
                    encrypted.append((char) (text.charAt(i) + key));
                }
            } else if (text.charAt(i) >= 97 && text.charAt(i) <= 122) {
                if (text.charAt(i) + key > 122) {
                    c = (char) (text.charAt(i) - 26);
                    encrypted.append((char) (c + key));
                } else {
                    encrypted.append((char) (text.charAt(i) + key));
                }
            }
        }
        return encrypted.toString();
    }

    @Override
    public String Decrypt(String text, int key){
        StringBuilder decrypted = new StringBuilder();
        char c;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) >= 65 && text.charAt(i) <= 90) {
                if (text.charAt(i) - key < 65) {
                    c = (char) (text.charAt(i) + 26);
                    decrypted.append((char) (c - key));
                } else {
                    decrypted.append((char) (text.charAt(i) - key));
                }
            } else if (text.charAt(i) >= 97 && text.charAt(i) <= 122) {
                if (text.charAt(i) - key < 97) {
                    c = (char) (text.charAt(i) + 26);
                    decrypted.append((char) (c - key));
                } else {
                    decrypted.append((char) (text.charAt(i) - key));
                }
            }
        }
        return decrypted.toString();
    }
}

class ConcreteStrategyUnicode implements Strategy {

    @Override
    public String Encrypt(String text, int key){
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            encrypted.append((char) (text.charAt(i) + key));
        }
        return encrypted.toString();
    }

    @Override
    public String Decrypt(String text, int key){
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            decrypted.append((char) (text.charAt(i) - key));
        }
        return decrypted.toString();
    }
}

class Context {
    private Strategy strategy;

    private String mode;
    private int key;
    private String data;
    private String alg;

    private boolean toFile;
    private String filePath;

    public void setStrategy(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    this.mode = args[i + 1];
                    break;
                case "-key":
                    this.key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    this.data = args[i + 1];
                    break;
                case "-in":
                    File file = new File(args[i + 1]);
                    try (Scanner sc = new Scanner(file)) {
                        this.data = sc.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error reading from the file at: "+args[i+1]);
                    }
                    break;
                case "-out":
                    this.toFile = true;
                    this.filePath = args[i+1];
                    break;
                case "-alg":
                    this.alg = args[i+1];
                    break;
            }
        }
    }

    public void executeStrategy() {
        String message = "";
        switch (mode) {
            case "enc":
                if (alg.equals("unicode")) {
                    ConcreteStrategyUnicode unicode = new ConcreteStrategyUnicode();
                    message = unicode.Encrypt(data, key);
                } else {
                    ConcreteStrategyShift shift = new ConcreteStrategyShift();
                    message = shift.Encrypt(data, key);
                }
                break;
            case "dec":
                if (alg.equals("unicode")) {
                    ConcreteStrategyUnicode unicode = new ConcreteStrategyUnicode();
                    message = unicode.Decrypt(data, key);
                } else {
                    ConcreteStrategyShift shift = new ConcreteStrategyShift();
                    message = shift.Decrypt(data, key);
                }
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
}


public class Main {
    public static void main(String[] args) {
            Context context = new Context();
            context.setStrategy(args);
            context.executeStrategy();
    }
}
