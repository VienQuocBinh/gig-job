package gigjob.service.impl;

import gigjob.common.exception.model.UserNotFoundException;
import gigjob.entity.Account;
import gigjob.model.request.AccountRequest;
import gigjob.repository.AccountRepository;
import gigjob.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountRequest getAccountByEmail(String email) throws UserNotFoundException {
        Account account = accountRepository.findAccountByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        return modelMapper.map(account, AccountRequest.class);
    }

    @Override
    public AccountRequest getAccountById(String id) throws UserNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id + " not found"));
        return modelMapper.map(account, AccountRequest.class);
    }

    @Override
    public AccountRequest getAccountByUsername(String username) throws UserNotFoundException {
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return modelMapper.map(account, AccountRequest.class);
    }
}
