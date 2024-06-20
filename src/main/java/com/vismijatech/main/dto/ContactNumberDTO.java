package com.vismijatech.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactNumberDTO {
    private String whatsappNumber;
    private String alternateNumber;
}
