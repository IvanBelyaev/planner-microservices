package com.example.micro.planner.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "stat", schema = "todolist", catalog = "todolist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "completed_total", updatable = false)
    private Long completedTotal;

    @Column(name = "uncompleted_total", updatable = false)
    private Long uncompletedTotal;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @Column(name="user_id")
    private Long userId;
}
