package com.visma.task.consumer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String uuidTps;
    private String content;
    private String statusType;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
