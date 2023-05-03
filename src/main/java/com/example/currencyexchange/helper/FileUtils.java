package com.example.currencyexchange.helper;

import com.example.currencyexchange.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.python.apache.commons.compress.utils.FileNameUtils;
import org.python.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FileUtils {
    private final static Path root = Paths.get("./images");
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static MultipartFile getFile() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) ((ServletRequestAttributes) requestAttributes).getRequest();
            MultipartFile[]  multipartFiles= null;
            Set set = multipartRequest.getFileMap().entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
            }
        }
        return null;
    }

    static {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            logger.error("Could not initialize folder for upload!");
            throw new RuntimeException(e);
        }
    }

    public static byte[] getImage(String decode) {
        String path = EncryptionUtils.base64Decoder(decode);
        Path file = root.resolve(path);
        UrlResource urlResource;
        try {
            urlResource = new UrlResource(file.toUri());
            if (urlResource.exists() || urlResource.isReadable()) {
                return ByteStreams.toByteArray(urlResource.getInputStream());
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String save(MultipartFile file, String directory) throws IOException {
        if (directory == null)
            return null;

        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        Path path = root.resolve(UserInfo.userId());
        Path absPath = Paths.get(path.toUri());
        Files.createDirectories(absPath);
        String fileName = EncryptionUtils.base64Encoder(imageKey(directory, EncryptionUtils.random(10)));
        Files.copy(file.getInputStream(), path.resolve(fileName + "." + extension));
        return EncryptionUtils.base64Encoder(path.toUri() + "/" + fileName + "." + extension);
    }

    public static void del(Object delDecode) throws IOException {
        if (delDecode != null) {
            Files.deleteIfExists(root.resolve(EncryptionUtils.base64Decoder(delDecode.toString())));
        }
    }

    public static String delAndSave(MultipartFile file, String directory, Object delDecode) throws IOException {
        del(delDecode);
        return save(file, directory);
    }

    public static String imageKey(String directory, String name) {
        return directory + "_" + name;
    }
}
