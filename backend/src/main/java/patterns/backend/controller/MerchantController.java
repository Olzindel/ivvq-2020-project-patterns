package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Merchant;
import patterns.backend.dto.MerchantDto;
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
    public ResponseEntity<List<MerchantDto>> getMerchants() {
        List<MerchantDto> merchantDtos = merchantService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(merchantDtos, HttpStatus.OK);
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<MerchantDto> getMerchant(@PathVariable("merchantId") final Long merchantId) {
        MerchantDto merchantDto = convertToDto(merchantService.findMerchantById(merchantId));
        return new ResponseEntity<>(merchantDto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<MerchantDto> createMerchant(@RequestBody Merchant merchant) {
        MerchantDto merchantDto = convertToDto(merchantService.create(merchant));
        return new ResponseEntity<>(merchantDto, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<MerchantDto> updateMerchant(@RequestBody Merchant merchant) {
        MerchantDto merchantDto = convertToDto(merchantService.update(merchant));
        return new ResponseEntity<>(merchantDto, HttpStatus.OK);
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity<String> deleteMerchant(@PathVariable("merchantId") final Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private MerchantDto convertToDto(Merchant merchant) {
        MerchantDto merchantDTO = modelMapper.map(merchant, MerchantDto.class);
        return merchantDTO;
    }

}
