package com.microtech.microtech.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("client")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{
}
