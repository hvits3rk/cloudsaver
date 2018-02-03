package com.romantupikov.cloudstorage.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    void init();

    MultipartFile saveOrUpdate(MultipartFile file);

    List<UriComponents> getUriComponents();

    Stream<String> listAll();

    Resource getAsResource(String filename);

    void deleteAll();
}
