package com.bitcoinbuyindia.websocketexample.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bitcoinbuyindia.websocketexample.entities.CandleStickData;
@Repository
public interface CandleStickDataReposiory extends CrudRepository<CandleStickData,Long> {
	
}