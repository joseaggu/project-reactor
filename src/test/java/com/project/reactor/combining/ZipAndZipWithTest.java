package com.project.reactor.combining;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
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
    Flux<String> dataOne = Flux.just("a", "b");
    Flux<String> dataTwo = Flux.just("1", "2");

    //when
    Flux<String> actual = Flux.zip(dataOne, dataTwo, (first, second) -> first + second)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("a1", "b2")
        .verifyComplete();
  }

  @Test
  void zipWith() {
    //given
    Flux<String> dataOne = Flux.just("a", "b");
    Flux<String> dataTwo = Flux.just("1", "2");

    //when
    Flux<String> actual = dataOne.zipWith(dataTwo, (first, second) -> first + second)
        .log();

    //then
    StepVerifier.create(actual)
        .expectNext("a1", "b2")
        .verifyComplete();
  }

  @Test
  void zipWithOther() {
    //given
    Flux<Parent> dataOne = Flux.fromIterable(Mocks.buildParent());
    Flux<Parent> dataTwo = Flux.fromIterable(Mocks.buildParent());

    //when
    Flux<String> actual =
        dataOne.zipWith(dataTwo, (first, second) -> first.getName().toUpperCase().concat("_").concat(second.getName().toLowerCase()))
            .log();

    //then
    StepVerifier.create(actual)
        .expectNext("ALEX_alex", "CARLOS_carlos")
        .verifyComplete();
  }

}
