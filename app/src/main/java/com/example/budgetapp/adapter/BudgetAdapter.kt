package com.example.budgetapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.budgetapp.data.model.Budget
import com.example.budgetapp.data.model.IncomeExpense
import com.example.budgetapp.databinding.RvcBudgetBinding

class BudgetAdapter (
    private val onBudgetClick: (IncomeExpense) -> Unit,
    private val onDeleteClick: (String) -> Unit)
    :ListAdapter<IncomeExpense,BudgetViewHolder>(BudgetDiffCallBack())

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder  =
        BudgetViewHolder(
            RvcBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onBudgetClick,
            onDeleteClick
        )

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) = holder.bind(getItem(position))
}
