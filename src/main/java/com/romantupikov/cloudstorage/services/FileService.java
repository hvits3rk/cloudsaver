package com.romantupikov.cloudstorage.services;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    GridFsResource getFileByOwnerAndFilename(String owner, String filename);

    boolean saveFile(MultipartFile file) throws IOException;
}
