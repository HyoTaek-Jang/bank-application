package com.daagng.test.api.controller.bank;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;
import static com.daagng.test.common.constants.bank.RegisterConstant.*;
import static com.daagng.test.common.constants.bank.TransferConstant.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daagng.test.api.request.bank.RegisterAccountRequest;
import com.daagng.test.api.request.bank.TransferMoneyRequest;
import com.daagng.test.api.request.bankingSystem.BankingSystemRegisterRequest;
import com.daagng.test.api.request.bankingSystem.BankingSystemTransferRequest;
import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.api.response.bank.RegisterAccountResponse;
import com.daagng.test.api.response.bank.TransferHistoryDto;
import com.daagng.test.api.response.bank.TransferHistoryResponse;
import com.daagng.test.api.response.bank.TransferResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemRegisterResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemTransferResponse;
import com.daagng.test.api.service.AccountService;
import com.daagng.test.api.service.HistoryService;
import com.daagng.test.api.service.TransferService;
import com.daagng.test.api.service.ValidationService;
import com.daagng.test.api.service.WebClientService;
import com.daagng.test.api.db.entity.Account;
import com.daagng.test.api.db.entity.Bank;
import com.daagng.test.api.db.entity.Transfer;
import com.daagng.test.api.db.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
public class BankController {
	private final AccountService accountService;
	private final WebClientService webClientService;
	private final ValidationService validationService;
	private final TransferService transferService;
	private final HistoryService historyService;

	@PostMapping("/register")
	public ResponseEntity<? extends BaseResponse> registerAccount(HttpServletRequest request,
		@Valid @RequestBody RegisterAccountRequest registerAccountRequest) {
		User user = (User)request.getAttribute("user");

		// request 유효성 검사
		Long accountNumber = validationService.numbericTest(registerAccountRequest.getAccountNumber(),
			ACCOUNT_NUMBER_SIZE_MSG);
		Bank bank = validationService.bankCodeTest(registerAccountRequest.getCode(), NOT_EXIST_CODE);
		if (accountService.findByAccountNumber(accountNumber) != null)
			return ResponseEntity.status(409).body(new BaseResponse(EXIST_ACCOUNT_NUMBER));

		BankingSystemRegisterRequest bankingSystemRegisterRequest = new BankingSystemRegisterRequest(bank.getCode(),
			registerAccountRequest.getAccountNumber());
		BankingSystemRegisterResponse response = webClientService.bankingServicePost(
			BankingSystemRegisterResponse.class,
			bankingSystemRegisterRequest,
			REGISTER_PATH);

		Account account = new Account(response.getBank_account_id(), accountNumber, user, bank);
		accountService.save(account);

		return ResponseEntity.status(201)
			.body(
				new RegisterAccountResponse(String.format("%0" + ACCOUNT_ID_SIZE + "d", response.getBank_account_id()),
					SUCCESS_REGISTER));
	}

	@PostMapping("/transfer")
	public ResponseEntity<? extends BaseResponse> transferMoney(HttpServletRequest request, @Valid @RequestBody
		TransferMoneyRequest moneyRequest) {
		User user = (User)request.getAttribute("user");

		// request 유효성 검사
		Long toAccountNumber = validationService.numbericTest(moneyRequest.getToAccountNumber(),
			ACCOUNT_NUMBER_SIZE_MSG);
		Long fromAccountId = validationService.numbericTest(moneyRequest.getFromAccountId(), ACCOUNT_ID_SIZE_MSG);
		Bank bank = validationService.bankCodeTest(moneyRequest.getToCode(), NOT_EXIST_CODE);
		Account fromAccount = accountService.findByAccountId(fromAccountId);

		if (fromAccount == null || !fromAccount.getUser().getId().equals(user.getId()))
			return ResponseEntity.status(409).body(new BaseResponse(NOT_MATCHING_USER));

		// 지연 거래 내역 조회
		if (transferService.findByAccountAndState(fromAccount, TRANSFER_WAITING) != null)
			return ResponseEntity.status(500).body(new BaseResponse(EXIST_WAITING_TRANSFER));

		if (transferService.findTxId() == null)
			return ResponseEntity.status(409).body(new BaseResponse(FULL_TX_ID));

		// 기본 값으로 대기 상태로 transfer 생성
		Transfer transfer = new Transfer(toAccountNumber, TRANSFER_WAITING, moneyRequest.getAmount(), null, bank,
			fromAccount);
		transferService.save(transfer);

		BankingSystemTransferRequest bankingSystemTransferRequest = new BankingSystemTransferRequest(transfer.getId(),
			fromAccountId,
			bank.getCode(), moneyRequest.getToAccountNumber(), moneyRequest.getAmount());
		BankingSystemTransferResponse response = webClientService.bankingServicePost(BankingSystemTransferResponse.class,
				bankingSystemTransferRequest,
				TRANSFER_PATH);
		// 요청이 성공적으로 끝나면, transfer의 상태변경
		transfer.finishedRequest(response.getResult(), response.getBank_tx_id());
		transferService.save(transfer);

		return ResponseEntity.status(201)
			.body(new TransferResponse(FINISHED_REQUEST, String.format("%0" + TX_ID_SIZE + "d", transfer.getId()),
				String.format("%0" + TX_ID_SIZE + "d", response.getBank_tx_id()), response.getResult()));
	}

	// 송금 내역 조회 API
	@GetMapping("/history")
	public ResponseEntity<? extends BaseResponse> findTransferHistory(HttpServletRequest request) {
		User user = (User)request.getAttribute("user");
		List<Account> accountList = accountService.findByUser(user);
		List<TransferHistoryDto> responses = historyService.findAllHistory(accountList);

		return ResponseEntity.status(200).body(new TransferHistoryResponse(FIND_TRANSFER_HISTORY, responses));
	}
}
