package mbn.service;

import mbn.dtos.FilterDTO;
import mbn.exceptions.CnpException;
import mbn.model.Client;
import mbn.model.FileRequest;
import mbn.repository.ClientRepository;
import mbn.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static mbn.util.ApplicationUtils.isNotNullOrEmpty;

@Service
public class ClientService{
    private ClientRepository clientRepository;
    private FileRepository fileRepository;
    private final String path;

    @Autowired
    public ClientService(@Value("${mbn-images.save-path}") String path, ClientRepository clientRepository, FileRepository fileRepository) {
        this.clientRepository = clientRepository;
        this.fileRepository = fileRepository;
        this.path = path;
    }

    public Client createClient(Client client) throws CnpException {
        if(clientRepository.findClientByCnp(client.getCnp()).isPresent()){
            throw new CnpException("CNP-ul exista deja in baza de date");
        }
        return clientRepository.save(client);
    }

    public Client getClientByCodPatient(Long codPatient) {
        Optional<Client> client = clientRepository.findClientByCodPatient(codPatient);
        if(client.isPresent()){
            return client.get();
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public List<Client> findClients(FilterDTO client) {
        List<Client> all = clientRepository.findAll(getSpecification(client));
        if(all.isEmpty()){
            return new ArrayList<>();
        }else{
            return all;
        }
    }
    private Specification<Client> getSpecification(FilterDTO clientDTO)
    {
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            criteriaQuery.distinct(true);
            Expression<String> exp1 = criteriaBuilder.concat(criteriaBuilder.upper(root.get("firstName")), " ");
            exp1 = criteriaBuilder.concat(exp1, criteriaBuilder.upper(root.get("lastName")));
            Expression<String> exp2 = criteriaBuilder.concat(criteriaBuilder.upper(root.get("lastName")), " ");
            exp2 = criteriaBuilder.concat(exp2, criteriaBuilder.upper(root.get("firstName")));

            if (isNotNullOrEmpty(clientDTO.getFilterText())) {
                Predicate predicateForData = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + clientDTO.getFilterText().toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + clientDTO.getFilterText().toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("cnp")).as(String.class), "%" + clientDTO.getFilterText().toUpperCase() + "%"),
                        criteriaBuilder.or(criteriaBuilder.like(exp1, "%"+ clientDTO.getFilterText().toUpperCase() +"%"), criteriaBuilder.like(exp2, "%"+ clientDTO.getFilterText().toUpperCase() +"%")));

                return criteriaBuilder.and(predicateForData);
            }else{
                throw new RuntimeException("");
            }
        };
    }

    public Client updateClient(Long clientId, Client client) {
        Client updatedClient = getClientByCodPatient(clientId);
        updatedClient.setPhone(client.getPhone());
        updatedClient.setAddress(client.getAddress());
        updatedClient.setLastName(client.getLastName());
        updatedClient.setFirstName(client.getFirstName());
        updatedClient.setGdprCompleted(client.isGdprCompleted());
        return clientRepository.save(updatedClient);
    }

    public Client updateClientImages(Long clientId, MultipartFile[] files) {
        Client updatedClient = getClientByCodPatient(clientId);
        Set<FileRequest> newFiles;
        if(updatedClient.getFileSet() != null){
            newFiles = updatedClient.getFileSet();
        }else{
            newFiles = new HashSet<>();
        }
        Arrays.stream(files).forEach(file -> {
            try {
                String directoryName = path.concat(String.valueOf(clientId));

                File directory = new File(directoryName);
                if (! directory.exists()){
                    directory.mkdir();
                }

                FileRequest fileRequest = new FileRequest();
                fileRequest.setName(file.getOriginalFilename());
                fileRequest.setPatientId(clientId);
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
        updatedClient.setFileSet(newFiles);
        return clientRepository.save(updatedClient);
    }
}
