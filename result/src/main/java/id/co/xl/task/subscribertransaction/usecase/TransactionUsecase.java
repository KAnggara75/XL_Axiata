package id.co.xl.task.subscribertransaction.usecase;

import id.co.xl.task.subscribertransaction.config.variable.AppConstant;
import id.co.xl.task.subscribertransaction.model.entity.TransactionDetail;
import id.co.xl.task.subscribertransaction.model.response.GenericResponse;
import id.co.xl.task.subscribertransaction.model.response.GetPinRs;
import id.co.xl.task.subscribertransaction.repository.TransactionRepository;
import id.co.xl.task.subscribertransaction.service.PinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionUsecase {
    private TransactionRepository transactionRepository;
    private PinService pinService;

    public TransactionUsecase(TransactionRepository transactionRepository, PinService pinService) {
        this.transactionRepository = transactionRepository;
        this.pinService = pinService;
    }

    public ResponseEntity<Object> getTransactionSummary(String msisdn, String pin) {
        String validMsisdn = AppConstant.VALIDMSISDN;

        GenericResponse<List<TransactionDetail>> detailGenericResponse = new GenericResponse<List<TransactionDetail>>()
                .setCode("00")
                .setMessage("success")
                .setStatus("ok");

        GetPinRs getPinRs = pinService.getPin(msisdn);

        if (!msisdn.equals(validMsisdn)) {
            detailGenericResponse.setCode(getPinRs.getCode())
                    .setMessage(getPinRs.getMessage())
                    .setStatus(getPinRs.getStatus());
            return new ResponseEntity<>(detailGenericResponse, HttpStatus.NOT_FOUND);
        }

        if (!getPinRs.getData().equals(pin)) {
            detailGenericResponse.setCode("01")
                    .setMessage("Invalid Pin")
                    .setStatus("Unauthorized");
            return new ResponseEntity<>(detailGenericResponse, HttpStatus.UNAUTHORIZED);
        }

        if (getPinRs.getCode().equals("00") && getPinRs.getData().equals(pin)) {
            List<TransactionDetail> transactionDetailList = transactionRepository.fetchByMSISDN(msisdn);
            detailGenericResponse.setData(transactionDetailList);
        }

        return new ResponseEntity<>(detailGenericResponse, HttpStatus.OK);
    }
}
