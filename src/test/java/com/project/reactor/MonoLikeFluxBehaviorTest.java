package com.project.reactor;

import java.util.List;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoLikeFluxBehaviorTest {

  @Test
  void monoLikeFluxBehavior() {
    //given

    List<Parent> data = Mocks.buildParent();

    //when
    Mono<List<Parent>> actual = Mono.just(data)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext(data)
        .verifyComplete();
  }

}

