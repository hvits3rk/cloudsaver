package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import com.romantupikov.cloudstorage.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class FileUploadController {

    private final HttpServletRequest httpServletRequest;
    private final GridFsOperations gridFsOperations;
    private final AccountRepository accountRepository;
    private final FileService fileService;

    public FileUploadController(GridFsOperations gridFsOperations,
                                AccountRepository accountRepository,
                                HttpServletRequest httpServletRequest,
                                FileService fileService) {
        this.gridFsOperations = gridFsOperations;
        this.accountRepository = accountRepository;
        this.httpServletRequest = httpServletRequest;
        this.fileService = fileService;
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        Optional<Account> accountOptional = accountRepository.findByUsername(httpServletRequest.getRemoteUser());

        if (!accountOptional.isPresent()) {
            return "files/upload_form";
        }

//        Query query = new Query(Criteria.where("metadata.owner").is(accountOptional.get().getId()));
        Query query = new Query();

        ArrayList<UriComponents> arrayList = gridFsOperations.find(query).map(
                gridFSFile -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "downloadFile", gridFSFile.getFilename())
                        .build())
                .into(new ArrayList<>());

        model.addAttribute("files", arrayList);

        return "files/upload_form";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) throws IOException {

        fileService.saveFile(file);

        String fileName = file.getOriginalFilename();
        float fileSizeMB = (file.getSize() / 1024f) / 1024f;

        String message = String.format("Файл: %s (%.2fMb) залит!", fileName, fileSizeMB);

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/upload";
    }

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) throws IOException {

        GridFsResource file = fileService.getFileByOwnerAndFilename(httpServletRequest.getRemoteUser(), filename);

        if (file == null)
            return null;

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }


}
