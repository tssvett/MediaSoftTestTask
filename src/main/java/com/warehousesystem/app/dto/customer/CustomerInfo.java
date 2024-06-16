package com.warehousesystem.app.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CustomerInfo {

    Long id;

    String accountNumber;

    String email;

    String inn;
}
