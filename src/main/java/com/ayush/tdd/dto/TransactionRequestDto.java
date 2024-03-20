package com.ayush.tdd.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDto {
    private Integer senderId;
    private Integer receiverId;
    private BigDecimal amount;
}
