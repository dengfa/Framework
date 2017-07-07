/*
 * Copyright (C) 2016 The AndroidSupport Project
 */

package com.hyena.framework.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by yangzc on 16/11/7.
 */
public class ZipUtils {

    public static void extract(String path, ZipEntryWalker walker) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                if (walker != null) {
                    walker.onEntry(zis, entry);
                }
                entry = zis.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface ZipEntryWalker {
        void onEntry(ZipInputStream zis, ZipEntry entry) throws Exception;

    }

}
