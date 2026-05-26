package com.example.umc10th.member.entity;

import com.example.umc10th.member.enums.Gender;
import com.example.umc10th.mission.enums.Address;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Address address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(nullable = false)
    private int point = 0;

    @Column(name = "agree_age", nullable = false)
    private boolean agreeAge;

    @Column(name = "agree_service", nullable = false)
    private boolean agreeService;

    @Column(name = "agree_privacy", nullable = false)
    private boolean agreePrivacy;

    @Column(name = "agree_location", nullable = false)
    private boolean agreeLocation;

    @Column(name = "agree_marketing", nullable = false)
    private boolean agreeMarketing;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_preferred_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name", nullable = false)
    private Set<String> preferredFoods;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected Member() {
    }

    public Member(
            String name,
            String email,
            String password,
            String nickname,
            String phoneNumber,
            Gender gender,
            LocalDate birthDate,
            Address address,
            String detailAddress,
            boolean agreeAge,
            boolean agreeService,
            boolean agreePrivacy,
            boolean agreeLocation,
            boolean agreeMarketing,
            Set<String> preferredFoods,
            String role,
            LocalDateTime createdAt
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.detailAddress = detailAddress;
        this.agreeAge = agreeAge;
        this.agreeService = agreeService;
        this.agreePrivacy = agreePrivacy;
        this.agreeLocation = agreeLocation;
        this.agreeMarketing = agreeMarketing;
        this.preferredFoods = preferredFoods;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public int getPoint() {
        return point;
    }

    public boolean isAgreeAge() {
        return agreeAge;
    }

    public boolean isAgreeService() {
        return agreeService;
    }

    public boolean isAgreePrivacy() {
        return agreePrivacy;
    }

    public boolean isAgreeLocation() {
        return agreeLocation;
    }

    public boolean isAgreeMarketing() {
        return agreeMarketing;
    }

    public Set<String> getPreferredFoods() {
        return preferredFoods;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
