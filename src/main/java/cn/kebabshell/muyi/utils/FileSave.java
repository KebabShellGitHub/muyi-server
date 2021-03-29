package cn.kebabshell.muyi.utils;

import cn.kebabshell.muyi.config.MyStaticConfig;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by KebabShell
 * on 2019/11/29 下午 12:47
 * 用于保存图片
 */
public class FileSave {
    /**
     * 保存
     * @param file
     * @return
     */
    public static String save(MultipartFile file, String folder) {
        String name = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + "_" + name;
        File dir = new File(MyStaticConfig.DIR + folder);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, newName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newName;
    }

    // private static void myWatermark(String folder, String picFileName){
    //     // picUrl：原图片地址
    //     String picUrl = MyStaticConfig.DIR + folder + picFileName;
    //     // 水印图片 相对于resource目录
    //     String watermark = "/watermark.png";
    //     // 输出到文件
    //     String outputFile = "/Users/yanpanyi/Desktop/test.jpeg";
    //     // 不透明度
    //     float opacity = 0.25f;
    //
    //     try {
    //         // 获取原图文件
    //         File file = new File(picUrl);
    //         // ImageIO读取图片
    //         BufferedImage image = ImageIO.read(file);
    //
    //         Thumbnails.of(image)
    //                 // 设置图片大小
    //                 .size(image.getWidth(), image.getHeight())
    //                 // 加水印 参数：1.水印位置 2.水印图片 3.不透明度0.0-1.0
    //                 .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(WatermarkDemo
    //                         .class.getResourceAsStream(watermark)), opacity)
    //                 // 输出到文件
    //                 .toFile(outputFile);
    //
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }


}
