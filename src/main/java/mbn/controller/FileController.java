package mbn.controller;

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
import java.util.UUID;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String path;
    private final String gdprPath;

    @Autowired
    public FileController(@Value("${mbn-images.save-path}") String path, @Value("${mbn-images.get-gdpr-path}") String gdprPath) {
        this.path = path;
        this.gdprPath = gdprPath;
    }

    @GetMapping(value= "/image/{clientId}/{fileId}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<byte[]> getFile(@PathVariable Long clientId, @PathVariable UUID fileId, HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile(path+ clientId + "/"+fileId+".png");
        byte[] array = method(file);
        logger.info("Request get file for client with ID: " + clientId);
        return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(array);
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
