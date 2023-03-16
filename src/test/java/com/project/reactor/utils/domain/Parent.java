package com.project.reactor.utils.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Parent {

  String name;

  String LastName;

  List<Child> namesChildren;
}
