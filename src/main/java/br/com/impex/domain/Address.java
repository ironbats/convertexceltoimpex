package br.com.impex.domain;

import lombok.Data;

@Data
public class Address {

    private Integer code;
    private Integer owner;
    private String streetname;
    private String streetnumber;
    private String town;
    private String postalcode;
    private String company;
    private boolean active;
    private boolean contactAddress;
    private  boolean shippingAddress;
    private boolean unloadingAddress;
    private boolean billingAddress;
}
