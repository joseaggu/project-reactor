package com.ownpractice;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoFlow {

  public static final Flux<String> colorsFlux = Flux.fromIterable(List.of("red", "blue", "black")).log();

  public static final Mono<String> colorsMono = Mono.just("red").log();


  public static void main(String[] args) {

    FluxAndMonoFlow.colorsFlux
        .subscribe();


    FluxAndMonoFlow.colorsMono
        .subscribe();

  }

}
