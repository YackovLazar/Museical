package com.ladswithlaptops.museical.classes;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.ladswithlaptops.museical.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Importer {

    /**
     * Copy all music files in a folder from the internal storage to the res folder.
     *
     * @param context    Context in which to run the method
     * @param folderName Name of the folder to copy files from
     * @return Number of files copied
     */
    // Copy all music files in a folder from the internal storage to the raw folder in res
    public static int copyMusicToRaw(Context context, String folderName) {
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
                    // Get the Resources object to access the res folder
                    Resources resources = context.getResources();
                    // Create a new file in the raw folder with the same name as the original file
                    // Open raw folder for writing
                    OutputStream out = getRawFolderOutputStream(context, resources);
                    // Copy the contents of the original file to the new file
                    copyFile(in, out);
                    // Close the input and output streams
                    in.close();
                    out.flush();
                    out.close();
                    // Increment the counter for the number of files copied
                    filesCopied++;
                    // Comment out the code that deletes the original file
                    // File file = new File(folderName + "/" + fileName);
                    // file.delete();
                }
            }
        } catch (IOException e) {
            // Log an error if something goes wrong
            Log.e("FileCopier", "Failed to copy music files", e);
        }
        // Return the number of files copied
        return filesCopied;
    }

    // Copy the contents of an InputStream to an OutputStream
    private static void copyFile (InputStream in, OutputStream out) throws IOException {
        // Use a buffer to improve performance
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static OutputStream getRawFolderOutputStream(Resources resources) throws IOException {
        // Get the resource ID for the raw folder
        int resId = resources.getIdentifier("raw", "raw", getPackageName());
        // Open the raw resource as an AssetFileDescriptor
        AssetFileDescriptor afd = resources.openRawResourceFd(resId);
        // Get the ParcelFileDescriptor from the AssetFileDescriptor
        ParcelFileDescriptor pfd = afd.getParcelFileDescriptor();
        // Return the InputStream from the ParcelFileDescriptor
        return new ParcelFileDescriptor.AutoCloseOutputStream(pfd);
    }

}
