package com.serjer.task.consumer.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
public final class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String uuidTps;
    @Getter
    private String content;
    @Setter
    private String statusType;
    private LocalDateTime dateCreated;
    @Setter
    private LocalDateTime dateModified;

    public Item(
            String uuidTps, String content, String statusType, LocalDateTime dateCreated, LocalDateTime dateModified) {
        this.uuidTps = uuidTps;
        this.content = content;
        this.statusType = statusType;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }
}
