package com.romantupikov.cloudstorage.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class FileUploadController {

    @Autowired
    GridFsOperations gridFsOperations;

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        ArrayList<UriComponents> arrayList = gridFsOperations.find(new Query()).map(
                gridFSFile -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "downloadFile", gridFSFile.getFilename())
                        .build())
                .into(new ArrayList<>());

//        for (UriComponents uriComponents : arrayList) {
//            System.out.println(uriComponents.getPathSegments().get(1));
//        }

        model.addAttribute("files", arrayList);

        return "upload_form";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) throws IOException {
        DBObject metaData = new BasicDBObject();

        String owner = "hvits3rk";
        String fileType = file.getContentType();
        String fileName = file.getOriginalFilename();
        float fileSizeMB = (file.getSize() / 1024f) / 1024f;

        metaData.put("originalName", fileName);
        metaData.put("owner", owner);

        InputStream fileStream = file.getInputStream();

        gridFsOperations.store(fileStream, fileName, fileType, metaData);

        String message = String.format("Файл: %s (%.2fMb) залит!", fileName, fileSizeMB);

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/upload";
    }

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) throws IOException {

        GridFsResource file = gridFsOperations.getResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
