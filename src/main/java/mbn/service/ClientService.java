package mbn.service;

import mbn.dtos.ClientDTO;
import mbn.model.Client;
import mbn.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService{
    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientByCodPatient(Long codPatient) {
        Optional<Client> client = clientRepository.findClientByCodPatient(codPatient);
        if(client.isPresent()){
            return client.get();
        }else{
            throw new RuntimeException("User not found");
        }
    }


    public List<Client> findEmployeeProjectsExampleMatcher(ClientDTO client) {
        return clientRepository.findAll(getSpecification(client));
    }
    private Specification<Client> getSpecification(ClientDTO clientDTO)
    {
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            criteriaQuery.distinct(true);

            if (isNotNullOrEmpty(clientDTO.getFilterText())) {
                Predicate predicateForData = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + clientDTO.getFilterText().toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + clientDTO.getFilterText().toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("cnp")).as(String.class), "%" + clientDTO.getFilterText().toUpperCase() + "%"));

                return criteriaBuilder.and(predicateForData);
            }else{
                throw new RuntimeException("");
            }
        };
    }

    public boolean isNotNullOrEmpty(String inputString)
    {
        return inputString != null && !inputString.isBlank() && !inputString.isEmpty() && !inputString.equals("undefined") && !inputString.equals("null") && !inputString.equals(" ");
    }

    public Client updateClient(Long clientId, Client client) {
        Client updatedClient = getClientByCodPatient(clientId);
        updatedClient.setPhone(client.getPhone());
        updatedClient.setAddress(client.getAddress());
        updatedClient.setLastName(client.getLastName());
        updatedClient.setFirstName(client.getFirstName());
        return clientRepository.save(updatedClient);
    }
}
