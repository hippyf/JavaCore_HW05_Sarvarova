package ru.geekbrains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;

/**
 * Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий)
 * во вновь созданную папку ./backup
 */
public class Program {

    private static final Random random = new Random();

    private static final int CHAR_BOUND_L = 65; // номер начального символа
    private static final int CHAR_BOUND_H = 90; // номер конечного символа
    public static final String BACKUP_FOLDER = "/backup";

    public static void main(String[] args) throws IOException {

        createFiles(5);
        copyFiles(".");
    }

    // Создаём файлы

    /**
     * Метод генерации некоторой последовательности символов
     *
     * @param amount кол-во символов
     * @return
     */
    private static String generateSymbols(int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringBuilder.append((char) random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        }
        return stringBuilder.toString();
    }

    /**
     * Записать последовательность символов в файл
     *
     * @param fileName имя файла
     * @param length   длина последовательности символов
     * @throws IOException
     */
    private static void writeFileContents(String fileName, int length) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(generateSymbols(length).getBytes());
        }
    }

    /**
     * Метод создания нескольких файлов
     *
     * @param count кол-во файлов, которые нужно создать
     * @return файлы
     * @throws IOException
     */

    private static String[] createFiles(int count) throws IOException {
        String[] fileNames = new String[count];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = "file " + (i + 1) + ".txt";
            writeFileContents(fileNames[i], 100);
            System.out.printf("Файл %s создан\n", fileNames[i]);
        }
        return fileNames;
    }

    /**
     * Метод копирования файлов в папку
     * @param path путь
     */
    private static void  copyFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("Неверно указан путь");
            return;
        }
        if (files.length == 0) {
            System.out.println("Папка пустая");
            return;
        }
        File backup = new File(path + BACKUP_FOLDER);
        if (!backup.exists()) {
            if (!backup.mkdir()) {
                System.out.println("Ошибка при слздании папки");
            }
        }
        for (File f : files) {
            File backupFile = new File (backup.getPath() + "/" + f.getName());
            if (f.isFile()) {
                try {
                    Files.copy(f.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println("IOException");
                }
            }
        }
    }
}
