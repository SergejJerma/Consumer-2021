package com.serjer.task.consumer.model;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public final class ClientRequest {
    @NonNull
    private String content;
}
