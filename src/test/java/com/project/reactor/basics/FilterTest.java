package com.project.reactor.basics;

import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FilterTest {

  @Test
  void filterFlux() {
    //given
    Flux<String> fluxMethod = Flux.fromIterable(Mocks.buildColors());

    //when
    Flux<String> actual = fluxMethod.filter(it -> it.length() <= 4).log();

    //then
    StepVerifier.create(actual)
        .expectNext("red", "blue")
        .verifyComplete();
  }

}
