package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Merchant;
import patterns.backend.dto.MerchantDTO;
import patterns.backend.services.MerchantService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/merchant", produces = "application/json")
public class MerchantController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MerchantService merchantService;

    @GetMapping()
    public List<MerchantDTO> getMerchants() {
        List<Merchant> merchants = merchantService.findAll();
        return merchants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public void getMerchant(@PathVariable final Long id) {
        merchantService.findMerchantById(id);
    }

    @PutMapping()
    public void addMerchant(@RequestBody Merchant merchant) {
        merchantService.saveMerchant(merchant);
    }

    @DeleteMapping("/{id}")
    public void deleteMerchant(@PathVariable final Long id) {
        merchantService.deleteMerchantById(id);
    }

    private MerchantDTO convertToDto(Merchant merchant) {
        MerchantDTO merchantDTO = modelMapper.map(merchant, MerchantDTO.class);
        return merchantDTO;
    }

}
