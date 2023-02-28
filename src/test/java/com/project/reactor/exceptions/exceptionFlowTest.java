package com.project.reactor.exceptions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class exceptionFlowTest {

  @Test
  void expectError() {
    //when
    Flux<String> fluxError = Flux.just("a", "b", "c")
        .mergeWith(Flux.error(new RuntimeException("Error Test")));

    //then
    StepVerifier.create(fluxError)
        .expectNext("a", "b", "c")
        .expectError()
        .verify();

  }

  @Test
  void expectErrorWithClass() {
    //when
    Flux<String> fluxError = Flux.just("a", "b", "c")
        .mergeWith(Flux.error(new RuntimeException("Error Test")));

    //then
    StepVerifier.create(fluxError)
        .expectNext("a", "b", "c")
        .expectError(RuntimeException.class)
        .verify();

  }

  @Test
  void expectErrorWithMessage() {
    //when
    Flux<String> fluxError = Flux.just("a", "b", "c")
        .mergeWith(Flux.error(new RuntimeException("Error Test")));
    //then
    StepVerifier.create(fluxError)
        .expectNext("a", "b", "c")
        .expectErrorMessage("Error Test")
        .verify();

  }

}
