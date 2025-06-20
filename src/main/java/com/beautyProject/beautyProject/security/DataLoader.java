package com.beautyProject.beautyProject.security;

import com.beautyProject.beautyProject.model.entity.ProductCategory;
import com.beautyProject.beautyProject.model.entity.SkinType;
import com.beautyProject.beautyProject.repository.ProductCategoryRepository;
import com.beautyProject.beautyProject.repository.ProductRepository;
import com.beautyProject.beautyProject.repository.SkinTypeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final SkinTypeRepository skinTypeRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    public DataLoader(SkinTypeRepository skinTypeRepository,
                      ProductRepository productRepository,
                      ProductCategoryRepository categoryRepository) {
        this.skinTypeRepository = skinTypeRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Əgər dəri tipləri yoxdursa, onları əlavə edirik
        if (skinTypeRepository.count() == 0) {
            loadSkinTypes();
        }

        // Əgər məhsul kateqoriyaları yoxdursa, onları əlavə edirik
        if (categoryRepository.count() == 0) {
            loadProductCategories();
        }

        // Əgər məhsullar yoxdursa, nümunə məhsullar əlavə edirik
        if (productRepository.count() == 0 && categoryRepository.count() > 0) {
            loadProducts();
        }
    }

    private void loadSkinTypes() {
        // Yağlı dəri tipi
        SkinType oily = new SkinType();
        oily.setName("oily");
        oily.setDescription("Yağlı dəri tipi");
        oily.setRecommendationText("Yağlı dəri üçün yüngül, su əsaslı nəmləndiricilərdən istifadə edin. Salicylic acid və niacinamide tərkibli məhsullar sizə kömək edə bilər.");
        skinTypeRepository.save(oily);

        // Quru dəri tipi
        SkinType dry = new SkinType();
        dry.setName("dry");
        dry.setDescription("Quru dəri tipi");
        dry.setRecommendationText("Quru dəri üçün zəngin nəmləndiricilərdən istifadə edin. Hyaluronic acid və ceramide tərkibli məhsullar sizə kömək edə bilər.");
        skinTypeRepository.save(dry);

        // Kombination dəri tipi
        SkinType combination = new SkinType();
        combination.setName("combination");
        combination.setDescription("Kombination dəri tipi");
        combination.setRecommendationText("T zonaya diqqət yotirin, balanslaşdırıcı məhsullardan istifadə edin.");
        skinTypeRepository.save(combination);

        // Normal dəri tipi
        SkinType normal = new SkinType();
        normal.setName("normal");
        normal.setDescription("Normal dəri tipi");
        normal.setRecommendationText("Balanslaşdırılmış qulluq rutini yaradın, nəmləndirici və günəş kremindən istifadə edin.");
        skinTypeRepository.save(normal);
    }

    private void loadProductCategories() {
        // Məhsul kateqoriyalarını əlavə edirik
        ProductCategory cleanser = new ProductCategory();
        cleanser.setName("Cleanser");
        cleanser.setDescription("Təmizləyici məhsullar");
        categoryRepository.save(cleanser);

        ProductCategory toner = new ProductCategory();
        toner.setName("Toner");
        toner.setDescription("Toner və essence məhsulları");
        categoryRepository.save(toner);

        ProductCategory serum = new ProductCategory();
        serum.setName("Serum");
        serum.setDescription("Xüsusi problemlər üçün konsentrasiya olunmuş məhsullar");
        categoryRepository.save(serum);

        ProductCategory moisturizer = new ProductCategory();
        moisturizer.setName("Moisturizer");
        moisturizer.setDescription("Nəmləndirici məhsullar");
        categoryRepository.save(moisturizer);

        ProductCategory sunscreen = new ProductCategory();
        sunscreen.setName("Sunscreen");
        sunscreen.setDescription("Günəş qoruyucu məhsullar");
        categoryRepository.save(sunscreen);

        ProductCategory mask = new ProductCategory();
        mask.setName("Mask");
        mask.setDescription("Üz maskaları");
        categoryRepository.save(mask);
    }

    private void loadProducts() {
        // Nümunə məhsulları əlavə etmək
        // Bu metod daha sonra tamamlanacaq
    }
}