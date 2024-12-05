package com.aryanproject.dreamshop.service.image;

import com.aryanproject.dreamshop.dto.ImageDto;
import com.aryanproject.dreamshop.exceptions.ResourceNotFoundException;
import com.aryanproject.dreamshop.model.Image;
import com.aryanproject.dreamshop.model.Product;
import com.aryanproject.dreamshop.repository.ImageRepository;
import com.aryanproject.dreamshop.service.product.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceInterface{

    private final ImageRepository imageRepository;
    private final ProductServiceInterface productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"+ id));
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () ->{
            throw new ResourceNotFoundException("Image not found"+ id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product= productService.getProductById(productId);
        List<ImageDto> savedImageDto= new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image image= new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProductImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl= "/api/v1/images/image/download";
                String downloadUrl= buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage= imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto= new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            }
            catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image= getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setProductImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
