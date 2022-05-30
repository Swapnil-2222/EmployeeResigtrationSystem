package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CourtCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "salary")
    private String salary;

    @Column(name = "gst_on_salary")
    private String gstOnSalary;

    @Column(name = "last_modified")
    private String lastModified;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public CourtCase firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public CourtCase lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public CourtCase address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public CourtCase contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public CourtCase emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSalary() {
        return this.salary;
    }

    public CourtCase salary(String salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getGstOnSalary() {
        return this.gstOnSalary;
    }

    public CourtCase gstOnSalary(String gstOnSalary) {
        this.setGstOnSalary(gstOnSalary);
        return this;
    }

    public void setGstOnSalary(String gstOnSalary) {
        this.gstOnSalary = gstOnSalary;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public CourtCase lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCase)) {
            return false;
        }
        return id != null && id.equals(((CourtCase) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCase{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", salary='" + getSalary() + "'" +
            ", gstOnSalary='" + getGstOnSalary() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
