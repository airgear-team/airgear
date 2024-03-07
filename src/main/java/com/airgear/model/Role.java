package com.airgear.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
}