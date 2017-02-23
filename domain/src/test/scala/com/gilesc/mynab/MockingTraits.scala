package com.gilesc.mynab

import java.time.LocalDate

import com.gilesc.mynab.account._
import com.gilesc.mynab.category._
import com.gilesc.mynab.transaction._

trait TestCaseHelpers {
  implicit def str2accountName(value: String): AccountName = {
    AccountName(value).toOption.getOrElse(AccountName("Random Account").toOption.get)
  }

}

trait MockAccountCreation { self: TestCaseHelpers =>
  def banking(name: String, transactions: Vector[Transaction]): BankingAccount =
    BankingAccount(name, transactions)
  def loan(name: String, transactions: Vector[Transaction]): LoanAccount =
    LoanAccount(name, transactions)
}

trait MockTransactionCreation { self: TestCaseHelpers =>
  def t(withdrawal: Double, deposit: Double): Transaction = {
    Transaction(LocalDate.now(),
      Payee("Me"),
      Category.apply(MajorCategory("loans"), MinorCategory("student loan")),
      Memo(""),
      Amount(BigDecimal(withdrawal)),
      Amount(BigDecimal(deposit)),
      Cleared(false))
  }

  def trans(date: LocalDate = LocalDate.now(),
    payee: String = "Me",
    majorCategory: String = "loans",
    minorCategory: String = "studen",
    memo: String = "loans",
    withdrawal: Double = 0.0,
    deposit: Double = 0.0,
    cleared: Boolean = false): Transaction = {

    Transaction(date,
      Payee(payee),
      Category.apply(MajorCategory(majorCategory), MinorCategory(minorCategory)),
      Memo(memo),
      Amount(BigDecimal(withdrawal)),
      Amount(BigDecimal(deposit)),
      Cleared(cleared))
  }
}

