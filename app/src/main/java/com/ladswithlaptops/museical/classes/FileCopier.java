package com.ladswithlaptops.museical.classes;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopier {

    // Copy all music files in a folder from the internal storage to the res folder and delete the original files
    public static int copyMusicToResAndDelete(Context context, String folderName) {
        // Initialize a counter to keep track of the number of files copied
        int filesCopied = 0;
        // Get the AssetManager to access the files in the internal storage
        AssetManager assetManager = context.getAssets();
        try {
            // Get the list of files in the specified folder
            String[] fileNames = assetManager.list(folderName);
            // Iterate through the list of files
            for (String fileName : fileNames) {
                // Check if the file is a music file
                if (fileName.endsWith(".mp3") || fileName.endsWith(".wav")) {
                    // Open the file for reading
                    InputStream in = assetManager.open(folderName + "/" + fileName);
                    // Create a new file in the res folder with the same name as the original file
                    File outFile = new File(context.getFilesDir(), fileName);
                    // Open the file for writing
                    OutputStream out = new FileOutputStream(outFile);
                    // Copy the contents of the original file to the new file
                    copyFile(in, out);
                    // Close the input and output streams
                    in.close();
                    out.flush();
                    out.close();
                    // Increment the counter for the number of files copied
                    filesCopied++;
                    // Delete the original file
                    File file = new File(folderName + "/" + fileName);
                    file.delete();
                }
            }
        } catch(IOException e) {
            // Log an error if something goes wrong
            Log.e("FileCopier", "Failed to copy music files", e);
        }
        // Return the number of files copied
        return filesCopied;
    }

    // Copy the contents of an InputStream to an OutputStream
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        // Use a buffer to improve performance
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
