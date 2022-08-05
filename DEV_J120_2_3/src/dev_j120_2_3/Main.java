/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dev_j120_2_3;


/**
 *
 * @author USER
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Введите URL текстового файла для анализа в следущем формате DiscName:\\folders\\fileName.txt:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();

        ScriptFile file = new ScriptFile(new File(url));
        try {
            file.read();
        } catch (IOException e) {
            System.out.println("Файл \"" + url + "\" не существует или не может быть прочитан.");
            System.exit(1);
        }
    }
}