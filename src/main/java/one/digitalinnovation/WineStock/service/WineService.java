package one.digitalinnovation.WineStock.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.WineStock.dto.WineDTO;
import one.digitalinnovation.WineStock.entity.Wine;
import one.digitalinnovation.WineStock.exception.WineAlreadyRegisteredException;
import one.digitalinnovation.WineStock.exception.WineNotFoundException;
import one.digitalinnovation.WineStock.exception.WineStockExceededException;
import one.digitalinnovation.WineStock.mapper.WineMapper;
import one.digitalinnovation.WineStock.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WineService {

    private final WineRepository wineRepository;
    private final WineMapper wineMapper = WineMapper.INSTANCE;

    public WineDTO createWine(WineDTO wineDTO) throws WineAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(wineDTO.getName());
        Wine wine = wineMapper.toModel(wineDTO);
        Wine savedWine = wineRepository.save(wine);
        return wineMapper.toDTO(savedWine);
    }

    public WineDTO findByName(String name) throws WineNotFoundException {
        Wine foundWine = wineRepository.findByName(name)
                .orElseThrow(() -> new WineNotFoundException(name));
        return wineMapper.toDTO(foundWine);
    }

    public List<WineDTO> listAll() {
        return wineRepository.findAll()
                .stream()
                .map(wineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws WineNotFoundException {
        verifyIfExists(id);
        wineRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws WineAlreadyRegisteredException {
        Optional<Wine> optSavedWine = wineRepository.findByName(name);
        if (optSavedWine.isPresent()) {
            throw new WineAlreadyRegisteredException(name);
        }
    }

    private Wine verifyIfExists(Long id) throws WineNotFoundException {
        return wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException(id));
    }

    public WineDTO increment(Long id, int quantityToIncrement) throws WineNotFoundException, WineStockExceededException {
        Wine wineToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + wineToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= wineToIncrementStock.getMax()) {
            wineToIncrementStock.setQuantity(wineToIncrementStock.getQuantity() + quantityToIncrement);
            Wine incrementedWineStock = wineRepository.save(wineToIncrementStock);
            return wineMapper.toDTO(incrementedWineStock);
        }
        throw new WineStockExceededException(id, quantityToIncrement);
    }
}
