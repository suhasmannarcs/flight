
package com.airasia.models;


public class FlightDetail {

    private int total_amt;
    private Onward onward;
    private Return _return;

    public int getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(int total_amt) {
        this.total_amt = total_amt;
    }

    public Onward getOnward() {
        return onward;
    }

    public void setOnward(Onward onward) {
        this.onward = onward;
    }

    public Return getReturn() {
        return _return;
    }

    public void setReturn(Return _return) {
        this._return = _return;
    }

}
