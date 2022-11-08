package org.sc.backend.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sc.backend.web.rest.admin.StocksResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transact")
public class TransactionController {

    private final Logger log = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController() {}

    @PostMapping("/buy")
    public ResponseEntity<TransactionResponse> buyPosition (@Valid @RequestBody TransactionRequest transactionRequest) {
        long startTS = System.currentTimeMillis();

        log.debug("buy position");
        log.debug(transactionRequest.toString());

        // - add x quantity of index from user's positions

        // - remove x quantity of index value to user's account

        long completeTS = System.currentTimeMillis();
        return new ResponseEntity<>(new TransactionResponse("result", null, "some data", startTS, completeTS), HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<TransactionResponse> sellPosition (@Valid @RequestBody TransactionRequest transactionRequest) {
        long startTS = System.currentTimeMillis();

        log.debug("sell position");
        log.debug(transactionRequest.toString());

        // - remove x quantity of index from user's positions
        // - add x quantity of index value to user's account

        long completeTS = System.currentTimeMillis();
        return new ResponseEntity<>(new TransactionResponse("result", null, "some data", startTS, completeTS), HttpStatus.OK);
    }

    static class TransactionRequest {
        String indexCode, indexType, transactionType, userId;
        Integer quantity;

        public TransactionRequest(){}

        public TransactionRequest(String code, String indexType, String transactionType, String userId, Integer quantity) {
            this.indexCode = code;
            this.indexType = indexType;
            this.transactionType = transactionType;
            this.userId = userId;
            this.quantity = quantity;
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


