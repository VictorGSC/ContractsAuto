package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, int months) {
		if (months < 1 || months < 12) {
			throw new IllegalArgumentException("O número de parcelas deve ser pelo menos 1");
		}

		double quotaBase = contract.getTotalValue() / months;

		for (int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);

			double updated = quotaBase + onlinePaymentService.interest(quotaBase, i);
			double full = updated + onlinePaymentService.paymentFee(updated);

			contract.addInstallment(new Installment(dueDate, full));
		}
	}
}
