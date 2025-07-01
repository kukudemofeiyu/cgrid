package com.things.cgomp.devicescale.message.req;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class Rate {
    private Long price;
    private Long useTotal;
    private Long lossTotal;
    private Long totalPrice;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getUseTotal() {
        return useTotal;
    }

    public void setUseTotal(Long useTotal) {
        this.useTotal = useTotal;
    }

    public Long getLossTotal() {
        return lossTotal;
    }

    public void setLossTotal(Long lossTotal) {
        this.lossTotal = lossTotal;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
