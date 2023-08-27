package com.example.budgetapp.ui.expense

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.budgetapp.R
import com.example.budgetapp.adapter.BudgetAdapter
import com.example.budgetapp.common.viewBinding
import com.example.budgetapp.data.model.IncomeExpense
import com.example.budgetapp.databinding.FragmentExpenseBinding
import com.example.budgetapp.ui.income.IncomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    private val binding by viewBinding ( FragmentExpenseBinding::bind)
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val expenseAdapter by lazy { BudgetAdapter(::onBudgetClick, ::onDeleteClick) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        db = Firebase.firestore

        with(binding)
        {
            recyclerView.adapter = expenseAdapter
        }

        listenExpenseBudget()
    }

    private fun listenExpenseBudget() {
        val docRef = db.collection("users").document(auth.currentUser!!.email.toString())
            .collection("income_expense").whereEqualTo("type", false)

        docRef.addSnapshotListener { snapshot, error ->
            val summaryList = arrayListOf<IncomeExpense>()

            var totalIncomes = 0.0

            snapshot?.forEach { document ->
                summaryList.add(
                    IncomeExpense(
                        document.id,
                        document.get("title") as String,
                        (document.get("price") as Number).toDouble(),
                        document.get("desc") as String,
                        document.get("incomeExpenseType") as Boolean?
                    )
                )
                totalIncomes += (document.get("price") as Number).toDouble()
            }

            binding.total.setText("Total: +${totalIncomes}")
            binding.total.setTextColor(Color.rgb(50,205,50))

            expenseAdapter.submitList(summaryList)
        }
    }

    private fun deleteBudget(docId: String) {
        db.collection("budget").document(docId)
            .delete()
            .addOnSuccessListener {
                //
            }
            .addOnFailureListener {
                //
            }
    }


    private fun onBudgetClick(incomeExpense : IncomeExpense)
    {
        val action = ExpenseFragmentDirections.expenseToAddorEdit().setIncomeExpense(incomeExpense)
        findNavController().navigate(action)


    }

    private fun onDeleteClick(docId: String) {
        deleteBudget(docId)
    }

}