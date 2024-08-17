package com.pms.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessegeRequest {
    private Long senderId;
    private String content;
    private Long projectId;
}
