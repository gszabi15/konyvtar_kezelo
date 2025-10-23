package org.gszabi15.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    private UUID bookId;
    private String userEmail;
    private int days;
}
