package com.teammovil.pettracker.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.*


object FileUtils {
    fun getPermanentImageDir (context: Context): String?{
        return File(context.filesDir, "PERMANENT_IMAGES").absolutePath
    }

    fun getTemporalImagesDir (context: Context): String?{
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
    }

    fun moveFile(
        originPath: String,
        destinationDirectory: String
    ) : String? {
        var input: InputStream? = null
        var out: OutputStream? = null
        try {

            val inputFile = File(originPath)
            if(inputFile.exists()) {

                //create output directory if it doesn't exist
                val dir = File(destinationDirectory)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val outputFile = File(destinationDirectory, inputFile.name)

                input = FileInputStream(originPath)
                out = FileOutputStream(outputFile.absolutePath)
                val buffer = ByteArray(1024)
                var read: Int = 0
                while (input.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                input.close()

                // write the output file
                out.flush()
                out.close()

                // delete the original file
                if(outputFile.exists()) {
                    inputFile.delete()
                    return outputFile.absolutePath
                }
            }
        } catch (fnfe1: FileNotFoundException) {
            Log.e("tag", fnfe1.message?.let{it}?:"")
        } catch (e: Exception) {
            Log.e("tag", e.message?.let{it}?:"")
        }
        return null
    }

    fun deleteFile(filePath: String?){
        filePath?.let {
            val sourceFile = File(filePath)
            if (sourceFile.exists()) {
                sourceFile.delete()
            }
        }
    }

    fun fileExist(filePath: String?): Boolean{
        filePath?.let {
            val sourceFile = File(filePath)
            return sourceFile.exists()
        }
            ?: return false
    }

    fun bitmapToFile(bitmap: Bitmap, path: String): File{
        val file = File(path)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.close()
        return file
    }
}