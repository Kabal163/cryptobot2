package ru.akuna.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akuna.entities.BittrexMarket;

import java.util.List;

public interface MarketRepository extends JpaRepository<BittrexMarket, Long>
{
    List<BittrexMarket> findByMarketNameContains(String marketName);
}
