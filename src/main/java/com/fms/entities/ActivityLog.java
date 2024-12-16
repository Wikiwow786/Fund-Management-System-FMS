
package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(schema ="fms", name="activity_log")
@Getter
@Setter
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action")
    private String action;

    @Column(name = "date")
    private OffsetDateTime dateTime;

}

