package com.airgear.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class ComplaintCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name length must be between 3 and 100 characters")
    private String name;

    @OneToMany(mappedBy = "complaintCategory")
    private List<Complaint> complaints;
}
