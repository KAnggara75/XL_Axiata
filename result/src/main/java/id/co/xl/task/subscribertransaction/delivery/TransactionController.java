package id.co.xl.task.subscribertransaction.delivery;

import id.co.xl.task.subscribertransaction.model.request.GetMonthlyTransactionRq;
import id.co.xl.task.subscribertransaction.service.ValidationService;
import id.co.xl.task.subscribertransaction.usecase.TransactionUsecase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionUsecase transactionUsecase;
    private final ValidationService validationService;

   
    public TransactionController(TransactionUsecase transactionUsecase, ValidationService validationService) {
        this.transactionUsecase = transactionUsecase;
        this.validationService = validationService;
    }

    @PostMapping("/api/transaction/summary")
    public ResponseEntity<?> getTransactionSummary(@RequestBody() GetMonthlyTransactionRq bodyRq) {
        validationService.validate(bodyRq);
        return transactionUsecase.getTransactionSummary(bodyRq.getMsisdn(), bodyRq.getPin());
    }
}
