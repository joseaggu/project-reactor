package com.project.reactor.combining;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Child;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ConcatAndConcatWithTest {

  @Test
  void concat() {
    //given
    Flux<Parent> dataA = Flux.fromIterable(Mocks.buildParent());
    Flux<Parent> dataB = Flux.fromIterable(Mocks.buildParent());
    //when
    Flux<String> actual = Flux.concat(dataA, dataB)
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
    Flux<Parent> dataA = Flux.fromIterable(Mocks.buildParent());
    Flux<Parent> dataB = dataA.concatWith(Flux.fromIterable(Mocks.buildParent()));
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
    Mono<String> dataA = Mono.just("A");
    Flux<String> dataB = dataA.concatWith(Mono.just("B")).log();
    //when
    Flux<String> actual = dataB
        .map(String::toUpperCase);

    //then
    StepVerifier.create(actual)
        .expectNext("A", "B")
        .verifyComplete();
  }
}
