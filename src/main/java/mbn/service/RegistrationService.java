package mbn.service;

import mbn.dtos.FilterDTO;
import mbn.model.Client;
import mbn.model.FileRequest;
import mbn.model.Registration;
import mbn.repository.ClientRepository;
import mbn.repository.FileRepository;
import mbn.repository.RegistrationRepository;
import org.flywaydb.core.internal.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static mbn.util.ApplicationUtils.isNotNullOrEmpty;

@Service
public class RegistrationService {

    private RegistrationRepository registrationRepository;
    private ClientRepository clientRepository;
    private FileRepository fileRepository;
    private final String path;

    @Autowired
    public RegistrationService(@Value("${mbn-images.save-path}") String path, RegistrationRepository registrationRepository,
                               ClientRepository clientRepository, FileRepository fileRepository) {
        this.registrationRepository = registrationRepository;
        this.clientRepository = clientRepository;
        this.fileRepository = fileRepository;
        this.path = path;
    }

    public Client getClient(Long codPatient) {
        Optional<Client> client = clientRepository.findClientByCodPatient(codPatient);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public int calculateAge(Date birthDate) {
        LocalDate localDate = convertToLocalDateViaSqlDate(birthDate);
        return Period.between(localDate, LocalDate.now()).getYears();
    }

    public Registration createRegistration(Registration registration, Long clientId, MultipartFile[] files) throws ParseException {
        Client client = getClient(clientId);
        Registration newRegistration = validateRegistration(registration);
        newRegistration.setClient(client);

        Date dateOfConsultation = registration.getDateOfConsultation();
        newRegistration.setDateOfConsultation(dateOfConsultation);

        newRegistration.setAgeAtConsultation(calculateAge(client.getDateOfBirth()));

        Set<FileRequest> newFiles = new HashSet<>();
        if (files != null) {
            Arrays.stream(files).forEach(file -> {
                try {
                    String directoryName = path.concat(String.valueOf(clientId));

                    File directory = new File(directoryName);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    FileRequest fileRequest = new FileRequest();
                    fileRequest.setName(file.getOriginalFilename());
                    fileRequest.setPatientId(clientId);

                    FileRequest save = fileRepository.save(fileRequest);

                    File f = new File(directoryName + "/" + save.getFileId() + ".png");
                    file.transferTo(f);

                    fileRequest.setPath(f.getPath());

                    newFiles.add(save);
                    System.out.println("File successfully saved as " + f.getAbsolutePath());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            newRegistration.setFileSet(newFiles);
        }

        return registrationRepository.save(newRegistration);
    }

    private Registration validateRegistration(Registration registration) {

        Registration newRegistration = new Registration();

        if (isNotNullOrEmpty(registration.getConsultedDoctor())) {
            newRegistration.setConsultedDoctor(registration.getConsultedDoctor());
        } else {
            newRegistration.setConsultedDoctor("N/A");
        }
        if (isNotNullOrEmpty(registration.getRecommendedDoctor())) {
            newRegistration.setRecommendedDoctor(registration.getRecommendedDoctor());
        } else {
            newRegistration.setRecommendedDoctor("N/A");
        }
        if (isNotNullOrEmpty(registration.getDiagnostic())) {
            newRegistration.setDiagnostic(registration.getDiagnostic());
        } else {
            newRegistration.setDiagnostic("N/A");
        }
        if (isNotNullOrEmpty(registration.getTreatment())) {
            newRegistration.setTreatment(registration.getTreatment());
        } else {
            newRegistration.setTreatment("N/A");
        }
        if (isNotNullOrEmpty(registration.getInvestigation())) {
            newRegistration.setInvestigation(registration.getInvestigation());
        } else {
            newRegistration.setInvestigation("N/A");
        }
        if (isNotNullOrEmpty(registration.getRecommendation())) {
            newRegistration.setRecommendation(registration.getRecommendation());
        } else {
            newRegistration.setRecommendation("N/A");
        }
        return newRegistration;
    }

    public Registration getRegistration(Long registrationId) throws Exception {
        Optional<Registration> byId = registrationRepository.findById(registrationId);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new Exception("Registration doesn't exist");
        }
    }

    public List<Registration> findRegistrations(FilterDTO filterDTO, Long clientId) throws ParseException {
        getClient(clientId);
        List<Registration> all = registrationRepository.findAll(getSpecification(filterDTO, clientId));
        if (all.isEmpty()) {
            return new ArrayList<>();
        } else {
            return all;
        }
    }

    private Specification<Registration> getSpecification(FilterDTO filterDTO, Long clientId) throws ParseException {
        Date searchDate = new Date();
        if (filterDTO.getFilterCriteria().equals("dateOfConsultation")) {
            String dateInString = filterDTO.getFilterText();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateInString);
            searchDate = DateUtils.addDaysToDate(date, 1);

        }
        Date finalSearchDate = searchDate;
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            criteriaQuery.distinct(true);

            if (isNotNullOrEmpty(filterDTO.getFilterText())) {
                Predicate predicateForData = null;

                predicateForData = criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(filterDTO.getFilterCriteria()), filterDTO.getFilterCriteria().equals("dateOfConsultation") ? finalSearchDate : filterDTO.getFilterText()),
                        criteriaBuilder.equal(root.get("client").get("codPatient"), clientId));

                return criteriaBuilder.and(predicateForData);
            } else {
                throw new RuntimeException("");
            }
        };
    }
}
