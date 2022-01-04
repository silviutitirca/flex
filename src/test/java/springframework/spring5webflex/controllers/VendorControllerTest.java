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
import springframework.spring5webflex.domain.Vendor;
import springframework.spring5webflex.repositories.VendorRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {

        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("FName1").lastName("LName1").build(),
                        Vendor.builder().firstName("FName2").lastName("LName2").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("FName").lastName("LName").build()));

        webTestClient.get().uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreateVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().firstName("John").lastName("Doe").build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("John").lastName("Doe").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void  testUpdateVendor(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("John").lastName("Doe").build());

        webTestClient.put()
                .uri("/api/v1/vendors/sameId")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchVendor(){
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("John").lastName("Doe").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/sameId")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    public void testPatchNullVendor(){
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().build());

        webTestClient.patch()
                .uri("/api/v1/vendors/sameId")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());
    }
}