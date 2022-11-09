package org.sc.backend.service;

import org.sc.backend.domain.Bonds;
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.domain.Stocks;
import org.sc.backend.service.impl.BondsServiceImpl;
import org.sc.backend.service.impl.MutualFundsServiceImpl;
import org.sc.backend.service.impl.StocksServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class FluctuationsInducer {
    private final StocksServiceImpl stocksService;
    private final BondsServiceImpl bondsService;
    private final MutualFundsServiceImpl mfService;

    private final Logger log = LoggerFactory.getLogger(FluctuationsInducer.class);

    float volatility = 0.02f + new SecureRandom().nextFloat()*(0.1f - 0.02f); //random number between two ranges: min + rand()*(max - min)

    public FluctuationsInducer(StocksServiceImpl stocksService, BondsServiceImpl bondsService, MutualFundsServiceImpl mfService) {
        this.stocksService = stocksService;
        this.bondsService = bondsService;
        this.mfService = mfService;
    }

    /**
     * Helper method to calculate randomized stock price.
     *
     * @param volatility factor by which the volatility of the stock is decided
     * @param initPrice inital Price of stock
     * @return fluctuated Price of stock
     */
    private float getNextStockPrice(float volatility, float initPrice) {
        float rnd = new SecureRandom().nextFloat();
        float changePercent = 2 * volatility * rnd;
        if (changePercent > volatility)
            changePercent -= (2 * volatility);

        float changeAmount = initPrice * changePercent;

        return initPrice + changeAmount;
    }

    public void induceStockFluctuations() {
        log.info("Inducing stock fluctuations...");
        List<Stocks> stocks = stocksService.findAll();
        for (Stocks stock : stocks) {
            stock.setCurrentPrice(getNextStockPrice(volatility, stock.getCurrentPrice()));
        }
        stocksService.saveAll(stocks);
    }

    public void induceBondFluctuations() {
        log.info("Inducing bond fluctuations...");
        List<Bonds> bonds = bondsService.findAll();
        for (Bonds bond : bonds) {
            bond.setCurrentPrice(getNextStockPrice(volatility, bond.getCurrentPrice()));
        }
        bondsService.saveAll(bonds);
    }

    public void induceMfFluctuations() {
        log.info("Inducing MF fluctuations...");
        List<MutualFunds> mfs = mfService.findAll();
        for (MutualFunds mf : mfs) {
            mf.setCurrentPrice(getNextStockPrice(volatility, mf.getCurrentPrice()));
        }
        mfService.saveAll(mfs);
    }
}
