package one.digitalinnovation.WineStock.controller;

import lombok.AllArgsConstructor;
import one.digitalinnovation.WineStock.dto.QuantityDTO;
import one.digitalinnovation.WineStock.dto.WineDTO;
import one.digitalinnovation.WineStock.exception.WineAlreadyRegisteredException;
import one.digitalinnovation.WineStock.exception.WineNotFoundException;
import one.digitalinnovation.WineStock.exception.WineStockExceededException;
import one.digitalinnovation.WineStock.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wines")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WineController implements WineControllerDocs{

    private final WineService wineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WineDTO createWine(@RequestBody @Valid WineDTO wineDTO) throws WineAlreadyRegisteredException {
        return wineService.createWine(wineDTO);
    }

    @GetMapping("/{name}")
    public WineDTO findByName(@PathVariable String name) throws WineNotFoundException {
        return wineService.findByName(name);
    }

    @GetMapping
    public List<WineDTO> listWines() {
        return wineService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws WineNotFoundException {
        wineService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public WineDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws WineNotFoundException, WineStockExceededException {
        return wineService.increment(id, quantityDTO.getQuantity());
    }
}
