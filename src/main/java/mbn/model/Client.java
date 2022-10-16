package mbn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="IdOrGenerated")
    @Column(name = "cod_patient", unique = true, nullable = false, precision = 20, scale = 0)
    private long codPatient;
    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    private String cnp;
    @Column(name = "date_of_birth")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Registration> registrations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clients_files_junction", joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileRequest> fileSet = new HashSet<>();

    @Column(name="gdpr_completed")
    private boolean gdprCompleted;

    public Client() {}

    public long getCodPatient() {
        return codPatient;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCodPatient(long codPatient) {
        this.codPatient = codPatient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    public Set<FileRequest> getFileSet() {
        return fileSet;
    }

    public void setFileSet(Set<FileRequest> fileSet) {
        this.fileSet = fileSet;
    }

    public boolean isGdprCompleted() {
        return gdprCompleted;
    }

    public void setGdprCompleted(boolean gdprCompleted) {
        this.gdprCompleted = gdprCompleted;
    }
}
