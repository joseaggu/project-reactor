package com.project.reactor.retryrepeat;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

/**
 * <p>
 * <img class="marble" src="img.png" alt="">
 * <p>
 */
@Slf4j
class RetryRepeatTest {

  @Test
  void retryForEver_dangerTest() {

    //when
    var value = Flux.just("a", "b")
        .map(it -> {
          log.error("Retry for ever");
          throw new IllegalArgumentException();

        })
        .retry();

    //then

    StepVerifier.create(value)
        .expectError()
        .verify();
  }

  @Test
  void retryXTimesTest() {

    //when
    var value = Flux.just("a", "b")
        .map(it -> {
          log.error("Retry x times");
          throw new IllegalArgumentException();

        })
        .retry(3);

    //then

    StepVerifier.create(value)
        .expectError()
        .verify();
  }

  @Test
  void retryWhenIfItsIllegalArgumentExceptionTest() {
    var maxAttempts = 8;
    var fixedDelay = Duration.ofMillis(500);
    var retryWhen = Retry.fixedDelay(maxAttempts, fixedDelay)
        .filter(ex -> ex instanceof IllegalArgumentException) // when its this exception retry
        .onRetryExhaustedThrow(
            (retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())); // Propagate  same exception

    //when
    var value = Flux.just("a", "b")
        .map(it -> {
          log.error("Retry X times");
          throw new IllegalStateException("RuntimeException");
        })
        .onErrorMap(ex -> {
          throw new IllegalArgumentException("Change for other exception", ex);
        })
        .retryWhen(retryWhen);

    //then
    StepVerifier.create(value)
        .expectErrorMessage("Change for other exception")
        .verify();
  }

  @Test
  void repeat() {

    //when
    var value = Flux.just("a")
        .map(it -> {
          log.error("Repeat X times");
          return it;
        })
        .repeat(2);

    //then
    StepVerifier.create(value)
        .expectNextCount(3)
        .verifyComplete();
  }
}
