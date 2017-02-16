package com.gilesc.mynab.account

import com.gilesc.commons.{Prepending, Removing}
import com.gilesc.mynab.transaction.Transaction

case class AccountName(value: String) extends AnyVal

sealed trait AccountType
case object Banking extends AccountType
case object Loan extends AccountType
case object Investment extends AccountType
case object Retirement extends AccountType

trait Account {
  def name: AccountName
  def accountType: AccountType
  def transactions: Vector[Transaction]

  def copy(name: AccountName, transactions: Vector[Transaction]) = this match {
    case BankingAccount(_, _) => BankingAccount(name, transactions)
    case LoanAccount(_, _) => LoanAccount(name, transactions)
    case InvestmentAccount(_, _) => InvestmentAccount(name, transactions)
    case RetirementAccount(_, _) => RetirementAccount(name, transactions)
  }
}

case class BankingAccount(name: AccountName, transactions: Vector[Transaction])
  extends Account { val accountType = Banking }

case class LoanAccount(name: AccountName, transactions: Vector[Transaction])
  extends Account { val accountType = Loan }

case class InvestmentAccount(name: AccountName, transactions: Vector[Transaction])
  extends Account { val accountType = Investment }

case class RetirementAccount(name: AccountName, transactions: Vector[Transaction])
  extends Account { val accountType = Retirement }

object Account extends AccountModule with Prepending with Removing {
  def apply(account: AccountType, name: String, transactions: Vector[Transaction]): Account = {
    account match {
      case Banking => BankingAccount(AccountName(name), transactions)
      case Loan => LoanAccount(AccountName(name), transactions)
      case Investment => InvestmentAccount(AccountName(name), transactions)
      case Retirement => RetirementAccount(AccountName(name), transactions)
    }
  }
}