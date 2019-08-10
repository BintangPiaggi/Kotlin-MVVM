package com.bintang.app.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText

class SaveDialogFragment : DialogFragment(){
    interface Callback{
        fun onSaveTip(name:String)
    }

    private var saveTipCallback: SaveDialogFragment.Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val saveDialog = context?.let {ctx ->
            val edt = EditText(ctx)
            edt.id = viewId
            edt.hint = "Enter Location"

            AlertDialog.Builder(ctx)
                .setView(edt)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", {_,_ ->onSave (edt)})
                .create()
        }

        return saveDialog!!
    }

    private fun onSave(editText: EditText){
        val text = editText.text.toString()
        if (text.isNotEmpty()){
            saveTipCallback?.onSaveTip(text)
        }
    }

    companion object{
        val viewId = View.generateViewId()
    }
}