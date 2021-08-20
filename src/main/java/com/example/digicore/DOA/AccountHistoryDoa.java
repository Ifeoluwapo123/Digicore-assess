package com.example.digicore.DOA;

import com.example.digicore.dto.response.AcctHistoryResponse;
import com.example.digicore.model.AccountHistory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.*;

@Component
public class  AccountHistoryDoa implements Dao<AccountHistory, Map>{

    private Map<String, List<AcctHistoryResponse>> accountInformation = new HashMap<>();

    @Override
    public Optional find(int index) {
        return Optional.ofNullable(accountInformation.get(index));
    }

    @Override
    public Map findAll() {
        return accountInformation;
    }

    @Override
    public AccountHistory save(AccountHistory accountHistory) {
        List<AcctHistoryResponse> info = new ArrayList<>();

        AcctHistoryResponse acctHistoryResponse = new AcctHistoryResponse();
        acctHistoryResponse.setAccountBalance(accountHistory.getAccountBalance());
        acctHistoryResponse.setAmount(accountHistory.getAmount());
        acctHistoryResponse.setNarration(accountHistory.getNarration());
        acctHistoryResponse.setTransactionDate(accountHistory.getTransactionDate());
        acctHistoryResponse.setTransactionType(accountHistory.getTransactionType());

        if(accountInformation.containsKey(accountHistory.getAccountNumber())){
            List<AcctHistoryResponse> info2 = accountInformation.get(accountHistory.getAccountNumber());
            info2.add(acctHistoryResponse);
            accountInformation.put(accountHistory.getAccountNumber(),
                   info2);
        }else{
            accountInformation.put(accountHistory.getAccountNumber(),
                    info);
        }

        return accountHistory;
    }

    @Override
    public void delete(AccountHistory o) {
        accountInformation.remove(o);
    }

    public List<AcctHistoryResponse> getAccountHistory(String accountNumber){
        System.out.println(accountInformation.get(accountNumber));
        return accountInformation.get(accountNumber) != null ? accountInformation.get(accountNumber): new ArrayList<>();
    }
}
