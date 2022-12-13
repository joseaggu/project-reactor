package com.project.reactor.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * <p>
 * <img class="marble" src="exceptionHandling.png" alt="">
 * <p>
 */
@Slf4j
class onErrorTest {

  @Test
  void onErrorResume() {
    //when
    var value = Flux.just("a", "b", "c")
        .concatWith(Flux.error(new RuntimeException("error")))
        .onErrorResume(ex -> {
          log.error("Error is: {}", ex.getMessage(), ex);
          return Flux.just("d");
        });

    //then
    StepVerifier.create(value)
        .expectNext("a", "b", "c", "d")
        .verifyComplete();
  }

  @Test
  void onErrorReturn() {
    //when
    var value = Flux.just("a", "b", "c")
        .concatWith(Flux.error(new RuntimeException("error")))
        .onErrorReturn("d");

    //then
    StepVerifier.create(value)
        .expectNext("a", "b", "c", "d")
        .verifyComplete();
  }

  @Test
  void onErrorContinue() {
    //when
    var value = Flux.just("a", "b", "c")
        .map(it -> {
          if (it.equals("b")) {
            throw new IllegalStateException("State not valid");
          }
          return it;
        })
        .onErrorContinue((ex, data) -> {
          log.error("Error is: {} and Data is: {}", ex.getMessage(), data, ex);
        });

    //then
    StepVerifier.create(value)
        .expectNext("a", "c")
        .verifyComplete();
  }

  @Test
  void onErrorMap() {
    //when
    var value = Flux.just("a", "b", "c")
        .map(it -> {
          if (it.equals("b")) {
            throw new IllegalStateException("State not valid");
          }
          return it;
        })
        .onErrorMap((ex) -> {
          throw new RuntimeException("Other error");
        });

    //then
    StepVerifier.create(value)
        .expectNext("a")
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void doOnError() {
    //when
    var value = Flux.just("a", "b", "c")
        .map(it -> {
          if (it.equals("b")) {
            throw new IllegalStateException("State not valid");
          }
          return it;
        })
        .doOnError((ex) -> {
          log.error("Error is: {} ", ex.getMessage(), ex);
        }).log();

    //then
    StepVerifier.create(value)
        .expectNext("a")
        .expectError(IllegalStateException.class)
        .verify();
  }
}
