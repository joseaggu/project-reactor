package com.project.reactor.combining;

import java.time.Duration;
import java.util.List;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * <a href="https://stackoverflow.com/questions/48478420/spring-reactor-merge-vs-concat">Differences between merge and Concat</a>
 */
class MergeAndMergeWithTest {

  @Test
  void merge() {
    //given
    Flux<Parent> dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    Flux<Parent> dataTwo = Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200));

    //when
    Flux<String> actual = Flux.merge(dataOne, dataTwo)
        .map(Parent::getName)
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX", "MATIAS", "CARLOS", "ROCIO")
        .verifyComplete();
  }

  @Test
  void mergeWith() {
    //given
    Flux<Parent> dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    Flux<Parent> dataTwo = dataOne.mergeWith(Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200)));

    //when
    Flux<String> actual = dataTwo
        .map(Parent::getName)
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX", "MATIAS", "CARLOS", "ROCIO")
        .verifyComplete();
  }

  @Test
  void mergeWithMono() {
    //given
    Mono<String> dataOne = Mono.just("A");
    Flux<String> dataTwo = dataOne.mergeWith(Mono.just("B"));

    //when
    Flux<String> actual = dataTwo
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("A", "B")
        .verifyComplete();
  }

  @Test
  void mergeSequential() {
    //given
    Flux<Parent> dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    Flux<Parent> dataTwo = Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200));

    //when
    Flux<String> actual = Flux.mergeSequential(dataOne, dataTwo)
        .map(Parent::getName)
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX", "CARLOS", "MATIAS", "ROCIO")
        .verifyComplete();
  }

}
