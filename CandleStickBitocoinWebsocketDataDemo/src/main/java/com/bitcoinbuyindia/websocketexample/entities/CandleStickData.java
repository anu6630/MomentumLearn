package com.bitcoinbuyindia.websocketexample.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
@Entity
@Data
public class CandleStickData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String coinPairName;
    
    /* Properties from Binance pojo */
    private String eventType;
    private long eventTime;
    private String symbol;
    private Long openTime;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private Long closeTime;
    private String intervalId;
    private Long firstTradeId;
    private Long lastTradeId;
    private String quoteAssetVolume;
    private Long numberOfTrades;
    private String takerBuyBaseAssetVolume;
    private String takerBuyQuoteAssetVolume;
    private Boolean isBarFinal;
    
}