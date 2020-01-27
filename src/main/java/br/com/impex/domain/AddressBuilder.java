package br.com.impex.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressBuilder {

    private Integer code;
    private Integer owner;
    private String addressName;
    private String province;
}
