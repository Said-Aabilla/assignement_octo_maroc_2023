package ma.octo.assignement.web.facade;
import ma.octo.assignement.dto.TransferRequestDto;
import ma.octo.assignement.dto.TransferResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static ma.octo.assignement.domain.util.ApiPaths.*;

@RestController
@RequestMapping(API+V1+TRANSFERS)
public interface TransferController {
    @GetMapping
    List<TransferResponseDto> getAllTransfers() ;

    @PostMapping
    TransferResponseDto createTransfer (TransferRequestDto transferRequestDto) throws CompteNonExistantException, TransactionException;

}
