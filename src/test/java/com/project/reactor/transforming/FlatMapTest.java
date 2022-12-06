package com.project.reactor.transforming;

import java.time.Duration;
import java.util.Random;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Child;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Transforma one source element to a Flux of 1 to N elements Always return Flux operator
 */
class FlatMapTest {

  @Test
  void flatMapFlux() {
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

  /**
   * flatMap work async
   */
  @Test
  void flatMapFluxAsync() {
    //given
    var data = Mocks.buildParent();

    //when
    var actual = Flux.fromIterable(data)
        .flatMap(t -> Flux.fromIterable(t.getNamesChildren())
            .delayElements(Duration.ofMillis(new Random().nextInt(1000))))
        .map(Child::getName)
        .map(String::toUpperCase)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("PETER", "MARIA", "JOAQUIN", "ROBERTO")
        .verifyComplete();
  }

}

