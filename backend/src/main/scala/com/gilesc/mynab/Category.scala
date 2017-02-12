package com.gilesc.mynab
package category

import com.gilesc.mynab.transaction.Transaction
import com.gilesc.mynab.account._

case class MajorCategory(value: String) extends AnyVal
case class MinorCategory(value: String) extends AnyVal
case class Category(major: MajorCategory, minor: MinorCategory)

trait CategoryModule {
  def renameCategory(before: Category, after: Category, accts: AccountGroup): AccountGroup
}

object Category extends CategoryModule {
  def renameCategory(before: Category, after: Category, accts: AccountGroup): AccountGroup = {
    def rename(transactions: List[Transaction]): List[Transaction] = {
      transactions.map { t =>
        if (t.category == before) t.copy(category = after) else t
      }
    }

    // loop through each account in the account group
    // for each account, map over the transaction list
    // foreach transaction in the transaction list
    //    if we need to rename the category, rename it
    val newAccounts = accts.accounts map {
      case BankingAccount(name, transactions) => BankingAccount(name, rename(transactions))
      case LoanAccount(name, transactions) => LoanAccount(name, rename(transactions))
      case InvestmentAccount(name, transactions) => InvestmentAccount(name, rename(transactions))
      case RetirementAccount(name, transactions) => RetirementAccount(name, rename(transactions))
    }

    accts.copy(accounts = newAccounts)
  }
}