package mbn.model;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="IdOrGenerated")
    @Column(name = "id_doctor", unique = true, nullable = false, precision = 20, scale = 0)
    private Long idDoctor;

    @Column(name="full_name")
    private String fullName;

//    @OneToOne(mappedBy = "recommendedDoctor", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private Registration recommendedDoctor;
//
//    @OneToOne(mappedBy = "consultedDoctor", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private Registration consultedDoctor;

    public Doctor() {
    }

    public Long getIdDoctor() {
        return idDoctor;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
