package PERTEMUAN_7.TUGAS.src.model;

import java.util.Date;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String membershipType;
    private String interests;
    private boolean activeStatus;
    private Date joinDate;

    // Constructor
    public Member(String name, String email, String phone, String address, 
                 String membershipType, String interests, boolean activeStatus, Date joinDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipType = membershipType;
        this.interests = interests;
        this.activeStatus = activeStatus;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
    public boolean isActiveStatus() { return activeStatus; }
    public void setActiveStatus(boolean activeStatus) { this.activeStatus = activeStatus; }
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
}