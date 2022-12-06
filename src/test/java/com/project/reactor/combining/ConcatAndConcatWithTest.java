package com.project.reactor.combining;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Child;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ConcatAndConcatWithTest {

  @Test
  void concat() {
    //given
    var dataA = Flux.fromIterable(Mocks.buildParent());
    var dataB = Flux.fromIterable(Mocks.buildParent());
    //when
    var actual = Flux.concat(dataA, dataB)
        .concatMap(t -> Flux.fromIterable(t.getNamesChildren()))
        .map(Child::getName)
        .map(String::toUpperCase);

    //then
    StepVerifier.create(actual)
        .expectNext("PETER", "MARIA", "JOAQUIN", "ROBERTO", "PETER", "MARIA", "JOAQUIN", "ROBERTO")
        .verifyComplete();
  }

  @Test
  void concatWith() {
    //given
    var dataA = Flux.fromIterable(Mocks.buildParent());
    var dataB = dataA.concatWith(Flux.fromIterable(Mocks.buildParent()));
    //when
    var actual = dataB
        .concatMap(t -> Flux.fromIterable(t.getNamesChildren()))
        .map(Child::getName)
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("PETER", "MARIA", "JOAQUIN", "ROBERTO", "PETER", "MARIA", "JOAQUIN", "ROBERTO")
        .verifyComplete();
  }

  @Test
  void concatWithMono() {
    //given
    var dataA = Mono.just("A");
    var dataB = dataA.concatWith(Mono.just("B")).log();
    //when
    var actual = dataB
        .map(String::toUpperCase);

    //then
    StepVerifier.create(actual)
        .expectNext("A", "B")
        .verifyComplete();
  }
}
