package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import services.ContractService;
import services.PaypalService;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            System.out.println("=== Entre os dados do contrato ===");
            
            System.out.print("Número: ");
            int number = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Data (dd/MM/yyyy): ");
            String dateStr = sc.nextLine();
            LocalDate date = LocalDate.parse(dateStr, fmt);
            
            System.out.print("Valor do contrato: ");
            double totalValue = sc.nextDouble();

            System.out.print("Entre com o número de parcelas: ");
            int months = sc.nextInt();

            
            Contract contract = new Contract(number, date, totalValue);

            
            ContractService contractService = new ContractService(new PaypalService());
            contractService.processContract(contract, months);

            System.out.println("\n=== Parcelas ===");
            for (Installment installment : contract.getInstallments()) {
                System.out.println(installment);
            }

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data do contrato inválida. Por favor, use o formato dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            
            System.out.println("Erro: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
