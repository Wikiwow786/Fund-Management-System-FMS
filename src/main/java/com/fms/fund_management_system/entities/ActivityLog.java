
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "remarks")
    private String remarks;

}

