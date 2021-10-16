package com.visma.task.consumer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Status {

    private String uuid;
    private StatusType statusType;
}
