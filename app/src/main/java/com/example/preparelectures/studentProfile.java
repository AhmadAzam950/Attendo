package com.example.preparelectures;

public class studentProfile {
    private String firstName;
    private String lastName;
    private String rollNo;
    private boolean check=false;
    public studentProfile()
    {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public studentProfile(String firstName, String lastName, String rollNo, boolean check) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.rollNo=rollNo;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
