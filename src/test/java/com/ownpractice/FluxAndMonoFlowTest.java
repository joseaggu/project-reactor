package com.ownpractice;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoFlowTest {

  @Test
  @Description("Example Flux with StepVerifier")
  void name() {
    //when
    var result = FluxAndMonoFlow.colorsMono;

    //then
    StepVerifier.create(result)
        .expectNext("red")
        .verifyComplete();

  }

  @Test
  @Description("Example Mono with StepVerifier")
  void namesMono() {

    //when
    var namesMono = Mono.just("alex");

    //then
    StepVerifier.create(namesMono)
        .expectNext("alex")
        .expectNextCount(0)
        .verifyComplete();
  }
}
