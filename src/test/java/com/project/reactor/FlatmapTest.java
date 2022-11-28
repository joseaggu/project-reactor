package com.project.reactor;

import com.project.reactor.domain.Child;
import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FlatmapTest {

  @Test
  void flatmapFlux() {
    //given
    var data = Mocks.buildParent();

    //when
    var actual = Flux.fromIterable(data)
        .flatMap(t -> Flux.fromIterable(t.getNamesChildren()))
        .map(Child::getName)
        .map(String::toUpperCase)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("PETER", "MARIA", "JOAQUIN", "ROBERTO")
        .verifyComplete();
  }

}

