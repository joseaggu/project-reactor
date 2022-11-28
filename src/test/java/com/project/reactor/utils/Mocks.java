package com.project.reactor.utils;

import java.util.List;

import com.project.reactor.domain.Child;
import com.project.reactor.domain.Parent;

public class Mocks {

  public static List<Parent> buildParent() {
    return List.of(Parent.builder()
            .name("Alex")
            .namesChildren(List.of(Child.builder()
                    .name("Peter")
                    .build(),
                Child.builder()
                    .name("Maria")
                    .build()))
            .build(),
        Parent.builder()
            .name("Carlos")
            .namesChildren(List.of(Child.builder()
                    .name("Joaquin")
                    .build(),
                Child.builder()
                    .name("Roberto")
                    .build()))
            .build());
  }

  public static List<String> buildColors() {
    return List.of("red", "blue", "black");
  }

}
