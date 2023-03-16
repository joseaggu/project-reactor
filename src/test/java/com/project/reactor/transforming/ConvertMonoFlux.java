package com.project.reactor.transforming;

import java.util.List;

import com.project.reactor.utils.Mocks;
import com.project.reactor.utils.domain.Parent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ConvertMonoFlux {

  @Test
  void convertMonoListToFlux() {
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

  @Test
  void convertFluxToMonoList() {
    //given
    List<Parent> data = Mocks.buildParent();

    //when
    Mono<List<String>> actual = Flux.fromIterable(data)
        .map(Parent::getName)
        .collectList();

    //then
    StepVerifier.create(actual)
        .expectNext(List.of("Alex", "Carlos"))
        .verifyComplete();
  }

  @Test
  void convertMonoListToFluxWithSet() {
    //given
    List<Parent> data = Mocks.buildParent();

    //when
    Flux<String> actual = Mono.just(data)
        .flatMapMany(Flux::fromIterable)
        .flatMap(parent -> Mono.just(parent.getName().toLowerCase())
            .map(x -> {
              parent.setLastName(x);
              return parent;
            }))
        .map(Parent::getLastName);

    //then
    StepVerifier.create(actual)
        .expectNext("alex", "carlos")
        .verifyComplete();
  }
}
