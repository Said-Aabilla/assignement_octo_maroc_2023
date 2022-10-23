package ma.octo.assignement.web.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.dto.TransferRequestDto;
import ma.octo.assignement.dto.TransferResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.facade.TransferService;
import ma.octo.assignement.web.facade.TransferController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.octo.assignement.domain.util.ApiPaths.*;

@RestController
@RequestMapping(API + V1 + TRANSFERS)
@RequiredArgsConstructor
@Slf4j
class TransferControllerImpl implements TransferController {


    private final TransferService transferService;



    @Override
    public List<TransferResponseDto> getAllTransfers() {
        log.info("Calling getAllTransfers from Transfer service");

        return transferService.getAllTransfers();
    }

    @Override
    public TransferResponseDto createTransfer(TransferRequestDto transferRequestDto) throws CompteNonExistantException, TransactionException {
        log.info("Calling create Transfer from Transfer service");

        return transferService.createTransfer(transferRequestDto);
    }

}
