package com.project.reactor.transforming;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * It's use it to transform mono to flux element
 */
class FlatMapManyTest {

  /**
   * Convert Mono.just([]) to Flux<Object>
   */
  @Test
  void flatMapMany() {
    //given
    var data = Mocks.buildParent();

    //when
    var actual = Mono.just(data)
        .flatMapMany(Flux::fromIterable).log()
        .map(Parent::getName);

    //then
    StepVerifier.create(actual)
        .expectNext("Alex", "Carlos")
        .verifyComplete();
  }
}

