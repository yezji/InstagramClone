package com.green.instagramclone.src.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.green.instagramclone.databinding.DialogConfirmBinding

class DialogConfirm(context: Context) : Dialog(context){
    private lateinit var binding: DialogConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
    }

    override fun show() {
        if (!this.isShowing) super.show()
    }


    /*fun setDialog(strTitle: String, strDescription: String) {
        dlg.setContentView(R.layout.dialog_confirm)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setCancelable(false)

        dlg.window!!.setLayout((WindowManager.LayoutParams.MATCH_PARENT*0.8).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)

        title = dlg.findViewById(R.id.tv_title)
        title.text = strTitle

        desc = dlg.findViewById(R.id.tv_description)
        desc.text = strDescription

        btnConfitm = dlg.findViewById(R.id.btn_confirm)
        btnConfitm.setOnClickListener {
            // TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드
//            listener.onConfirmClicked()
            dlg.dismiss()
        }
    }*/



}