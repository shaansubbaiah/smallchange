package org.sc.backend.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Object to return as body in JWT Authentication.
 */
public class UserAuthResponse {
    private String userId, firstName, lastName, email, role, jwt;
    private byte[] profileImg;

    private List<Long> accounts;

    public UserAuthResponse(String userId, String name, String email, String role, Long lastLoginTimestamp, String jwt, byte[] profileImg, List<Long> accounts) {
        this.userId = userId;
        this.accounts = accounts;
        int beginIndex = name.indexOf(' ') > 0 ? name.indexOf(' ') : name.length();
        this.firstName = name.substring(0, beginIndex);
        this.lastName = beginIndex < name.length() ? name.substring(beginIndex + 1) : "";
        this.email = email;
        this.role = role;
        this.jwt = jwt;
        this.profileImg = profileImg;
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }


    UserAuthResponse(String idToken) {
        this.jwt = idToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("jwt")
    String getJwt() {
        return jwt;
    }

    void setJwt(String jwt) {
        this.jwt = jwt;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }
}
