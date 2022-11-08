package org.sc.backend.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

import org.sc.backend.domain.Positions;
import org.sc.backend.domain.ScUser;
import org.sc.backend.domain.enumeration.AssetType;
import org.sc.backend.repository.PositionsRepository;
import org.sc.backend.security.jwt.JWTFilter;
import org.sc.backend.security.jwt.TokenProvider;
import org.sc.backend.service.PositionsQueryService;
import org.sc.backend.service.PositionsService;
import org.sc.backend.service.ScUserService;
import org.sc.backend.service.criteria.PositionsCriteria;
import org.sc.backend.service.impl.PositionsServiceImpl;
import org.sc.backend.service.impl.ScUserServiceImpl;
import org.sc.backend.web.rest.admin.PositionsResource;
import org.sc.backend.web.rest.dto.UserAuthResponse;
import org.sc.backend.web.rest.dto.UserPortfolio;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.sc.backend.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(PositionsResource.class);
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final ScUserService scUserService;

    private final PositionsService positionsService;

    private final PositionsRepository positionsRepository;

    private final PositionsQueryService positionsQueryService;

    public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, ScUserServiceImpl scUserService, PositionsServiceImpl positionsService, PositionsRepository positionsRepository, PositionsQueryService positionsQueryService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.scUserService = scUserService;
        this.positionsService = positionsService;
        this.positionsRepository = positionsRepository;
        this.positionsQueryService = positionsQueryService;
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
        scUser.setPasswordHash(new BCryptPasswordEncoder(14).encode(scUser.getPasswordHash()));

        if (scUserService.findOne(scUser.getScUserId()).isPresent()) {
            throw new BadRequestAlertException("User with id already exists.", "userId", "id exists");
        }

        ScUser newUser;
        try {
            newUser = scUserService.save(scUser);
        } catch (Exception e) {
            throw new BadRequestAlertException("Invalid parameters.", "userId", "id exists");
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/portfolio")
    public ResponseEntity<UserPortfolio> getPortfolio(PositionsCriteria criteria) {
        if (criteria.getScUserId() == null || (
            criteria.getPositionId() != null || criteria.getAssetCode() != null || criteria.getAssetType() != null || criteria.getBuyPrice() != null || criteria.getQuantity() != null))
            throw new BadRequestAlertException("Invalid parameters", "Positions", "invalidparams");

        log.debug("REST request to get Positions by criteria: {}", criteria);
        List<Positions> positions = positionsQueryService.findByCriteria(criteria);

        UserPortfolio response = new UserPortfolio();

        for (Positions p : positions) {
            if (p.getAssetType() == AssetType.STOCK) {
                //fetch stock details through asset code and append to List<UserPosititions> in response.
            }
            else if (p.getAssetType() == AssetType.BOND) {
                //fetch bond details through asset code and append to List<UserPosititions> in response.
            }
            else {
                //fetch mf details through asset code and append to List<UserPosititions> in response.
            }
        }
        return ResponseEntity.ok().body(response);
    }
}
