package com.project.reactor.basics;

import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoFlowTest {

  @Test
  void monoJust() {
    StepVerifier.create(Mono.just("red").log()) //Similar to how get data from DB or Service
        .expectNext("red")
        .verifyComplete();
  }

  @Test
  void fluxIterable() {
    StepVerifier.create(Flux.fromIterable(Mocks.buildColors()).log())// Similar to how get data from DB or Service
        .expectNext("red", "blue", "black")
        .verifyComplete();
  }

  @Test
  void fluxJust() {
    StepVerifier.create(Flux.just(Mocks.buildColors()).log())// Similar to how get data from DB or Service
        .expectNext(Mocks.buildColors())
        .verifyComplete();
  }

}

