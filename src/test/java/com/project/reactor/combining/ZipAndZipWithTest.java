package com.project.reactor.combining;

import com.project.reactor.utils.Mocks;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Zip or combine two sources together
 */
class ZipAndZipWithTest {

  @Test
  void zip() {
    //given
    var dataOne = Flux.just("a", "b");
    var dataTwo = Flux.just("1", "2");

    //when
    var actual = Flux.zip(dataOne, dataTwo, (first, second) -> first + second)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("a1", "b2")
        .verifyComplete();
  }

  @Test
  void zipWith() {
    //given
    var dataOne = Flux.just("a", "b");
    var dataTwo = Flux.just("1", "2");

    //when
    var actual = dataOne.zipWith(dataTwo, (first, second) -> first + second)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("a1", "b2")
        .verifyComplete();
  }

  @Test
  void zipWithOther() {
    //given
    var dataOne = Flux.fromIterable(Mocks.buildParent());
    var dataTwo = Flux.fromIterable(Mocks.buildParent());

    //when
    var actual =
        dataOne.zipWith(dataTwo, (first, second) -> first.getName().toUpperCase().concat("_").concat(second.getName().toLowerCase()))
            .log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX_alex", "CARLOS_carlos")
        .verifyComplete();
  }

}
