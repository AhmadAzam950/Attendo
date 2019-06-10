package com.example.preparelectures;

public class teacherProfile {
    private String firstName;
    private String lastName;
    private String qualification;
    public teacherProfile()
    {

    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public teacherProfile(String firstName, String lastName, String qualification) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.qualification=qualification;
    }
}
