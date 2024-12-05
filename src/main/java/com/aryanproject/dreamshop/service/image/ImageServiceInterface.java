package com.aryanproject.dreamshop.service.image;

import com.aryanproject.dreamshop.dto.ImageDto;
import com.aryanproject.dreamshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageServiceInterface {
    Image getImageById(Long id);
    void deleteById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
