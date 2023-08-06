package com.example.demo;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootApplication
public class SpringBootReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactiveApplication.class, args);
	}

}

@org.springframework.web.bind.annotation.RestController
class RestController {

	@GetMapping("/test")
	public String test() {
		return "test";

	}

	@GetMapping("/testM")
	public Mono<String> getM() {
		return compute();
	}

	private Mono<String> compute() {
		return Mono.just("Test Mono");
	}

	@GetMapping("twoMono")
	Mono<String> handleTwoMono() {
		return computeFullName();
	}

	private Mono<String> computeFullName() {
		return getFirstName()
				.zipWith(getLastName())
				.map(value -> {
					return value.getT1() + value.getT2();
				});

	}

	private Mono<String> computeFullName1() {
		return getFirstName()
				.zipWith(getLastName())
				.map(new Function<Tuple2<String, String>, String>() {

					@Override
					public String apply(Tuple2<String, String> arg0) {
						return arg0.getT1() + arg0.getT2();
					}

				});

	}

	private Mono<String> getFirstName() {
		return Mono.just("Ravi").delayElement(Duration.ofSeconds(5));

	}

	private Mono<String> getLastName() {
		return Mono.just("More").delayElement(Duration.ofSeconds(5));
	}
}
