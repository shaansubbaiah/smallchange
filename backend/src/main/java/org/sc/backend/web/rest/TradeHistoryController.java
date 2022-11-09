package org.sc.backend.web.rest;

import org.sc.backend.config.CommonUtils;
import org.sc.backend.domain.TradeHistory;
import org.sc.backend.service.TradeHistoryQueryService;
import org.sc.backend.service.criteria.TradeHistoryCriteria;
import org.sc.backend.web.rest.dto.UserPortfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.StringFilter;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TradeHistoryController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private TradeHistoryQueryService tradeHistoryQueryService;

    public TradeHistoryController(TradeHistoryQueryService tradeHistoryQueryService) {
        this.tradeHistoryQueryService = tradeHistoryQueryService;
    }

    @GetMapping("/trade-history")
    public ResponseEntity<List<TradeHistory>> getTradeHistory(@RequestHeader(name="Authorization") String token) {
        String userId = CommonUtils.getUserIdFromAuthorizationToken(token);
        TradeHistoryCriteria criteria = new TradeHistoryCriteria();
        criteria.setScUserId((StringFilter) new StringFilter().setEquals(userId));

        return new ResponseEntity<>(tradeHistoryQueryService.findByCriteria(criteria), HttpStatus.OK);

    }
}
