package com.project.reactor.transforming;

import java.util.function.Function;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import com.project.reactor.utils.domain.ParentMapper;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class TransformTest {

  /**
   * Transforma un objeto a otro, similar a como trabaja un mapper
   */
  @Test
  void transform() {
    //given

    var data = Mocks.buildParent();

    Function<Flux<Parent>, Flux<ParentMapper>> functionTransform = it -> it.map(et -> ParentMapper.builder()
        .nameMapper(et.getName())
        .build());

    //when

    var actual = Flux.fromIterable(data)
        .transform(functionTransform)
        .map(ParentMapper::getNameMapper).log();

    //then

    StepVerifier.create(actual)
        .expectNext("Alex", "Carlos")
        .verifyComplete();

  }
}

