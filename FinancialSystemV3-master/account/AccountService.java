package account;

import customer.Customer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AccountService {

    private Set<Account> accounts = new HashSet<>();

    private Integer numeroDaConta;
    Account account = new Account(numeroDaConta);

    public Set<Account> listarContasAbertas() {
        return accounts;
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var account = searchAccountByNumber(numeroDaConta);
        return account.getSaldo();
    }

    public void abrir(OpenAccountData accountData) {
        var customer = new Customer(accountData.customerData());
        var conta = new Account(accountData.numeroDaConta(), customer);
        if (accounts.contains(conta)) {
            throw new BusinessRuleException("Já existe outra conta aberta com o mesmo número!");
        }

        accounts.add(conta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal value) {
        var account = searchAccountByNumber(numeroDaConta);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Valor do saque deve ser superior a zero!");
        }

        if (value.compareTo(account.getSaldo()) > 0) {
            throw new BusinessRuleException("Saldo insuficiente!");
        }

        account.sacar(value);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal value) {
        var account = searchAccountByNumber(numeroDaConta);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Valor do deposito deve ser superior a zero!");
        }

        account.depositar(value);
    }

    public void encerrar(Integer numeroDaConta) {
        var account = searchAccountByNumber(numeroDaConta);
        if (account.possuiSaldo()) {
            throw new BusinessRuleException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        accounts.remove(account);
    }

    private Account searchAccountByNumber(Integer number) {
        return accounts
                .stream()
                .filter(c -> c.getNumero() == number)
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("Não existe conta cadastrada com esse número!"));
    }

    Account.CreditCard creditCard = (Account.CreditCard) account.getCreditCard();


    public BigDecimal consultarFaturaLimiteCartao(Integer numeroDaConta) {
        var account = searchAccountByNumber(numeroDaConta);
        return account.getCreditCardLimit();
    }

    public void realizarCompraNoCartaoDeCredito(Integer numeroDaConta, BigDecimal value) {
        var account = searchAccountByNumber(numeroDaConta);
        var creditCard = account.getCreditCard();

        if (creditCard == null) {
            throw new BusinessRuleException("Esta conta não possui um cartão de crédito associado.");
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Valor da compra deve ser superior a zero!");
        }

        if (value.compareTo(creditCard.getLimit()) > 0) {
            throw new BusinessRuleException("Compra recusada. Excede o limite de crédito.");
        }

        creditCard.purchase(value);
    }

    public Account getAccount() {
        return account;
    }
}