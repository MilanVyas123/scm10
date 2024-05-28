package com.scm.scm10.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm10.helper.AppConstant;

@Service
public class CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String filename) {
        try {
            byte[] data = file.getBytes();

            // Capture the upload response
            Map<String, Object> uploadResult = cloudinary.uploader().upload(data,ObjectUtils.asMap("public_id", filename));
            // Get the secure URL from the upload response
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlFromPublicId(String publicID) {
        return cloudinary.url().transformation(new Transformation<>()
                .width(AppConstant.CONTACT_IMAGE_WIDTH)
                .height(AppConstant.CONTACT_IMAGE_HEIGHT)
                .crop(AppConstant.CONTACT_IMAGE_CROP))
                .generate(publicID);
    }

}
