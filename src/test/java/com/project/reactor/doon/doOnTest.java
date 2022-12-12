package com.project.reactor.doon;

import java.util.List;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * <p>
 * <img class="marble" src="doon.png" alt="">
 * <p>
 */
class doOnTest {

  @Test
  void success() {
    //when
    Flux.fromIterable(List.of("a", "b"))
        .doOnSubscribe(consumer -> System.out.println("doOnSubscribe: " + consumer))
        .doOnNext(consumer -> System.out.println("doOnNext: " + consumer))
        .doOnComplete(() -> System.out.println("doOnComplete"))
        .doFinally(onFinally -> System.out.println("doFinally: " + onFinally))
        .subscribe();
  }

  @Test
  void error() {
    //when
    Flux.fromIterable(List.of("a", "b")).concatWith(Flux.error(new RuntimeException("Error Test")))
        .doOnSubscribe(consumer -> System.out.println("doOnSubscribe: " + consumer))
        .doOnNext(consumer -> System.out.println("doOnNext: " + consumer))
        .doOnComplete(() -> System.out.println("doOnComplete"))
        .doOnError(consumer -> System.out.println("doOnError: " + consumer))
        .doFinally(onFinally -> System.out.println("doFinally: " + onFinally))
        .subscribe();
  }
}
