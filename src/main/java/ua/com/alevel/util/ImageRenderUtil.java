package ua.com.alevel.util;

import org.springframework.web.multipart.MultipartFile;
import ua.com.alevel.properties.StaticProperties;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public final class ImageRenderUtil {

    private static String pathToImage;
    private static String newPostId;


    private ImageRenderUtil() {
        throw new IllegalStateException("Utility class.");
    }


    public static String writeImageToFilesAndGetPath(MultipartFile multipartFile, String newPostId) throws IOException {
        ImageRenderUtil.newPostId = newPostId; //Id последнего поста, что остылаем в PostFacadeImpl, под этим ИД будет храниться фотка
        return savingLargeImage(multipartFile);
    }

    private static String savingLargeImage(MultipartFile image) throws IOException {

        //Генерация пути для сохранения фотки
        pathToImage = generatePathToPackageForSavedImage() + "/" + newPostId + getExtensionFile(Objects.requireNonNull(image.getOriginalFilename()));
        //Сохранение фотки
        image.transferTo(new File(pathToImage));

        return pathToImage;
    }


    private static String generatePathToPackageForSavedImage() {
        //Генерация пути для сохранения фотки
        return StaticProperties.PATH_PROJECT + "/src/main/resources/static/files";
    }

    private static String getExtensionFile(String originalFileName) {
        //Получение формата фотки(jpg, png и т.п.)
        return originalFileName.substring(originalFileName.lastIndexOf('.'));
    }

}
