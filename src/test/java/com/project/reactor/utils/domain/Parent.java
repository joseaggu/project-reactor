package com.project.reactor.utils.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Parent {

  String name;

  List<Child> namesChildren;
}
