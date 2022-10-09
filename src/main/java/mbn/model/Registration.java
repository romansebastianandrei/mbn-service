package mbn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="registrations")
public class Registration {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="IdOrGenerated")
    @Column(name = "registration_id", unique = true, nullable = false, precision = 20, scale = 0)
    private Long idRegistration;

    @Column(name="date_of_consultation")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfConsultation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "registrations_files_junction", joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileRequest> fileSet = new HashSet<>();

    @Column(name = "recommended_doctor")
    private String recommendedDoctor;

    @Column(name = "consulted_doctor")
    private String consultedDoctor;
    @Column(name="diagnostic")
    private String diagnostic;
    @Column(name="investigation")
    private String investigation;
    @Column(name="treatment")
    private String treatment;
    @Column(name="recommendation")
    private String recommendation;

    @Column(name="age_at_consultation")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int ageAtConsultation;

    @ManyToOne
    @JoinColumn(name = "cod_patient")
    @JsonIgnore
    private Client client;


    public Long getIdRegistration() {
        return idRegistration;
    }

    public void setIdRegistration(Long idRegistration) {
        this.idRegistration = idRegistration;
    }

    public String getRecommendedDoctor() {
        return recommendedDoctor;
    }

    public void setRecommendedDoctor(String recommendedDoctor) {
        this.recommendedDoctor = recommendedDoctor;
    }

    public String getConsultedDoctor() {
        return consultedDoctor;
    }

    public void setConsultedDoctor(String consultedDoctor) {
        this.consultedDoctor = consultedDoctor;
    }

    public Date getDateOfConsultation() {
        return dateOfConsultation;
    }

    public void setDateOfConsultation(Date dateOfConsultation) {
        this.dateOfConsultation = dateOfConsultation;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<FileRequest> getFileSet() {
        return fileSet;
    }

    public void setFileSet(Set<FileRequest> fileSet) {
        this.fileSet = fileSet;
    }

    public int getAgeAtConsultation() {
        return ageAtConsultation;
    }

    public void setAgeAtConsultation(int ageAtConsultation) {
        this.ageAtConsultation = ageAtConsultation;
    }
}
