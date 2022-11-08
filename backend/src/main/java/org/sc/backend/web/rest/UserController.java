package org.sc.backend.web.rest;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sc.backend.domain.*;
import org.sc.backend.domain.enumeration.AssetType;
import org.sc.backend.repository.PositionsRepository;
import org.sc.backend.security.jwt.JWTFilter;
import org.sc.backend.security.jwt.TokenProvider;
import org.sc.backend.service.*;
import org.sc.backend.service.criteria.BondsCriteria;
import org.sc.backend.service.criteria.MutualFundsCriteria;
import org.sc.backend.service.criteria.PositionsCriteria;
import org.sc.backend.service.criteria.StocksCriteria;
import org.sc.backend.service.impl.PositionsServiceImpl;
import org.sc.backend.service.impl.ScUserServiceImpl;
import org.sc.backend.web.rest.admin.PositionsResource;
import org.sc.backend.web.rest.dto.UserAuthResponse;
import org.sc.backend.web.rest.dto.UserPortfolio;
import org.sc.backend.web.rest.dto.UserPosition;
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
import tech.jhipster.service.filter.StringFilter;

import java.util.*;

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

    private final PositionsQueryService positionsQueryService;

    private final StocksQueryService stocksQueryService;

    private final BondsQueryService bondsQueryService;

    private final MutualFundsQueryService mfQueryService;

    public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, ScUserServiceImpl scUserService, PositionsServiceImpl positionsService, PositionsRepository positionsRepository, PositionsQueryService positionsQueryService, StocksQueryService stocksQueryService, BondsQueryService bondsQueryService, MutualFundsQueryService mfQueryService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.scUserService = scUserService;
        this.stocksQueryService = stocksQueryService;
        this.bondsQueryService = bondsQueryService;
        this.mfQueryService = mfQueryService;
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
    public ResponseEntity<UserPortfolio> getPortfolio(@RequestHeader (name="Authorization") String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        //String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode payloadJson = mapper.readTree(payload);
            String subject = payloadJson.get("sub").textValue();

            PositionsCriteria criteria = new PositionsCriteria();

            criteria.setScUserId((StringFilter) new StringFilter().setEquals(subject));

            List<Positions> positions = positionsQueryService.findByCriteria(criteria);
            log.debug("POSIIONS:-------------- {}", positions);

            List<UserPosition> stockPositions = new ArrayList<>(), bondPositions = new ArrayList<>(), mfPositions = new ArrayList<>();

            for (Positions p : positions) {
                if (p.getAssetType() == AssetType.STOCK) {
                    //fetch stock details through asset code and append to List<UserPosititions> in response.
                    StocksCriteria stocksCriteria = new StocksCriteria();
                    stocksCriteria.setCode((StringFilter) new StringFilter().setEquals(p.getAssetCode()));

                    List<Stocks> stocks = stocksQueryService.findByCriteria(stocksCriteria);
                    for (Stocks st : stocks) {
                        stockPositions.add(new UserPosition(st.getName(), st.getCode(), AssetType.STOCK.getValue(), p.getQuantity(), p.getBuyPrice(), st.getCurrentPrice()));
                    }
                }
                else if (p.getAssetType() == AssetType.BOND) {
                    //fetch bond details through asset code and append to List<UserPosititions> in response.
                    BondsCriteria bondsCriteria = new BondsCriteria();
                    bondsCriteria.setCode((StringFilter) new StringFilter().setEquals(p.getAssetCode()));

                    List<Bonds> bonds = bondsQueryService.findByCriteria(bondsCriteria);
                    for (Bonds b : bonds) {
                        bondPositions.add(new UserPosition(b.getName(), b.getCode(), AssetType.BOND.getValue(), p.getQuantity(), p.getBuyPrice(), b.getCurrentPrice()));
                    }
                }
                else {
                    //fetch mf details through asset code and append to List<UserPosititions> in response.
                    MutualFundsCriteria mfCriteria = new MutualFundsCriteria();
                    mfCriteria.setCode((StringFilter) new StringFilter().setEquals(p.getAssetCode()));

                    List<MutualFunds> mutualFunds = mfQueryService.findByCriteria(mfCriteria);
                    for (MutualFunds mf : mutualFunds) {
                        mfPositions.add(new UserPosition(mf.getName(), mf.getCode(), AssetType.MUTUALFUND.getValue(), p.getQuantity(), p.getBuyPrice(), mf.getCurrentPrice()));
                    }
                }
            }
            return ResponseEntity.ok().body(new UserPortfolio(stockPositions, bondPositions, mfPositions));

        } catch (JsonProcessingException e) {
            throw new BadRequestAlertException("Invalid user ID!", "UserController", "invalidheader");
        }
    }
}
