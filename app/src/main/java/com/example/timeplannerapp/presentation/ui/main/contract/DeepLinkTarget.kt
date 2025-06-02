package com.example.timeplannerapp.presentation.ui.main.contract

import android.content.Intent
import com.example.utils.functional.Constants.App.EDITOR_DEEP_LINK

enum class DeepLinkTarget{
    MAIN,EDITOR;

    companion object{
        fun byIntent(intent: Intent) = when{
            intent.action == Intent.ACTION_VIEW && intent.dataString == EDITOR_DEEP_LINK -> EDITOR
            else -> MAIN
        }
    }
}