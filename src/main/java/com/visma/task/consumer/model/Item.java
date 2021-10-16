package com.visma.task.consumer.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String uuidTps;
    @NotNull
    private String content;
    private String statusType;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
