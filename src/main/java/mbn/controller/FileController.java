package mbn.controller;

import mbn.model.FileRequest;
import mbn.repository.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static mbn.util.ApplicationUtils.getExtensionByApacheCommonLib;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String path;
    private final String gdprPath;

    private FileRepository fileRepository;

    @Autowired
    public FileController(@Value("${mbn-images.save-path}") String path, @Value("${mbn-images.get-gdpr-path}") String gdprPath, FileRepository fileRepository) {
        this.path = path;
        this.gdprPath = gdprPath;
        this.fileRepository = fileRepository;
    }

    @GetMapping(value= "/image/{clientId}/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public  @ResponseBody byte[] getFile(@PathVariable Long clientId, @PathVariable UUID fileId, HttpServletResponse response) throws IOException {
        Optional<FileRequest> byId = fileRepository.findById(fileId);
        if(byId.isPresent()){
            String path1 = byId.get().getPath();
            File file = ResourceUtils.getFile(path1);
            byte[] array = method(file);
            logger.info("Request get image file for client with ID : " + clientId);
            String extention = getExtensionByApacheCommonLib(file.getName());

            if(extention.equals("pdf")){
                FileInputStream fis= new FileInputStream(path1);
                byte[] targetArray = new byte[fis.available()];
                fis.read(targetArray);
                return targetArray;
            }
            return array;
        }
        throw new RuntimeException("File does not exist");
    }

    @GetMapping(value= "/image/pdf/{clientId}/{fileId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public  @ResponseBody byte[] getFilePdf(@PathVariable Long clientId, @PathVariable UUID fileId, HttpServletResponse response) throws IOException {
        Optional<FileRequest> byId = fileRepository.findById(fileId);
        if(byId.isPresent()){
            String path1 = byId.get().getPath();
            File file = ResourceUtils.getFile(path1);
            byte[] array = method(file);
            logger.info("Request get pdf file for client with ID : " + clientId);
            String extention = getExtensionByApacheCommonLib(file.getName());

            if(extention.equals("pdf")){
                FileInputStream fis= new FileInputStream(path1);
                byte[] targetArray = new byte[fis.available()];
                fis.read(targetArray);
                return targetArray;
//                return ResponseEntity
//                        .ok()
//                        .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
//                        .body(targetArray);
            }
//            return ResponseEntity
//                    .ok()
//                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
//                    .body(array);
            return array;
        }
        throw new RuntimeException("File does not exist");

    }

    @GetMapping(value="/printing/pdf",produces= MediaType.APPLICATION_PDF_VALUE)
    public  @ResponseBody byte[]  print() {

        try {
            FileInputStream fis= new FileInputStream(new File(gdprPath));
            byte[] targetArray = new byte[fis.available()];
            fis.read(targetArray);
            return targetArray;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] method(File file) throws IOException {

        FileInputStream fl = new FileInputStream(file);

        byte[] arr = new byte[(int)file.length()];

        fl.read(arr);

        fl.close();

        return arr;
    }
}
