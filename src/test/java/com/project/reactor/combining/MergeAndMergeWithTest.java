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
    var dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    var dataTwo = Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200));

    //when
    var actual = Flux.merge(dataOne, dataTwo)
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
    var dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    var dataTwo = dataOne.mergeWith(Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200)));

    //when
    var actual = dataTwo
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
    var dataOne = Mono.just("A");
    var dataTwo = dataOne.mergeWith(Mono.just("B"));

    //when
    var actual = dataTwo
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("A", "B")
        .verifyComplete();
  }

  @Test
  void mergeSequential() {
    //given
    var dataOne = Flux.fromIterable(Mocks.buildParent()).delayElements(Duration.ofMillis(100));
    var dataTwo = Flux.fromIterable(List.of(Parent.builder().name("Matias").build(),
        Parent.builder().name("Rocio").build())).delayElements(Duration.ofMillis(200));

    //when
    var actual = Flux.mergeSequential(dataOne, dataTwo)
        .map(Parent::getName)
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX", "CARLOS", "MATIAS", "ROCIO")
        .verifyComplete();
  }

}
