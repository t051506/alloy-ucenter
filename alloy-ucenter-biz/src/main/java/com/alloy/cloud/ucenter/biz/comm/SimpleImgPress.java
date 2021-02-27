package com.alloy.cloud.ucenter.biz.comm;

import cn.hutool.core.util.RandomUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 简单图片压缩
 *
 * @author tankechao
 * @since 2021-01-27 16:01:34
 */
public class SimpleImgPress {

    private final static Logger logger = LoggerFactory.getLogger(SimpleImgPress.class);
    //图片最边长
    private final static long IMAGE_LENGTH_SIZE = 2048L;
    //图片大小
    private final static long IMAGE_SIZE = 1048576L;
    //指定图片大小
    private final static long DES_FILE_SIZE = 1024L;

    /**
     * 获取图片最长边长度
     *
     * @param params MultipartFile
     * @return int 图片最长边长度
     */
    public static int getImageLengthOfSide(MultipartFile params) {
        int lengthSize = 0;
        Map<String, Integer> result = new HashMap<>();
        // 获取图片格式
        String suffixName = getSuffixNameInfo(params);
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffixName);
            ImageReader reader = readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(params.getInputStream());
            reader.setInput(iis, true);
            result.put("width", reader.getWidth(0));
            result.put("height", reader.getHeight(0));
            if (reader.getWidth(0) > reader.getHeight(0)) {
                lengthSize = reader.getWidth(0);
            } else {
                lengthSize = reader.getHeight(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lengthSize;
    }

    /**
     * 获取图片格式
     *
     * @param params MultipartFile
     * @return 图片格式
     */
    public static String getSuffixNameInfo(MultipartFile params) {
        String result = "";
        // 图片后缀
        String suffixName = params.getOriginalFilename().substring(
                params.getOriginalFilename().lastIndexOf("."));
        if (suffixName.indexOf("png") > 0) {
            result = "png";
        } else if (suffixName.indexOf("jpg") > 0) {
            result = "jpg";
        } else if (suffixName.indexOf("jpeg") > 0) {
            result = "jpeg";
        }
        return result;
    }


    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
//        double accuracy = 0.85;
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            logger.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
                    "", srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小，单位kb
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.6;
//            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.5;
//            accuracy = 0.8;
        } else if (size < 3275) {
            accuracy = 0.4;
//            accuracy = 0.7;
        } else {
            accuracy = 0.3;
        }
        logger.info("accuracy = {}", accuracy);
        accuracy = 0.3;
        return accuracy;
    }

    /**
     * base64 转MultipartFile
     *
     * @param base64 图片base64
     * @return MultipartFile
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            // 注意base64是否有头信息，如：data:image/jpeg;base64,。。。
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            logger.error("base64 转MultipartFile异常:{}", e.getMessage(), e);
            return null;
        }
    }


    /**
     * 压缩单个图片
     *
     * @return MultipartFile
     */
    public static MultipartFile[] byte2Base64StringFun(MultipartFile[] fileImg) {
        MultipartFile[] result = fileImg;
        // 获取图片最长边
        int imageLengthSize = SimpleImgPress.getImageLengthOfSide(fileImg[0]);
        long imageSize = fileImg[0].getSize();

        if (imageLengthSize > IMAGE_LENGTH_SIZE || imageSize > IMAGE_SIZE) {
            BASE64Encoder encoder = new BASE64Encoder();
            String imgData1 = null;
            try {
                InputStream inputStream = fileImg[0].getInputStream();
                byte[] imgData = SimpleImgPress.compressPicForScale(StreamUtils.copyToByteArray(inputStream), DES_FILE_SIZE);
                imgData1 = "data:" + fileImg[0].getContentType() + ";base64," + encoder.encode(imgData);
                MultipartFile def = SimpleImgPress.base64ToMultipart(imgData1);
                result[0] = def;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 压缩图片
     *
     * @return 压缩后的图片
     */
    public static MultipartFile[] pressImages(MultipartFile[] fileImg) {
        MultipartFile[] result = fileImg;
        int index = 0;
        for (MultipartFile multipartFile : fileImg) {
            // 获取图片最长边
            int imageLengthSize = SimpleImgPress.getImageLengthOfSide(multipartFile);
            long imageSize = multipartFile.getSize();

            if (imageLengthSize > IMAGE_LENGTH_SIZE || imageSize > IMAGE_SIZE) {
                BASE64Encoder encoder = new BASE64Encoder();
                try {
                    InputStream inputStream = multipartFile.getInputStream();
                    byte[] imgData = SimpleImgPress.compressPicForScale(StreamUtils.copyToByteArray(inputStream), DES_FILE_SIZE);
                    String imgData1 = "data:" + multipartFile.getContentType() + ";base64," + encoder.encode(imgData);
                    MultipartFile def = SimpleImgPress.base64ToMultipart(imgData1);
                    result[index] = def;
                    index++;
                } catch (IOException e) {
                    logger.error("图片:{}压缩失败:{}", multipartFile.getOriginalFilename(), e.getMessage(), e);
                }
            }
        }
        return result;
    }

    private static void transfer(MultipartFile multipartFile) throws IOException, IllegalStateException {
        String filePath = "z:/upload/";
        String path = filePath + RandomUtil.randomString(5) + "-" + multipartFile.getOriginalFilename();
        File dest = new File(path);
        //检测是否存在该目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //写入文件
        multipartFile.transferTo(dest);
    }

    private static void transfers(MultipartFile[] multipartFiles) throws IOException, IllegalStateException {
        String filePath = "z:/upload/";
        for (MultipartFile multipartFile : multipartFiles) {
            String path = filePath + RandomUtil.randomString(5) + "-" + multipartFile.getOriginalFilename();
            File dest = new File(path);
            //检测是否存在该目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            //写入文件
            multipartFile.transferTo(dest);
        }
    }
}
