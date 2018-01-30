package com.romantupikov.cloudstorage.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    //    GridFsResource getFileByOwnerAndFilename(String owner, String filename);
//
    void saveFile(MultipartFile file);

    //
    List<UriComponents> getUriComponents();

    void init();

    Stream<String> loadAll();

    Resource loadAsResource(String filename);

    void deleteAll();
}
