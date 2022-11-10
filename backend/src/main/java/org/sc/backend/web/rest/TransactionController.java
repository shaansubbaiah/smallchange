package org.sc.backend.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sc.backend.domain.*;
import org.sc.backend.domain.enumeration.AssetType;
import org.sc.backend.service.*;
import org.sc.backend.service.criteria.BondsCriteria;
import org.sc.backend.service.criteria.MutualFundsCriteria;
import org.sc.backend.service.criteria.PositionsCriteria;
import org.sc.backend.service.criteria.StocksCriteria;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.StringFilter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.sc.backend.config.CommonUtils.getUserIdFromAuthorizationToken;

@RestController
@RequestMapping("/api/transact")
public class TransactionController {

    private final Logger log = LoggerFactory.getLogger(TransactionController.class);
    private final PositionsQueryService positionsQueryService;
    private final PositionsService positionsService;
    private final StocksQueryService stocksQueryService;
    private final MutualFundsQueryService mutualFundsQueryService;
    private final BondsQueryService bondsQueryService;
    private final StocksService stocksService;
    private final MutualFundsService mutualFundsService;
    private final BondsService bondsService;
    private final ScUserService scUserService;
    private final TradeHistoryService tradeHistoryService;

    private final ScAccountService accountService;


    public TransactionController(PositionsQueryService positionsQueryService, PositionsService positionsService, StocksQueryService stocksQueryService, MutualFundsQueryService mutualFundsQueryService, BondsQueryService bondsQueryService, StocksService stocksService, MutualFundsService mutualFundsService, BondsService bondsService, ScUserService scUserService, TradeHistoryService tradeHistoryService, ScAccountService accountService) {
        this.positionsQueryService = positionsQueryService;
        this.positionsService = positionsService;
        this.stocksQueryService = stocksQueryService;
        this.mutualFundsQueryService = mutualFundsQueryService;
        this.bondsQueryService = bondsQueryService;
        this.stocksService = stocksService;
        this.mutualFundsService = mutualFundsService;
        this.bondsService = bondsService;
        this.scUserService = scUserService;
        this.tradeHistoryService = tradeHistoryService;
        this.accountService = accountService;
    }

    public Float getAssetPrice(String indexCode, String indexType) {
        if (indexType.contentEquals("STOCK")) {
            StocksCriteria stocksCriteria = new StocksCriteria();
            stocksCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<Stocks> stocks = stocksQueryService.findByCriteria(stocksCriteria);
            if (stocks.size() > 0) {
                return stocks.get(0).getCurrentPrice();
            }
        } else if (indexType.contentEquals("MUTUALFUND")) {
            MutualFundsCriteria mutualFundsCriteria = new MutualFundsCriteria();
            mutualFundsCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<MutualFunds> mfs = mutualFundsQueryService.findByCriteria(mutualFundsCriteria);
            if (mfs.size() > 0) {
                return mfs.get(0).getCurrentPrice();
            }
        } else {
            BondsCriteria bondsCriteria = new BondsCriteria();
            bondsCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<Bonds> bonds = bondsQueryService.findByCriteria(bondsCriteria);
            if (bonds.size() > 0) {
                return bonds.get(0).getCurrentPrice();
            }
        }
        return -1.0f;
    }

    public Boolean updateMarketplaceAssetQuantity(String indexCode, String indexType, Integer changeInQuantity) {
        int newQuantity;
        if (indexType.contentEquals("STOCK")) {
            StocksCriteria stocksCriteria = new StocksCriteria();
            stocksCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<Stocks> stocks = stocksQueryService.findByCriteria(stocksCriteria);
            if (stocks.size() > 0) {
                if (stocks.get(0).getQuantity() + changeInQuantity >= 0) {
                    newQuantity = stocks.get(0).getQuantity() + changeInQuantity;
                    Stocks updatedStock = stocks.get(0);
                    updatedStock.setQuantity(newQuantity);
                    stocksService.update(updatedStock);
                    return true;
                }
            }
        } else if (indexType.contentEquals("MUTUALFUND")) {
            MutualFundsCriteria mutualFundsCriteria = new MutualFundsCriteria();
            mutualFundsCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<MutualFunds> mfs = mutualFundsQueryService.findByCriteria(mutualFundsCriteria);
            if (mfs.size() > 0) {
                if (mfs.get(0).getQuantity() + changeInQuantity >= 0) {
                    newQuantity = mfs.get(0).getQuantity() + changeInQuantity;
                    MutualFunds updatedMf = mfs.get(0);
                    updatedMf.setQuantity(newQuantity);
                    mutualFundsService.update(updatedMf);
                    return true;
                }
            }
        } else {
            BondsCriteria bondsCriteria = new BondsCriteria();
            bondsCriteria.setCode((StringFilter) new StringFilter().setEquals(indexCode));
            List<Bonds> bonds = bondsQueryService.findByCriteria(bondsCriteria);
            if (bonds.size() > 0) {
                if (bonds.get(0).getQuantity() + changeInQuantity >= 0) {
                    newQuantity = bonds.get(0).getQuantity() + changeInQuantity;
                    Bonds updatedBond = bonds.get(0);
                    updatedBond.setQuantity(newQuantity);
                    bondsService.update(updatedBond);
                    return true;
                }
            }
        }
        return false;
    }

