package mbn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id_registration", unique = true, nullable = false, precision = 20, scale = 0)
    private Long idRegistration;

    @Column(name="date_of_consultation")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfConsultation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "registrations_files_junction", joinColumns = @JoinColumn(name = "id_registration"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileRequest> fileSet = new HashSet<>();

    @Column(name = "id_recommended_doctor")
    private Integer recommendedDoctor;

    @Column(name = "id_consulted_doctor")
    private Integer consultedDoctor;
    @Column(name="diagnostic")
    private String diagnostic;
    @Column(name="investigation")
    private String investigation;
    @Column(name="treatment")
    private String treatment;
    @Column(name="recommendation")
    private String recommendation;

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

    public Integer getRecommendedDoctor() {
        return recommendedDoctor;
    }

    public void setRecommendedDoctor(Integer recommendedDoctor) {
        this.recommendedDoctor = recommendedDoctor;
    }

    public Integer getConsultedDoctor() {
        return consultedDoctor;
    }

    public void setConsultedDoctor(Integer consultedDoctor) {
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
}
