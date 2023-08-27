package com.example.budgetapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.budgetapp.data.model.Budget
import com.example.budgetapp.data.model.IncomeExpense

class BudgetDiffCallBack : DiffUtil.ItemCallback<IncomeExpense>() {
    override fun areItemsTheSame(oldItem: IncomeExpense, newItem: IncomeExpense): Boolean {
        return oldItem.docId == newItem.docId
    }

    override fun areContentsTheSame(oldItem: IncomeExpense, newItem: IncomeExpense): Boolean {
        return oldItem == newItem
    }

}