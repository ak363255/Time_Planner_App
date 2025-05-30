package com.example.presentation.ui.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf


enum class TimePlannerLanguage(val code: String) {
    EN("en"),
    RU("en"),
    DE("en"),
    ES("en"),
    FA("en"),
    FR("en"),
    PT_BR("en"),
    TR("en"),
    VN("en"),
    PL("en"),
    IT("en"),
}

enum class LanguageUiType(val code: String?) {
    DEFAULT(null),
    EN("en"),
    RU("en"),
    DE("en"),
    ES("en"),
    FA("en"),
    FR("en"),
    PT_BR("en"),
    TR("en"),
    VN("en"),
    PL("en"),
    IT("en"),
}
val LocalTimePlannerLanguage = staticCompositionLocalOf<TimePlannerLanguage> {
    error("Language is not provided")
}

fun fetchCoreLanguage(code: String):TimePlannerLanguage{
    return TimePlannerLanguage.entries.find { it.code == code }?: TimePlannerLanguage.EN
}

fun fetchAppLanguage(languageUiType: LanguageUiType) = when(languageUiType){
    LanguageUiType.DEFAULT -> fetchCoreLanguage(androidx.compose.ui.text.intl.Locale.current.language)
    LanguageUiType.EN -> TimePlannerLanguage.EN
    LanguageUiType.RU -> TimePlannerLanguage.RU
    LanguageUiType.DE -> TimePlannerLanguage.DE
    LanguageUiType.ES -> TimePlannerLanguage.ES
    LanguageUiType.FA ->TimePlannerLanguage.FA
    LanguageUiType.FR -> TimePlannerLanguage.FR
    LanguageUiType.PT_BR ->TimePlannerLanguage.PT_BR
    LanguageUiType.TR -> TimePlannerLanguage.TR
    LanguageUiType.VN ->TimePlannerLanguage.VN
    LanguageUiType.PL ->TimePlannerLanguage.PL
    LanguageUiType.IT ->TimePlannerLanguage.IT
}