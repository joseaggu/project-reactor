package com.project.reactor;

import com.project.reactor.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class DefaultEmptyTest {

  @Test
  void defaultEmpty() {
    //when
    final var build = Parent.builder().name("default").build();
    final var actual = Flux.empty().defaultIfEmpty(build);

    //then
    StepVerifier.create(actual)
        .expectNext(build)
        .verifyComplete();
  }
}

