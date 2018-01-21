package ru.akuna.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<BittrexMarket, Long>
{
    List<BittrexMarket> findByMarketNameContains(String marketName);
}
