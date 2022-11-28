package com.project.reactor;

import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoLikeFluxBehaviorTest {

  @Test
  void monoLikeFluxBehavior() {
    //given

    var data = Mocks.buildParent();

    //when
    var actual = Mono.just(data)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext(data)
        .verifyComplete();
  }

}

