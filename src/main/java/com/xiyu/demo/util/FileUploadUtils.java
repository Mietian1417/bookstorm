package com.xiyu.demo.util;

import com.xiyu.demo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-20
 * Time: 17:19
 *
 * @author 陈子豪
 */
@Slf4j
public class FileUploadUtils {
    public static String generateUniqueFileName(Integer userId, MultipartFile file) {
        // 生成唯一文件名的逻辑
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return userId + "_" + UUID.randomUUID().toString() + fileExtension;
    }

    public static void saveFile(MultipartFile file, String newFileName, String dir) throws IOException {
        // 保存文件的逻辑
        File directory = new File(dir);
        if (!directory.exists() && directory.mkdirs()) {
            log.info("目录已成功创建: {}", directory.getAbsolutePath());
        } else {
            log.info("目录创建失败或目录已存在: {}", directory.getAbsolutePath());
        }

        String saveFilePath = dir + newFileName;
        file.transferTo(new File(saveFilePath));
    }

    public static void deleteOldImage(String oldImageUrl, String filePath) {
        // 删除旧图片的逻辑
        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            File oldImageFile = new File(filePath);
            if (oldImageFile.exists() && oldImageFile.delete()) {
                log.info("成功删除用户之前的图片: {}", oldImageFile);
            } else {
                log.error("删除图片失败或图片不存在: {}", oldImageFile);
            }
        }
    }
}
