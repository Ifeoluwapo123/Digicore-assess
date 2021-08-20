package com.example.digicore.DOA;

import com.example.digicore.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.*;

@Component
public class AccountDao implements Dao<Account, List>{
    private List<Account> accounts = new ArrayList<>();

    private Comparator<Account> userId = Comparator.comparing(Account::getId);

    private Comparator<Account> accountName = Comparator.comparing(Account::getAccountName);

    private Comparator<Account> accountNumber = Comparator.comparing(Account::getAccountNumber);

    @Override
    public Optional<Account> find(int index) {
        return Optional.ofNullable(accounts.get(index));
    }

    @Override
    public List findAll() {
        return accounts;
    }

    @Override
    public Account save(Account account) {
        try{
            if(account.getId() == null){
                account.setId((long) (count() + 1));
                accounts.add(account);
            }else{
                accounts.set(findUserIndexById(account.getId()), account);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return account;
    }

    @Override
    public void delete(Account account) {
        accounts.remove(account);
    }

    private int count() {
        return accounts.size();
    }

    public int findUserIndexByAccountName(String accName){
        return Collections.binarySearch(
                accounts, new Account(accName), accountName);
    }

    public int findUserIndexById(Long id){
        return Collections.binarySearch(
                accounts, new Account(id), userId);
    }

    public int findUserIndexByAccountNumber(Long id, String accNumber){
        return Collections.binarySearch(
                accounts, new Account(id, accNumber), accountNumber);
    }

}
