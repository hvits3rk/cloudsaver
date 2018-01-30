package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class FileUploadController {

    private final HttpServletRequest httpServletRequest;
    private final FileService fileService;

    public FileUploadController(HttpServletRequest httpServletRequest,
                                FileService fileService) {
        this.httpServletRequest = httpServletRequest;
        this.fileService = fileService;
        fileService.init();
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {

        List<UriComponents> uriComponents = fileService.getUriComponents();

        model.addAttribute("files", uriComponents);

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
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {

        Resource file = fileService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
