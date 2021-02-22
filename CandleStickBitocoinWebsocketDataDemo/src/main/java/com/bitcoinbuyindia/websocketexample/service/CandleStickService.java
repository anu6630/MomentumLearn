package com.bitcoinbuyindia.websocketexample.service;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.bitcoinbuyindia.websocketexample.entities.CandleStickData;
import com.bitcoinbuyindia.websocketexample.repositories.CandleStickDataReposiory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Data
@Slf4j
@Service
public class CandleStickService {
	private String apiKey = "your api key";
	private String apiSecret = "your api secret";
	private String coinPairName = "btcusdt";
	@Autowired
	private CandleStickDataReposiory candleStickDataReposiory;
	public void subscribeCandleStickData() {
		BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance(apiKey,apiSecret).newWebSocketClient();
		client.onCandlestickEvent(coinPairName, CandlestickInterval.FIVE_MINUTES, response -> {
			// Save only if candle stick bar is final
			if(response.getBarFinal()) {
				CandleStickData candleStickEvent = new CandleStickData();
				candleStickEvent.setCoinPairName(coinPairName);
				candleStickEvent.setEventType(response.getEventType());
				candleStickEvent.setEventTime(response.getEventTime());
				candleStickEvent.setSymbol(response.getSymbol());
				candleStickEvent.setOpenTime(response.getOpenTime());
				candleStickEvent.setOpen(response.getOpen());
				candleStickEvent.setClose(response.getClose());
				candleStickEvent.setHigh(response.getHigh());
				candleStickEvent.setLow(response.getLow());
				candleStickEvent.setVolume(response.getVolume());
				candleStickEvent.setCloseTime(response.getCloseTime());
				candleStickEvent.setIntervalId(response.getIntervalId());
				candleStickEvent.setFirstTradeId(response.getFirstTradeId());
				candleStickEvent.setLastTradeId(response.getLastTradeId());
				candleStickEvent.setQuoteAssetVolume(response.getQuoteAssetVolume());
				candleStickEvent.setNumberOfTrades(response.getNumberOfTrades());
				candleStickEvent.setTakerBuyBaseAssetVolume(response.getTakerBuyBaseAssetVolume());
				candleStickEvent.setTakerBuyQuoteAssetVolume(response.getTakerBuyQuoteAssetVolume());
				candleStickEvent.setIsBarFinal(response.getBarFinal());
				candleStickDataReposiory.save(candleStickEvent);
			} else {
				log.info("kline is not final open_time={}",response.getOpenTime());
			}
		});
		
	}

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("starting service");
		this.subscribeCandleStickData(); //Start reading Binance data whenever the application loads
	}
}