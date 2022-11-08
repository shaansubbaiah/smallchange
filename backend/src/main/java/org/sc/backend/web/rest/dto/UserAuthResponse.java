package org.sc.backend.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication.
 */
public class UserAuthResponse {
    private String userId, firstName, lastName, email, role, jwt;
    private Long lastLoginTimestamp;

    private byte[] profileImg;

    public UserAuthResponse(String userId, String name, String email, String role, Long lastLoginTimestamp, String jwt, byte[] profileImg) {
        this.userId = userId;
        int beginIndex = name.indexOf(' ') > 0 ? name.indexOf(' ') : name.length();
        this.firstName = name.substring(0, beginIndex);
        this.lastName = beginIndex < name.length() ? name.substring(beginIndex + 1) : "";
        this.email = email;
        this.role = role;
        this.lastLoginTimestamp = lastLoginTimestamp;
        this.jwt = jwt;
        this.profileImg = profileImg;
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

    public Long getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Long lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }
}
