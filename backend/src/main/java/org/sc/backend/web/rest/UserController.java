package org.sc.backend.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

import org.sc.backend.domain.ScUser;
import org.sc.backend.security.jwt.JWTFilter;
import org.sc.backend.security.jwt.TokenProvider;
import org.sc.backend.service.impl.ScUserServiceImpl;
import org.sc.backend.web.rest.vm.LoginVM;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final ScUserServiceImpl scUserService;

    public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, ScUserServiceImpl scUserService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.scUserService = scUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthResponse> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        //authenticate user
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //auth success, generate jwt
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());

        //get user details
        ScUser user = scUserService.findOne(loginVM.getUsername()).isPresent() ? scUserService.findOne(loginVM.getUsername()).get() : null;
        if (user == null) throw new UsernameNotFoundException("Username not found in database!");

        //set HTTP headers and return
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new UserAuthResponse(user.getScUserId(), user.getName(), user.getEmail(), user.getScUserRole().toString(), System.currentTimeMillis(), jwt, user.getImage()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ScUser> register(@Valid @RequestBody ScUser scUser) {
        //hash passwords
        scUser.setPasswordHash(new BCryptPasswordEncoder(14).encode(scUser.getPasswordHash()));

        //TODO: Implement checks before insertion

        return new ResponseEntity<>(scUserService.save(scUser), HttpStatus.CREATED);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class UserAuthResponse {
        private String userId, firstName, lastName, email, role, jwt;
        private Long lastLoginTimestamp;

        private byte[] profileImg;

        public UserAuthResponse(String userId, String name, String email, String role, Long lastLoginTimestamp, String jwt, byte[] profileImg) {
            this.userId = userId;
            int beginIndex = name.indexOf(' ') > 0 ? name.indexOf(' ') : name.length();
            this.firstName = name.substring(0, beginIndex);
            this.lastName = name.substring(beginIndex + 1);
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
}
