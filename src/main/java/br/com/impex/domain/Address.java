package br.com.impex.domain;

import lombok.Data;

@Data
public class Address {

    private Integer code;
    private Integer owner;
    private String addressName;
    private String province;
}
