package com.romantupikov.cloudstorage.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final AccountRepository accountRepository;
    private final GridFsOperations gridFsOperations;
    private final HttpServletRequest httpServletRequest;

    public FileServiceImpl(AccountRepository accountRepository,
                           GridFsOperations gridFsOperations,
                           HttpServletRequest httpServletRequest) {
        this.accountRepository = accountRepository;
        this.gridFsOperations = gridFsOperations;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public GridFsResource getFileByOwnerAndFilename(String owner, String filename) {

        Optional<Account> optionalAccount = accountRepository.findByUsername(owner);
        if (!optionalAccount.isPresent()) {
            return null;
        }

//        Query query = new Query(GridFsCriteria.whereMetaData("ownerId").is(optionalAccount.get().getId())
//                .andOperator(GridFsCriteria.whereFilename().is(filename)));

        Query query = new Query(GridFsCriteria.whereFilename().is(filename));

        GridFSFile gridFSFile = gridFsOperations.findOne(query);

        if (gridFSFile != null) {
            return gridFsOperations.getResource(filename);
        } else {
            return null;
        }
    }

    @Override
    public boolean saveFile(MultipartFile file) throws IOException {

        Optional<Account> accountOptional = accountRepository.findByUsername(httpServletRequest.getRemoteUser());

//        String ownerId = accountOptional.get().getId();

        String fileType = file.getContentType();
        String fileName = file.getOriginalFilename();

        DBObject metaData = new BasicDBObject();
        metaData.put("originalName", fileName);
//        metaData.put("ownerId", ownerId);

        InputStream fileStream = file.getInputStream();
        gridFsOperations.store(fileStream, fileName, fileType, metaData);

        return true;
    }
}
