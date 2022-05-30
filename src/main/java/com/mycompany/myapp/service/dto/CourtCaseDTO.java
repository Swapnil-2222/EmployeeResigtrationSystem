package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourtCase} entity.
 */
public class CourtCaseDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String contactNo;

    private String emailAddress;

    private String salary;

    private String gstOnSalary;

    private String lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getGstOnSalary() {
        return gstOnSalary;
    }

    public void setGstOnSalary(String gstOnSalary) {
        this.gstOnSalary = gstOnSalary;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCaseDTO)) {
            return false;
        }

        CourtCaseDTO courtCaseDTO = (CourtCaseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courtCaseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseDTO{" +
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
