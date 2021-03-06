package ru.akuna;

public enum TopMarkets {
    BTC2LTC("BTC-LTC"),
    BTC2DOGE("BTC-DOGE"),
    BTC2XRP("BTC-XRP"),
    USDT2BTC("USDT-BTC");

    private final String text;

    /**
     * @param text
     */
    TopMarkets(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
