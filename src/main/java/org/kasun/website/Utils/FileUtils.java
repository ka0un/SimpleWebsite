package org.kasun.website.Utils;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FileUtils {
    private Plugin plugin;

    public void copyFileFromResources(String fileName, File destinationFolder) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             FileOutputStream outputStream = new FileOutputStream(new File(destinationFolder, fileName))) {

            if (inputStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

    public static boolean renameFile(String oldFilePath, String newFileName) {
        File oldFile = new File(oldFilePath);

        if (!oldFile.exists()) {
            System.out.println("Old file does not exist.");
            return false;
        }

        String parentDirectory = oldFile.getParent();
        File newFile = new File(parentDirectory, newFileName);

        if (newFile.exists()) {
            System.out.println("A file with the new name already exists.");
            return false;
        }

        boolean renamed = oldFile.renameTo(newFile);
        return renamed;
    }

    public static boolean doesFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully: " + filePath);
                return true;
            } else {
                System.err.println("Unable to delete file: " + filePath);
                return false;
            }
        } else {
            System.err.println("File not found: " + filePath);
            return false;
        }
    }

    public static void unarchiveZipFile(String zipFilePath, String extractToPath) {
        byte[] buffer = new byte[1024];

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;

            // Iterate over each entry in the ZIP file
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String entryName = zipEntry.getName();
                String entryPath = extractToPath + File.separator + entryName;

                // Create the directories for the entry path if they don't exist
                new File(entryPath).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(entryPath)) {
                    int len;
                    // Read and write the current entry to the file
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