    @PostMapping("/buy")
    public ResponseEntity<TransactionResponse> buyPosition(@Valid @RequestBody TransactionRequest transactionRequest, @RequestHeader(name = "Authorization") String token) {
        long startTS = System.currentTimeMillis();

        String userId = getUserIdFromAuthorizationToken(token);

        log.debug("buy position");
        log.debug(transactionRequest.toString());

        // 1) - add x quantity of index from user's positions
        // ---------- check if position exists
        PositionsCriteria positionsCriteria = new PositionsCriteria();
        positionsCriteria.setScUserId((StringFilter) new StringFilter().setEquals(userId));
        positionsCriteria.setAssetCode((StringFilter) new StringFilter().setEquals(transactionRequest.indexCode));
        List<Positions> positions = positionsQueryService.findByCriteria(positionsCriteria);
        // ---------- Get current price of asset
        Float currentPrice = getAssetPrice(transactionRequest.indexCode, transactionRequest.indexType);
        if (currentPrice < 0) {
            throw new BadRequestAlertException("Couldn't get asset price!", "UserController", "failed_getting_price");
        }
        // ---------- if position exists, increase by quantity
        if (positions.size() > 0) {
            Integer currentQuantity = positions.get(0).getQuantity();
            Positions updatedPosition = positions.get(0);
            updatedPosition.setQuantity(currentQuantity + transactionRequest.quantity);
            updatedPosition.setBuyPrice(currentPrice);
            positionsService.update(updatedPosition);
        }
        // ---------- else, add to positions
        else {
            ScUser scUser;
            if (scUserService.findOne(userId).isPresent()) {
                scUser = scUserService.findOne(userId).get();
                Positions newPosition = new Positions(
                    userId,
                    transactionRequest.indexCode,
                    AssetType.valueOf(transactionRequest.indexType),
                    currentPrice,
                    transactionRequest.quantity,
                    scUser
                );

                positionsService.save(newPosition);
            } else {
                throw new BadRequestAlertException("Invalid user ID!", "UserController", "invalid_id");
            }
        }

        // 2) - remove x quantity of index value to user's account
        log.debug("Removing " + currentPrice * transactionRequest.quantity + " from user's account.");
        Optional<ScAccount> x = accountService.findOne(transactionRequest.selectedAccount);
        if(x.isPresent()){
            ScAccount scAccount = x.get();
            Float old_balance = scAccount.getAccBalance();
            scAccount.setAccBalance(old_balance - (transactionRequest.quantity * positions.get(0).getBuyPrice()));
            accountService.update(scAccount);
        }
        else {
            throw new BadRequestAlertException("Failed to find bank account!", "UserController", "fail_find_bnk_acc");
        }

        // 3) - Remove x quantity of index from marketplace
        if (!updateMarketplaceAssetQuantity(transactionRequest.indexCode, transactionRequest.indexType, (transactionRequest.quantity * -1))) {
            throw new BadRequestAlertException("Failed to update marketplace quantity!", "UserController", "fail_upd_mkt_qty");
        }

        // 4) - Write details to trade history
        ScUser scUser;
        if (scUserService.findOne(userId).isPresent()) {
            scUser = scUserService.findOne(userId).get();
            tradeHistoryService.save(new TradeHistory(userId, transactionRequest.indexCode, AssetType.valueOf(transactionRequest.indexType), currentPrice, "BUY", transactionRequest.quantity, LocalDate.now(), scUser));
        }
        else {
            throw new BadRequestAlertException("Invalid user ID!", "UserController", "invalid_id");
        }

        long completeTS = System.currentTimeMillis();
        return new ResponseEntity<>(new TransactionResponse("result", null, "some data", startTS, completeTS), HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<TransactionResponse> sellPosition(@Valid @RequestBody TransactionRequest transactionRequest, @RequestHeader(name = "Authorization") String token) {
        long startTS = System.currentTimeMillis();
        String userId = getUserIdFromAuthorizationToken(token);

        log.debug("sell position");
        log.debug(transactionRequest.toString());

        // 1) - remove x quantity of index from user's positions
        // ---------- check if position exists
        PositionsCriteria positionsCriteria = new PositionsCriteria();
        positionsCriteria.setScUserId((StringFilter) new StringFilter().setEquals(userId));
        positionsCriteria.setAssetCode((StringFilter) new StringFilter().setEquals(transactionRequest.indexCode));
        List<Positions> positions = positionsQueryService.findByCriteria(positionsCriteria);
        // ---------- Get current price of asset
        Float currentPrice = getAssetPrice(transactionRequest.indexCode, transactionRequest.indexType);
        // ---------- if position exists, decrease by quantity
        if (positions.size() > 0) {
            Integer currentQuantity = positions.get(0).getQuantity();
            if (currentQuantity < transactionRequest.quantity) {
                throw new BadRequestAlertException("Quantity is too low!", "UserController", "invalid_quantity");
            }
            Positions position = positions.get(0);
            // --------- if new quantity > 0, update position
            if (currentQuantity - transactionRequest.quantity > 0) {
                position.setQuantity(currentQuantity - transactionRequest.quantity);
                position.setBuyPrice(currentPrice);
                positionsService.update(position);
            }
            // --------- else, delete position
            else {
                positionsService.delete(position.getPositionId());
            }
        }
        // ---------- else throw error
        else {
            throw new BadRequestAlertException("Invalid position!", "UserController", "invalid_position");
        }

        // 2) - add x quantity of index value to user's account
        log.debug("Adding " + currentPrice * transactionRequest.quantity + " to user's account.");
        Optional<ScAccount> x = accountService.findOne(transactionRequest.selectedAccount);
        if(x.isPresent()){
            ScAccount scAccount = x.get();
            Float old_balance = scAccount.getAccBalance();
            scAccount.setAccBalance(old_balance + (transactionRequest.quantity * positions.get(0).getBuyPrice()));
            accountService.update(scAccount);
        }
        else {
            throw new BadRequestAlertException("Failed to find bank account!", "UserController", "fail_find_bnk_acc");
        }

        // 3) - Add x quantity of index from marketplace
        if (!updateMarketplaceAssetQuantity(transactionRequest.indexCode, transactionRequest.indexType, transactionRequest.quantity)) {
            throw new BadRequestAlertException("Failed to update marketplace quantity!", "UserController", "fail_upd_mkt_qty");
        }

        // 4) - Write details to trade history
        ScUser scUser;
        if (scUserService.findOne(userId).isPresent()) {
            scUser = scUserService.findOne(userId).get();
            tradeHistoryService.save(new TradeHistory(userId, transactionRequest.indexCode, AssetType.valueOf(transactionRequest.indexType), currentPrice, "SELL", transactionRequest.quantity, LocalDate.now(), scUser));
        }
        else {
            throw new BadRequestAlertException("Invalid user ID!", "UserController", "invalid_id");
        }

        long completeTS = System.currentTimeMillis();
        return new ResponseEntity<>(new TransactionResponse("result", null, "some data", startTS, completeTS), HttpStatus.OK);
    }

    static class TransactionRequest {
        String indexCode, indexType, transactionType, userId;
        Integer quantity;
        Long selectedAccount;

        public TransactionRequest() {
        }

        public TransactionRequest(String code, String indexType, String transactionType, String userId, Integer quantity, Long selectedAccount) {
            this.indexCode = code;
            this.indexType = indexType;
            this.transactionType = transactionType;
            this.userId = userId;
            this.quantity = quantity;
            this.selectedAccount = selectedAccount;
        }

        @JsonProperty("index_code")
        public String getIndexCode() {
            return indexCode;
        }

        public void setIndexCode(String code) {
            this.indexCode = code;
        }

        @JsonProperty("index_type")
        public String getIndexType() {
            return indexType;
        }

        public void setIndexType(String indexType) {
            this.indexType = indexType;
        }

        @JsonProperty("transaction_type")
        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        @JsonProperty("user_id")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "TransactionRequest{" +
                "indexCode='" + indexCode + '\'' +
                ", indexType='" + indexType + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", userId='" + userId + '\'' +
                ", quantity=" + quantity +
                '}';
        }

        public Long getSelectedAccount() {
            return selectedAccount;
        }

        public void setSelectedAccount(Long selectedAccount) {
            this.selectedAccount = selectedAccount;
        }
    }

    static class TransactionResponse {
        String result, error, data;
        long startTimestamp, completeTimestamp;

        public TransactionResponse(String result, String error, String data, long startTimestamp, long completeTimestamp) {
            this.result = result;
            this.error = error;
            this.data = data;
            this.startTimestamp = startTimestamp;
            this.completeTimestamp = completeTimestamp;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public long getStartTimestamp() {
            return startTimestamp;
        }

        public void setStartTimestamp(long startTimestamp) {
            this.startTimestamp = startTimestamp;
        }

        public long getCompleteTimestamp() {
            return completeTimestamp;
        }

        public void setCompleteTimestamp(long completeTimestamp) {
            this.completeTimestamp = completeTimestamp;
        }
    }
}


