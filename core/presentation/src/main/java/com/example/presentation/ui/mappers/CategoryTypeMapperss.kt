package com.example.presentation.ui.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.domain.entities.categories.DefaultCategoryType
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.theme.tokens.TimePlannerIcons
import com.example.presentation.ui.theme.tokens.TimePlannerStrings

fun DefaultCategoryType.mapToIcon(icons: TimePlannerIcons) : Int = when(this){
    DefaultCategoryType.WORK -> icons.categoryWorkIcon
    DefaultCategoryType.REST -> icons.categoryRestIcon
    DefaultCategoryType.AFFAIRS -> icons.categoryAffairsIcon
    DefaultCategoryType.TRANSPORT -> icons.categoryTransportIcon
    DefaultCategoryType.STUDY -> icons.categoryStudyIcon
    DefaultCategoryType.EAT -> icons.categoryEatIcon
    DefaultCategoryType.ENTERTAINMENTS -> icons.categoryEntertainmentsIcon
    DefaultCategoryType.SPORT -> icons.categorySportIcon
    DefaultCategoryType.SLEEP -> icons.categorySleepIcon
    DefaultCategoryType.CULTURE -> icons.categoryCultureIcon
    DefaultCategoryType.OTHER -> icons.categoryOtherIcon
    DefaultCategoryType.EMPTY -> icons.categoryEmptyIcon
    DefaultCategoryType.HYGIENE -> icons.categoryHygiene
    DefaultCategoryType.HEALTH -> icons.categoryHealth
    DefaultCategoryType.SHOPPING -> icons.categoryShopping
}

fun DefaultCategoryType.mapToString(strings: TimePlannerStrings): String = when (this) {
    DefaultCategoryType.WORK -> strings.categoryWorkTitle
    DefaultCategoryType.REST -> strings.categoryRestTitle
    DefaultCategoryType.AFFAIRS -> strings.categoryChoresTitle
    DefaultCategoryType.TRANSPORT -> strings.categoryTransportTitle
    DefaultCategoryType.STUDY -> strings.categoryStudyTitle
    DefaultCategoryType.EAT -> strings.categoryEatTitle
    DefaultCategoryType.ENTERTAINMENTS -> strings.categoryEntertainmentsTitle
    DefaultCategoryType.SPORT -> strings.categorySportTitle
    DefaultCategoryType.SLEEP -> strings.categorySleepTitle
    DefaultCategoryType.CULTURE -> strings.categoryCultureTitle
    DefaultCategoryType.OTHER -> strings.categoryOtherTitle
    DefaultCategoryType.EMPTY -> strings.categoryEmptyTitle
    DefaultCategoryType.HYGIENE -> strings.categoryHygieneTitle
    DefaultCategoryType.HEALTH -> strings.categoryHealthTitle
    DefaultCategoryType.SHOPPING -> strings.categoryShoppingTitle
}

@Composable
fun DefaultCategoryType.mapToName() = mapToString(TimePlannerRes.strings)

@Composable
fun DefaultCategoryType.mapToIconPainter() = painterResource(id = mapToIcon(TimePlannerRes.icons))
