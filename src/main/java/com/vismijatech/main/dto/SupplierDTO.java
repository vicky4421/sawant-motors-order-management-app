package com.vismijatech.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private String name;
    private String whatsappNumber;
    private String alternateNumber;
}
