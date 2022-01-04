package springframework.spring5webflex.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.spring5webflex.domain.Category;
import springframework.spring5webflex.domain.Vendor;
import springframework.spring5webflex.repositories.CategoryRepository;
import springframework.spring5webflex.repositories.VendorRepository;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0 || vendorRepository.count().block() == 0){
            System.out.println("#### Loading data on Bootstrap... ####");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Dried fruits").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            categoryRepository.save(Category.builder().description("Milch").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Pavel").lastName("Titirca").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Iacov").lastName("Paraschiv").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Acsah").lastName("Jonesh").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Abigail").lastName("Titirca").build()).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());

        }
    }
}
