package mbn.service;

import mbn.model.Client;
import mbn.model.FileRequest;
import mbn.model.Registration;
import mbn.repository.ClientRepository;
import mbn.repository.FileRepository;
import mbn.repository.RegistrationRepository;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class RegistrationService {

    private RegistrationRepository registrationRepository;
    private ClientRepository clientRepository;
    private FileRepository fileRepository;

    private final static String PATH = "/Users/albica/Desktop/mbn/mbn-service/src/main/resources/static/documents/";

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, ClientRepository clientRepository,
                               FileRepository fileRepository) {
        this.registrationRepository = registrationRepository;
        this.clientRepository = clientRepository;
        this.fileRepository = fileRepository;
    }

    public Client getClient(Long codPatient) {
        Optional<Client> client = clientRepository.findClientByCodPatient(codPatient);
        if(client.isPresent()){
            return client.get();
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public Registration createRegistration (Registration registration, Long clientId, MultipartFile[] files) throws Exception{
        Client client = getClient(clientId);
        registration.setClient(client);
        Set<FileRequest> newFiles = new HashSet<>();
        Arrays.stream(files).forEach(file -> {
            try {
                String directoryName = PATH.concat(String.valueOf(clientId));
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
        registration.setFileSet(newFiles);

        return registrationRepository.save(registration);
    }

    public Registration getRegistration(Long registrationId) throws Exception {
        Optional<Registration> byId = registrationRepository.findById(registrationId);
        if(byId.isPresent()){
            return byId.get();
        }else{
            throw new Exception("Registration doesnt exist");

        }
    }


}
