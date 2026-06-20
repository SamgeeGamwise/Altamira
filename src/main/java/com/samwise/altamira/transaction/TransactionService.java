package com.samwise.altamira.transaction;

import com.samwise.altamira.account.AccountRepository;
import com.samwise.altamira.account.AccountService;
import com.samwise.altamira.account.domain.Account;
import com.samwise.altamira.common.exceptions.NotFoundException;
import com.samwise.altamira.transaction.domain.Transaction;
import com.samwise.altamira.transaction.domain.TransactionType;
import com.samwise.altamira.transaction.dto.DepositRequest;
import com.samwise.altamira.transaction.dto.TransactionResponse;
import com.samwise.altamira.transaction.dto.WithdrawalRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public TransactionResponse getTransaction(UUID id) {
        Transaction transaction = transactionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));

        return TransactionResponse.from(transaction);
    }

    public List<TransactionResponse> getAccountTransactions(UUID accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new NotFoundException("Account not found");
        }

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        return TransactionResponse.fromList(transactions);
    }

    @Transactional
    public TransactionResponse createWithdrawal(WithdrawalRequest request) {
        Account account = accountService.getActiveAccountOrThrow(request.accountId());

        account.setBalance(account.getBalance().subtract(request.amount()));

        Transaction transaction = new Transaction(
            account,
            TransactionType.WITHDRAWAL,
            request.amount(),
            request.description()
        );

        Transaction savedTransaction = transactionRepository.save(transaction);
        accountRepository.save(account);

        return TransactionResponse.from(savedTransaction);
    }

    @Transactional
    public TransactionResponse createDeposit(DepositRequest request) {
        Account account = accountService.getActiveAccountOrThrow(request.accountId());

        account.setBalance(account.getBalance().add(request.amount()));

        Transaction transaction = new Transaction(
                account,
                TransactionType.DEPOSIT,
                request.amount(),
                request.description()
        );

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return TransactionResponse.from(transaction);
    }
}
