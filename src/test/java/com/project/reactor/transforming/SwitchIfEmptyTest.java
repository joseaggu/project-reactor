package com.project.reactor.transforming;

import java.util.List;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SwitchIfEmptyTest {

  @Test
  void switchIfEmpty() {
    //when
    final Flux<String> build = Flux.fromIterable(List.of(Parent.builder().name("default").build())).map(Parent::getName);

    final Flux<String> actual = Flux.fromIterable(Mocks.buildParentEmpty())
        .map(Parent::getName)
        .switchIfEmpty(build).log();

    //then
    StepVerifier.create(actual)
        .expectNext("default")
        .verifyComplete();
  }

  @Test
  void switchIfEmptySortDifferent() {
    //when
    final Flux<Parent> build = Flux.fromIterable(List.of(Parent.builder().name("default").build()));

    final Flux<String> actual = Flux.fromIterable(Mocks.buildParentEmpty())
        .switchIfEmpty(build).log()
        .map(Parent::getName);

    //then
    StepVerifier.create(actual)
        .expectNext("default")
        .verifyComplete();
  }
}

