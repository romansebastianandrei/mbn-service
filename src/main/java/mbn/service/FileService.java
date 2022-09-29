package mbn.service;

import mbn.model.FileRequest;
import mbn.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FileService {

    private final static String PATH = "/Users/albica/Desktop/mbn/mbn-service/src/main/resources/static/documents/";

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileRequest> saveFileForRegistration(MultipartFile[] files, Long clientId, Long registrationId) {
        Set<FileRequest> newFiles = new HashSet<>();
        Arrays.stream(files).forEach(file -> {
            try {
                String directoryName = PATH.concat(String.valueOf(clientId)).concat((String.valueOf(registrationId)));
//                String fileName = file.getOriginalFilename();

                File directory = new File(directoryName);
                if (! directory.exists()){
                    directory.mkdir();
                }

                FileRequest fileRequest = new FileRequest();
//                fileRequest.setPath(f.getPath());
                fileRequest.setName(file.getOriginalFilename());
                FileRequest save = fileRepository.save(fileRequest);

                File f = new File(directoryName + "/" + save.getFileId()+ ".png");
                file.transferTo(f);

                fileRequest.setPath(f.getPath());

                newFiles.add(save);
                System.out.println("File successfully saved as " + f.getAbsolutePath());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return fileRepository.saveAll(newFiles);
    }
}
