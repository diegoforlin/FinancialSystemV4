package account;

import customer.Customer;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private Integer number;
    private BigDecimal saldo;
    private Customer mainCustomer;
    private CreditCard creditCard;

    public Account(Integer numero, Customer titular, BigDecimal limit) {
        this.number = numero;
        this.mainCustomer = titular;
        this.saldo = BigDecimal.ZERO;
        this.creditCard = new CreditCard(limit);
    }
    public Account(Integer numeroDaConta, Customer customer) {
    }

    public Account(Integer numeroDaConta) {
    }

    public boolean possuiSaldo() {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number.equals(account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero='" + number + '\'' +
                ", saldo=" + saldo +
                ", titular=" + mainCustomer +
                '}';
    }

    public Integer getNumero() {
        return number;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Customer getMainCustomer() {
        return mainCustomer;
    }

    public BigDecimal getLimit() {
        return creditCard.getLimit();
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public BigDecimal getCreditCardLimit() {
        return getLimit();
    }

    public class CreditCard {
        private BigDecimal limit;
        private BigDecimal saldo;

        public CreditCard(BigDecimal limit) {
            this.limit = limit;
            this.saldo = BigDecimal.ZERO;
        }

        public BigDecimal getLimit() {
            return limit;
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public void purchase(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Valor da compra deve ser superior a zero!");
            }

            if (saldo.add(amount).compareTo(limit) <= 0) {
                saldo = saldo.add(amount);
                System.out.println("Compra efetuada com sucesso. Saldo atual: " + saldo + " e limite é " + limit);
            } else {
                throw new BusinessRuleException("Compra recusada. Excede o limite de crédito.");
            }
        }
    }
}
