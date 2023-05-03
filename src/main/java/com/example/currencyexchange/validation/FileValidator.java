package com.example.currencyexchange.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<FileImage, MultipartFile> {
    private static final Integer MB = 1024 * 1024;
    private long maxSizeInMB;

    @Override
    public void initialize(FileImage fileImage) {
        this.maxSizeInMB = fileImage.maxSizeInMB();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null)
            return true;

        String contentType = multipartFile.getContentType();
        if (!(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg")))
            return false;

        return multipartFile.getSize() < maxSizeInMB * MB;
    }
}
