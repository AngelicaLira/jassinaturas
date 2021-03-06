package br.com.moip.jassinaturas.clients;

import br.com.moip.jassinaturas.clients.attributes.Coupon;
import br.com.moip.jassinaturas.communicators.CouponCommunicator;

//TODO: testes desta classe
public class CouponClient {

    private final CouponCommunicator couponCommunicator;

    public CouponClient(final CouponCommunicator couponCommunicator) {
        this.couponCommunicator = couponCommunicator;
    }

    public Coupon show(final String code) {
        return couponCommunicator.show(code);
    }

    public Coupon create(final Coupon toBeCreated) {
        return couponCommunicator.create(toBeCreated);
    }

    public Coupon activate(final String code) {
        return couponCommunicator.activate(code);
    }

    public Coupon inactivate(final String code) {
        return couponCommunicator.inactivate(code);
    }
}
