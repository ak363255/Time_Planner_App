package com.example.impl.presentation.ui.home.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.entities.settings.CalendarButtonBehavior
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.views.TopAppBarButton
import com.example.presentation.ui.views.TopAppBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    calendarIconBehavior : CalendarButtonBehavior,
    onMenuIconClick : () ->Unit,
    onOverviewIconClick : () ->Unit,
    onOpenCalendar : () -> Unit,
    onGoToToday : () -> Unit
){
    TopAppBar(
        title = {
            TopAppBarTitle(
                text = HomeThemeRes.strings.topAppBarHomeTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            TopAppBarButton(
                modifier = Modifier.padding(end = 48.dp),
                imageVector = Icons.Default.Menu,
                imageDescription = HomeThemeRes.strings.topAppBarMenuIconDesc,
                onButtonclick = onMenuIconClick
            )
        },
        actions = {
            TopAppBarButton(
                imagePainter = painterResource(HomeThemeRes.icons.calendar),
                imageDescription = HomeThemeRes.strings.topAppBarCalendarIconDesc,
                onButtonclick = when(calendarIconBehavior){
                    CalendarButtonBehavior.OPEN_CALENDAR -> onOpenCalendar
                    CalendarButtonBehavior.SET_CURRENT_DATE -> onGoToToday
                },
                onLongButtonClick = when(calendarIconBehavior){
                    CalendarButtonBehavior.OPEN_CALENDAR -> onGoToToday
                    CalendarButtonBehavior.SET_CURRENT_DATE -> onOpenCalendar
                }
            )
            TopAppBarButton(
                imagePainter = painterResource(TimePlannerRes.icons.overview),
                imageDescription = HomeThemeRes.strings.topAppBarOverviewTitle,
                onButtonclick = onOverviewIconClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background

        )
    )

}