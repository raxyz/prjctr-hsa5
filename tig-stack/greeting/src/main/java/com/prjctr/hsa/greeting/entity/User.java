package com.prjctr.hsa.greeting.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Data
public class User {
  @Id
  private String id;

  private String firstName;
  private String lastName;

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
