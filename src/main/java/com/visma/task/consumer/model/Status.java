package com.visma.task.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Status {
    private String uuid;
    private StatusType statusType;
}
