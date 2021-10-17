package com.visma.task.consumer.model;

import lombok.*;

@Data
@NoArgsConstructor
public class ClientRequest {
    @NonNull
    private String content;
}
