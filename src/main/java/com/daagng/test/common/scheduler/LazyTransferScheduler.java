package com.daagng.test.common.scheduler;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;
import static com.daagng.test.common.constants.bank.TransferConstant.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daagng.test.api.response.bankingSystem.BankingSystemSearchTransferResponse;
import com.daagng.test.api.service.TransferService;
import com.daagng.test.api.service.WebClientService;
import com.daagng.test.db.entity.Transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LazyTransferScheduler {
	@Value("${isRealBankingSystem}")
	boolean isRealBankingSystem;

	private final TransferService transferService;
	private final WebClientService webClientService;

	@Scheduled(cron = "*/5 * * * * *")
	public void lazyTransferScheduler() {
		for (Transfer transfer :
			transferService.findAllByState(TRANSFER_WAITING)) {
			String path = TRANSFER_PATH + "/" + transfer.getId();
			BankingSystemSearchTransferResponse transferResponse;
			if (isRealBankingSystem) {
				transferResponse = webClientService.bankingServiceGet(
					BankingSystemSearchTransferResponse.class, path);
			} else {
				// 뱅킹 서비스가 작동하지 않으면 항상 성공을 반환함을 가정
				transferResponse = new BankingSystemSearchTransferResponse(SUCCESS_SIGNATURE);
			}
			transfer.finishedRequest(transferResponse.getResult(), null);
			transferService.save(transfer);
			log.info("tx_id : " + transfer.getId() + "의 이체결과가 업데이트 됐습니다. result : " + transferResponse.getResult());
		}
	}
}
