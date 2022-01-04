package springframework.spring5webflex.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.spring5webflex.domain.Category;
import springframework.spring5webflex.repositories.CategoryRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                Category.builder().description("Cat2").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {

        given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get().uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreateCategory(){
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("descr").build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("someCat").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateCategory(){
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("someCat").build());

        webTestClient.put()
                .uri("/api/v1/categories/sameId")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchCategory(){
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("someCat").build());

        webTestClient.patch()
                .uri("/api/v1/categories/sameId")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void testPatchCategoryNoChanges(){
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri("/api/v1/categories/sameId")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());
    }
}