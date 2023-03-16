package com.project.reactor.transforming;

import java.util.List;

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
    List<Parent> data = Mocks.buildParent();

    //when
    Flux<String> actual = Mono.just(data)
        .flatMapMany(Flux::fromIterable).log()
        .map(Parent::getName);

    //then
    StepVerifier.create(actual)
        .expectNext("Alex", "Carlos")
        .verifyComplete();
  }

}

