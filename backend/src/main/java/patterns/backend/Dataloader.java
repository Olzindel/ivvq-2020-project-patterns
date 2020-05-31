package patterns.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.domain.Role;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.services.ImageLinkService;
import patterns.backend.services.ProductService;
import patterns.backend.services.UserService;

@Component
@Profile("PREPROD")
public class Dataloader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageLinkService imageLinkService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUser();
        initProduct();
    }

    private void initUser() {
        UserInput userInput = new UserInput("admin", "admin", "admin", "admin", "admin@gmail.com", "M", "8", "4500", "Toulouse", Role.MERCHANT, null);
        userService.create(userInput);
    }

    private void initProduct() {
        ProductInput arturiaPendragonInput = new ProductInput("Arturia Pendragon", 17f, ProductStatus.AVAILABLE, "", 1, null);
        ProductInput violetEvergardenInput = new ProductInput("Violet Evergarden", 16f, ProductStatus.AVAILABLE, "", 4, null);
        ProductInput inoriYuzurihaInput = new ProductInput("Inori Yuzuriha", 16f, ProductStatus.AVAILABLE, "", 4, null);
        ProductInput lucinaInput = new ProductInput("Lucina", 16f, ProductStatus.AVAILABLE, "", 4, null);
        ProductInput corrinInput = new ProductInput("Corrin", 16f, ProductStatus.AVAILABLE, "", 4, null);


        Product violetEvergarden = productService.create(violetEvergardenInput);
        Product arturiaPendragon = productService.create(arturiaPendragonInput);
        Product inoriYuzuriha = productService.create(inoriYuzurihaInput);
        Product lucina = productService.create(lucinaInput);
        Product corrin = productService.create(corrinInput);


        ImageLinkInput arturiaImageLink1 = new ImageLinkInput("https://waifu.clan.su/_ld/2/54338270.jpg", arturiaPendragon.getId());
        ImageLinkInput arturiaImageLink2 = new ImageLinkInput("https://i.redd.it/z9vzlizb8sl01.jpg", arturiaPendragon.getId());
        ImageLinkInput violetImageLink = new ImageLinkInput("https://s.itl.cat/pngfile/s/153-1532902_violet-evergarden-full-violet-evergarden-luculia-marlborough.png", violetEvergarden.getId());
        ImageLinkInput inoriImageLink = new ImageLinkInput("https://www.anime-planet.com/images/characters/inori-yuzuriha-30505.jpg?t=1571074008", inoriYuzuriha.getId());
        ImageLinkInput lucinaImageLink = new ImageLinkInput("https://static.zerochan.net/Lucina.%28Fire.Emblem%29.full.2681058.jpg", lucina.getId());
        ImageLinkInput corrinImageLink = new ImageLinkInput("https://66.media.tumblr.com/340f4287378a2f5d33f1f4821fae1ba3/tumblr_pga9xi6Fml1u8syzno1_400.png", corrin.getId());

        imageLinkService.create(arturiaImageLink1);
        imageLinkService.create(arturiaImageLink2);
        imageLinkService.create(violetImageLink);
    }
}
