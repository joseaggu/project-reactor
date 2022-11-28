package com.project.reactor;

import java.time.Duration;
import java.util.Random;

import com.project.reactor.domain.Child;
import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ConcatMapTest {

  @Test
  void contactMap() {
    //given
    var data = Mocks.buildParent();

    //when
    var actual = Flux.fromIterable(data)
        .concatMap(t -> Flux.fromIterable(t.getNamesChildren())
            .delayElements(Duration.ofMillis(new Random().nextInt(1000)))) // executed in order different as flatmap, that one works async
        .map(Child::getName)
        .map(String::toUpperCase)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("PETER", "MARIA", "JOAQUIN", "ROBERTO")
        .verifyComplete();
  }

}

