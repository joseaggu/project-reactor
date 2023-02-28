package com.project.reactor.transforming;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Child;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Works similar to flatmap, the difference it's concatMap preserver the ordering
 */
class ConcatMapTest {

  @Test
  void concatMap() {
    //given
    List<Parent> data = Mocks.buildParent();

    //when
    Flux<String> actual = Flux.fromIterable(data)
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

