package com.keyou.fdcda.api.utils;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by zzq on 2017-11-01.
 */
public class ImageUtil {
    private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    
    public static ImageInfo getImagePixelInfo(File file) {
//        FileChannel fc;
//        if(file.exists() && file.isFile()){
//            try {
//                FileInputStream fs = new FileInputStream(file);
//                fc = fs.getChannel();
//                System.out.println(fc.size() + "-----fc.size()");
//            } catch (Exception e) {
//                logger.error("error:", e);
//            } 
//        }
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            logger.error("error:", e);
        }
        if (bi == null) {
            return null;
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        return new ImageInfo(width, height, file.getName(), file.length());
    }
    
    public static class ImageInfo {
        Integer width;
        Integer height;
        String name;
        Long fileSize;

        ImageInfo(Integer width, Integer height, String name, Long fileSize) {
            this.setWidth(width);
            this.setHeight(height);
            this.setName(name);
            this.setFileSize(fileSize);
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }
    }
}
