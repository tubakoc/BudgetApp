package com.example.budgetapp.ui.addoredit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgetapp.R
import com.example.budgetapp.common.viewBinding
import com.example.budgetapp.data.model.IncomeExpense
import com.example.budgetapp.databinding.FragmentAddorEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddorEditFragment : BottomSheetDialogFragment(R.layout.fragment_addor_edit) {

    private val binding by viewBinding(FragmentAddorEditBinding::bind)
    private val args by navArgs<AddorEditFragmentArgs>()
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore

        with(binding)
        {
           args.incomeExpense?.let {
               etTitle.setText(it.title)
               etDesc.setText(it.desc)
               etPrice.setText(it.price.toString())
               btnSave.visibility = View.VISIBLE
               btnAdd.visibility = View.GONE
           }

            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()
                val price = etPrice.text.toString()
                val rbExpense = rbExpense.isChecked

                if (title.isNotEmpty() && desc.isNotEmpty() && price.isNotEmpty()) {
                    addBudget(title, desc, price.toDouble(), rbExpense)
                } else {
                    Toast.makeText(requireContext(), "CANNOT ADD", Toast.LENGTH_SHORT).show()
                }
            }

            btnSave.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()
                val price = etPrice.text.toString()

                val rbExpense = rbExpense.isChecked
                if (title.isNotEmpty() && desc.isNotEmpty() && price.isNotEmpty()) {
                    saveBudget((args.incomeExpense?.docId ?: "") as String, title, price.toDouble(), desc, rbExpense)
                } else {
                    Toast.makeText(requireContext(), "CANNOT ADD", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun saveBudget(docId : String,title: String, price: Double, desc: String, rbExpense: Boolean) {

        db.collection("budget").document(docId)
            .update(
                mapOf(
                    "title" to title,
                    "price" to price,
                    "desc" to desc,
                    "incomeExpenseType" to rbExpense
                )
            )
            .addOnSuccessListener {
                findNavController().navigateUp()
            }
            .addOnFailureListener {
                //
            }
    }

    private fun addBudget(title: String, desc: String, price: Double, rbExpense: Boolean) {

        val budget = IncomeExpense(
            docId = null,
            title = title,
            price = price,
            desc = desc,
            incomeExpenseType = rbExpense
        )

        db.collection("budget").document(title).set(budget).addOnSuccessListener {
            findNavController().navigateUp()
        }.addOnFailureListener{

        }
    }


}