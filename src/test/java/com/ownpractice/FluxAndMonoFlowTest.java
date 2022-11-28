package com.ownpractice;

import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoFlowTest {

  private static List<String> getColors() {
    return List.of("red", "blue", "black");
  }

  @Test
  void name() {
    StepVerifier.create(Mono.just("red").log())//Similar to how get data from DB or Service
        .expectNext("red")
        .verifyComplete();
  }

  @Test
  void fluxAllMethods() {
    StepVerifier.create(Flux.fromIterable(getColors()).log())// Similar to how get data from DB or Service
        .expectNext("red", "blue", "black")
        .verifyComplete();
  }

  @Test
  void fluxMap() {
    //given
    var fluxMethod = Flux.fromIterable(getColors()).log();

    //when
    var actual = fluxMethod
        .map(String::toUpperCase);

    //then
    StepVerifier.create(actual)
        .expectNext("RED", "BLUE", "BLACK")
        .verifyComplete();
  }

  @Test
  void filterFlux() {
    //given
    var fluxMethod = Flux.fromIterable(getColors());

    //when
    var actual = fluxMethod.filter(it -> it.length() <= 4).log();

    //then
    StepVerifier.create(actual)
        .expectNext("red", "blue")
        .verifyComplete();
  }

  @Test
  void homeWork() {

    //when
    var actual = namesMono_map_filter(3).log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX")
        .verifyComplete();
  }

  private Mono<String> namesMono_map_filter(int stringLength) {
    return Mono.just("alex")
        .filter(it -> it.length() > stringLength)
        .map(String::toUpperCase);
  }
}
