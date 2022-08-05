/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_2_3;

/**
 *
 * @author USER
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScriptFile {
    private final Map<String, Integer> vars = new HashMap<>();
    private File file;

    public ScriptFile(File file) {
        if (file == null) throw new IllegalArgumentException("Файл не может быть пустым");
        this.file = file;
    }

    public void read() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                if (s.isEmpty()) {
                    continue;
                }
                if (s.charAt(0) == '#') {
                    continue;
                }

                String[] lineVars = s.split(" ");

                if (lineVars[0].equals("print")) {

                    String str = s.substring(6);
                    String resultStr = "";
                    String tempStr = "";
                    boolean startWritingString = false;
                    boolean startWritingValue = false;
                    char[] chars = str.toCharArray();
                    StringBuffer resultStrBuffer = new StringBuffer(resultStr);
                    StringBuffer tempStrBuffer = new StringBuffer(tempStr);

                    for (int i = 0; i < chars.length; i++) {

                        if (chars[i] == '"' && startWritingString == false) {
                            startWritingString = true;
                        } else if (chars[i] == '"' && startWritingString) {
                            startWritingString = false;
                        } else if (chars[i] != '"' && startWritingString) {
                            resultStrBuffer.append(chars[i]);
                        } else if (chars[i] == '$' && startWritingValue == false) {
                            startWritingValue = true;
                            tempStrBuffer.append(chars[i]);
                        } else if (chars[i] != ',' && (startWritingValue && i != chars.length - 1)) {
                            tempStrBuffer.append(chars[i]);
                        } else if (chars[i] != ',' && (startWritingValue && i == chars.length - 1)) {
                            tempStrBuffer.append(chars[i]);
                            startWritingValue = false;
                            resultStrBuffer.append(get(tempStrBuffer.toString()));
                            tempStrBuffer.setLength(0);
                        } else if (chars[i] == ',' && startWritingValue) {
                            startWritingValue = false;
                            resultStrBuffer.append(get(tempStrBuffer.toString()));
                            tempStrBuffer.setLength(0);
                        }
                    }

                    System.out.println(resultStrBuffer);

                } else if (lineVars[0].equals("set")) {

                    String var = "";
                    int value = 0;
                    boolean plus = true;
                    boolean equality = false;

                    for (int i = 1; i < lineVars.length; i++) {

                        if (lineVars[i].charAt(0) == '$') {
                            if (equality) {

                                if (plus) {
                                    value += get(lineVars[i]);
                                } else {
                                    value -= get(lineVars[i]);
                                }
                            } else {
                                var = lineVars[i];
                            }
                        } else if (lineVars[i].matches("[-+]?\\d+")) {
                            if (plus) {
                                value += Integer.parseInt(lineVars[i]);
                            } else {
                                value -= Integer.parseInt(lineVars[i]);
                            }
                        } else if (lineVars[i].charAt(0) == '-') {
                            plus = false;
                        } else if (lineVars[i].charAt(0) == '+') {
                            plus = true;
                        } else if (lineVars[i].charAt(0) == '=') {
                            equality = true;
                        } else {
                            throw new IOException("Неподдерживаемый символ");
                        }
                    }

                    set(var, value);

                } else {
                    throw new IOException("Неподдерживаемая команда");
                }
            }
        }
    }

    public Integer get(String varName) {

        Integer ing = vars.get(varName);

        if (ing == null) {
            throw new IllegalArgumentException("'varName' " + varName +" не найден");
        }

        return vars.get(varName);
    }

    public Integer set(String varName, int value) {
        if (varName == null) {
            throw new IllegalArgumentException("'varName' пуст");
        }

        return vars.put(varName, value);
    }
}
