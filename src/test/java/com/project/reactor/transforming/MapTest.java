package com.project.reactor.transforming;

import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Map operator it's used to transforme one element to other
 */
class MapTest {

  @Test
  void fluxMap() {
    //given
    Flux<String> fluxMethod = Flux.fromIterable(Mocks.buildColors());

    //when
    Flux<String> actual = fluxMethod
        .map(String::toUpperCase).log();

    //then
    StepVerifier.create(actual)
        .expectNext("RED", "BLUE", "BLACK")
        .verifyComplete();
  }

  @Test
  void monoMap() {
    //given
    Mono<String> fluxMethod = Mono.just("red").log();

    //when
    Mono<String> actual = fluxMethod
        .map(String::toUpperCase);

    //then
    StepVerifier.create(actual)
        .expectNext("RED")
        .verifyComplete();
  }

}

