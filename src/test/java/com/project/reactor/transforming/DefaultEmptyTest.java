package com.project.reactor.transforming;

import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * The difference between DefaultEmpty and SwitchIfEmpty is the first one used with object and the last one it's used with publisher
 */
class DefaultEmptyTest {

  @Test
  void defaultEmpty() {
    //when
    final var build = Parent.builder().name("default").build();
    final var actual = Flux.empty().defaultIfEmpty(build).log();

    //then
    StepVerifier.create(actual)
        .expectNext(build)
        .verifyComplete();
  }
}

