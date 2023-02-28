package com.project.reactor.transforming;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Child;
import com.project.reactor.utils.domain.Parent;
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
    List<Parent> data = Mocks.buildParent();

    //when
    Flux<String> actual = Flux.fromIterable(data)
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
    List<Parent> data = Mocks.buildParent();

    //when
    Flux<String> actual = Flux.fromIterable(data)
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

