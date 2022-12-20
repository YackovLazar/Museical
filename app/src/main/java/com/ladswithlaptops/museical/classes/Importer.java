package com.ladswithlaptops.museical.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.io.File;

public class Importer {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Importer() {}  // Prevents instantiation

    public static int moveFiles(Context context, String originalDirectoryPath, String destinationDirectoryPath) 
    {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int fileCount = 0;

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission, so prompt the user
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            // TODO Consider adding re-asking if disapproved in the past
        } else {
            // We have permission, so proceed with the file move
            File originalDirectory = new File(originalDirectoryPath);
            File destinationDirectory = new File("raw");

            File[] files = originalDirectory.listFiles();
            for (File file : files) 
            {
                if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav"))) 
                {
                    File destination = new File(destinationDirectory, file.getName());
                    boolean success = file.renameTo(destination);
                    if (success)
                    {
                        fileCount++;
                    }
                }
            }
        }
        return fileCount;
    }
}
//   /**
//     * Copy all music files in a folder from the internal storage to the res folder.
//     *
//     * @param context    Context in which to run the method
//     * @param folderName Name of the folder to copy files from
//     * @return Number of files copied
//     */
//    // Copy all music files in a folder from the internal storage to the raw folder in res
//    public static int copyMusicToRaw(Context context, String folderName) {
//        // Initialize a counter to keep track of the number of files copied
//        int filesCopied = 0;
//        // Get the AssetManager to access the files in the internal storage
//        AssetManager assetManager = context.getAssets();
//        try {
//            // Get the list of files in the specified folder
//            String[] fileNames = assetManager.list(folderName);
//            // Iterate through the list of files
//            for (String fileName : fileNames) {
//                // Check if the file is a music file
//                if (fileName.endsWith(".mp3") || fileName.endsWith(".wav")) {
//                    // Open the file for reading
//                    InputStream in = assetManager.open(folderName + "/" + fileName);
//                    // Get the Resources object to access the res folder
//                    Resources resources = context.getResources();
//                    // Create a new file in the raw folder with the same name as the original file
//                    // Open raw folder for writing
//                    OutputStream out = getRawFolderOutputStream(context, resources);
//                    // Copy the contents of the original file to the new file
//                    copyFile(in, out);
//                    // Close the input and output streams
//                    in.close();
//                    out.flush();
//                    out.close();
//                    // Increment the counter for the number of files copied
//                    filesCopied++;
//                    // Comment out the code that deletes the original file
//                    // File file = new File(folderName + "/" + fileName);
//                    // file.delete();
//                }
//            }
//        } catch (IOException e) {
//            // Log an error if something goes wrong
//            Log.e("FileCopier", "Failed to copy music files", e);
//        }
//        // Return the number of files copied
//        return filesCopied;
//    }
//
//    // Copy the contents of an InputStream to an OutputStream
//    private static void copyFile (InputStream in, OutputStream out) throws IOException {
//        // Use a buffer to improve performance
//        byte[] buffer = new byte[1024];
//        int read;
//        while ((read = in.read(buffer)) != -1) {
//            out.write(buffer, 0, read);
//        }
//    }
//
//    public static OutputStream getRawFolderOutputStream(Resources resources) throws IOException {
//        // Get the resource ID for the raw folder
//        int resId = resources.getIdentifier("raw", "raw", getPackageName());
//        // Open the raw resource as an AssetFileDescriptor
//        AssetFileDescriptor afd = resources.openRawResourceFd(resId);
//        // Get the ParcelFileDescriptor from the AssetFileDescriptor
//        ParcelFileDescriptor pfd = afd.getParcelFileDescriptor();
//        // Return the InputStream from the ParcelFileDescriptor
//        return new ParcelFileDescriptor.AutoCloseOutputStream(pfd);
//    }
//
//}
