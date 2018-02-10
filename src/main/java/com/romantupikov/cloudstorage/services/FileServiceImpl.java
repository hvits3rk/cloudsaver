package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.controllers.FileUploadController;
import com.romantupikov.cloudstorage.model.Account;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation;
    private final HttpServletRequest httpServletRequest;
    private final AccountService accountService;

    public FileServiceImpl(HttpServletRequest httpServletRequest, AccountService accountService) {
        this.rootLocation = Paths.get("root");
        this.httpServletRequest = httpServletRequest;
        this.accountService = accountService;
        init();
    }

    @Override
    public MultipartFile saveOrUpdate(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Пустой файл");
            }
            if (filename.contains("..")) {
                throw new RuntimeException("Файл содержит недопустимы символы " + filename);
            }

            Account account = accountService.getByUsername(httpServletRequest.getRemoteUser());

            Path path = Paths.get(account.getUsername(), filename);

            initDirs(rootLocation.resolve(account.getUsername()));

            Files.copy(file.getInputStream(), rootLocation.resolve(path),
                    StandardCopyOption.REPLACE_EXISTING);

            account.addFile(filename);
            accountService.saveOrUpdate(account);

        } catch (IOException e) {
            throw new RuntimeException("Не получилось сохранить файл " + filename, e);
        }

        return file;
    }

    @Override
    public Stream<String> listAll() {

        Account account = accountService.getByUsername(httpServletRequest.getRemoteUser());

        Stream<String> pathStream = account.getFiles().stream();

        return pathStream;
    }

    @Override
    public List<UriComponents> getUriComponents() {

        return listAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "downloadFile", path).build())
                .collect(Collectors.toList());
    }

    @Override
    public Resource getAsResource(String filename) {
        Account account = accountService.getByUsername(httpServletRequest.getRemoteUser());

        try {
            Optional<String> fileOptional = account.getFiles().stream()
                    .filter(file -> file.equals(filename))
                    .findFirst();

            if (!fileOptional.isPresent()) {
                throw new RuntimeException("Файл не найден: " + filename);
            }

            Path file = rootLocation.resolve(Paths.get(account.getUsername(), fileOptional.get()));

            System.out.println("file path: " + file);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Не получилось прочитать: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Не получилось прочитать: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось инициализировать рут директорию", e);
        }
    }

    private void initDirs(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось инициализировать директорию", e);
        }
    }

//    private String generageChecksum(MultipartFile file) throws NoSuchAlgorithmException, IOException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(file.getBytes());
//        byte[] digest = md.digest();
//        return DatatypeConverter.printHexBinary(digest).toUpperCase();
//    }
}
