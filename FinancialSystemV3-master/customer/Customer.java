package customer;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {

    private String name;
    private String cpf;
    private String email;

    public Customer (CustomerApplicationData data) {
        this.name = data.name();
        this.cpf = data.cpf();
        this.email = data.email();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return cpf.equals(customer.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getNome() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

}

