package com.duy.run;

import android.content.Context;
import android.util.Log;

import com.duy.editor.file.FileManager;
import com.duy.project_files.ClassFile;
import com.duy.project_files.ProjectFile;
import com.spartacusrex.spartacuside.session.TermSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by duy on 18/07/2017.
 */

public class CommandManager {
    private static final String TAG = "CommandManager";
    private static final String LIBRARY_BUILDER_FILE = "builder/library_builder.sh";
    private static final String JAVA_BUILDER_FILE = "builder/java_builder.sh";

    public static void buildJarFile(Context context, TermSession termSession, ProjectFile pf) {
        Log.d(TAG, "compileAndRun() called with: filePath = [" + pf + "]");
        File home = context.getFilesDir();
        try {
            FileOutputStream fos = termSession.getTermOut();
            PrintWriter pw = new PrintWriter(fos);

            //set value for variable
            pw.println("PROJECT_PATH=" + pf.getProjectDir());
            pw.println("PROJECT_NAME=" + pf.getProjectName());
            ClassFile mainClass = pf.getMainClass();
            pw.println("MAIN_CLASS=" + mainClass.getName());
            pw.println("PATH_MAIN_CLASS=" + mainClass.getName().replace(".", "/"));
            pw.println("ROOT_PACKAGE=" + mainClass.getRootPackage());


            InputStream stream = context.getAssets().open(LIBRARY_BUILDER_FILE);
            String builder = FileManager.streamToString(stream).toString();
            pw.print(builder);
            pw.flush();

            File temp = new File(home, "tmp");
            if (!temp.exists()) temp.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void compileAndRun(Context context, TermSession termSession, ProjectFile pf) {
        Log.d(TAG, "compileAndRun() called with: filePath = [" + pf + "]");
        File home = context.getFilesDir();
        try {
            FileOutputStream fos = termSession.getTermOut();
            PrintWriter pw = new PrintWriter(fos);

            //set value for variable
            pw.println("PROJECT_PATH=" + pf.getProjectDir());
            pw.println("PROJECT_NAME=" + pf.getProjectName());
            pw.println("MAIN_CLASS=" + pf.getMainClass().getName());
            pw.println("PATH_MAIN_CLASS=" + pf.getMainClass().getName().replace(".", "/"));
            pw.println("ROOT_PACKAGE=" + pf.getMainClass().getRootPackage());

            InputStream stream = context.getAssets().open(JAVA_BUILDER_FILE);
            String builder = FileManager.streamToString(stream).toString();
            pw.print(builder);
            pw.flush();

            File temp = new File(home, "tmp");
            if (!temp.exists()) temp.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
