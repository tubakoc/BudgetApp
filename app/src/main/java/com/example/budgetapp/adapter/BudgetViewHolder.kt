package com.example.budgetapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.data.model.Budget
import com.example.budgetapp.data.model.IncomeExpense
import com.example.budgetapp.databinding.RvcBudgetBinding


class BudgetViewHolder (private val binding:RvcBudgetBinding,
                        private val onBudgetClick: (IncomeExpense) -> Unit,
                        private val onDeleteClick: (String) -> Unit)
    : RecyclerView.ViewHolder(binding.root)
{
    @SuppressLint("SetTextI18n")
        fun bind(incomeExpense: IncomeExpense) = with(binding){

        val type = incomeExpense.incomeExpenseType
        tvTitle.text= incomeExpense.title
        tvDesc.text = incomeExpense.desc


        if (type == true) {
            tvPrice.text = "+${incomeExpense.price} ₺"
            tvPrice.setTextColor(Color.rgb(50,205,50))
        }

        else {
            tvPrice.text = "-${incomeExpense.price} ₺"
            tvPrice.setTextColor(Color.RED)
        }

        root.setOnClickListener {
            onBudgetClick(incomeExpense)
        }

        ivDelete.setOnClickListener {
            incomeExpense.docId?.let(onDeleteClick)
        }

    }
}

