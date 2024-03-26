import account.AccountService;
import account.BusinessRuleException;
import account.OpenAccountData;
import customer.CustomerApplicationData;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    private static AccountService service = new AccountService();
    private static Scanner sc = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var option = exibirMenu();
        while (option != 9) {
            try {
                switch (option) {
                    case 1:
                        listarContas();
                        break;
                    case 2:
                        abrirConta();
                        break;
                    case 3:
                        encerrarConta();
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        realizarSaque();
                        break;
                    case 6:
                        realizarDeposito();
                        break;
                    case 7:
                        consultarFaturaLimiteCartao();
                        break;
                    case 8:
                        realizarCompraNoCartaoDeCredito();
                        break;
                }
            } catch (BusinessRuleException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println("Pressione ENTER para continuar...");
            sc.nextLine();
            option = exibirMenu();
        }

        System.out.println("Finalizando a aplicação.");
    }

    private static void realizarCompraNoCartaoDeCredito() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();

        System.out.println("Digite o valor da compra:");
        var valorCompra = sc.nextBigDecimal();

        BigDecimal limiteCartao = service.consultarFaturaLimiteCartao(numeroDaConta);
        BigDecimal saldo = service.consultarSaldo(numeroDaConta);

        if (valorCompra.compareTo(limiteCartao) <= 0) {
            service.realizarCompraNoCartaoDeCredito(numeroDaConta, valorCompra);
            saldo = service.consultarSaldo(numeroDaConta); // Atualize o saldo após a compra
            System.out.println("Compra realizada com sucesso! Seu saldo atual é de " + saldo + " e seu limite é de " + limiteCartao);
        } else {
            System.out.println("Compra recusada. Valor da compra excede o limite do cartão de crédito.");
        }
    }


    private static void consultarFaturaLimiteCartao() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();
        var limiteCartao = service.consultarFaturaLimiteCartao(numeroDaConta);
        System.out.println("Limite do cartão de crédito: " + limiteCartao);
        sc.next();
    }

    private static int exibirMenu() {
        System.out.println("""
                FINANCIALSYSTEM - ESCOLHA UMA OPÇÃO:
                1 - Listar contas abertas
                2 - Abertura de conta
                3 - Encerramento de conta
                4 - Consultar saldo de uma conta
                5 - Realizar saque em uma conta
                6 - Realizar depósito em uma conta
                7 - Consultar Limite do Cartão de Crédito da Conta
                8 - Realizar Compra no Cartão de Crédito
                9 - Sair
                """);
        return sc.nextInt();
    }

    private static void listarContas() {
        System.out.println("Contas cadastradas:");
        var accounts = service.listarContasAbertas();
        accounts.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }

    private static void abrirConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();

        System.out.println("Digite o nome do cliente:");
        var name = sc.next();

        System.out.println("Digite o cpf do cliente:");
        var cpf = sc.next();

        System.out.println("Digite o email do cliente:");
        var email = sc.next();

        service.abrir(new OpenAccountData(numeroDaConta, new CustomerApplicationData(name, cpf, email)));

        System.out.println("Conta aberta com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }

    private static void encerrarConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();

        service.encerrar(numeroDaConta);

        System.out.println("Conta encerrada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }

    private static void consultarSaldo() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();
        var saldo = service.consultarSaldo(numeroDaConta);
        System.out.println("Saldo da conta: " + saldo);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }

    private static void realizarSaque() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();

        System.out.println("Digite o valor do saque:");
        var valor = sc.nextBigDecimal();

        service.realizarSaque(numeroDaConta, valor);
        System.out.println("Saque realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }

    private static void realizarDeposito() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = sc.nextInt();

        System.out.println("Digite o valor do depósito:");
        var valor = sc.nextBigDecimal();

        service.realizarDeposito(numeroDaConta, valor);

        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        sc.next();
    }
}
