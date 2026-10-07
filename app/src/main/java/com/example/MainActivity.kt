package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.MunicipalBottomNav
import com.example.ui.components.MunicipalTopBar
import com.example.ui.components.NotificationsDialog
import com.example.ui.screens.AboutMunicipalityScreen
import com.example.ui.screens.AnnouncementDetailScreen
import com.example.ui.screens.AppealsScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HotlinesScreen
import com.example.ui.screens.MenuScreen
import com.example.ui.screens.NewsDetailScreen
import com.example.ui.screens.NewsScreen
import com.example.ui.screens.SocialMediaScreen
import com.example.ui.screens.TaxesAndPaymentsScreen
import com.example.ui.theme.MingachevirMunicipalityTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BottomNavTab
import com.example.viewmodel.MunicipalUiState
import com.example.viewmodel.MunicipalViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MunicipalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MingachevirMunicipalityTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                MunicipalAppContent(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun MunicipalAppContent(
    uiState: MunicipalUiState,
    viewModel: MunicipalViewModel
) {
    val canGoBack = uiState.screenStack.size > 1 || uiState.currentTab != BottomNavTab.HOME
    BackHandler(enabled = canGoBack) {
        viewModel.navigateBack()
    }

    if (uiState.showNotificationsDialog) {
        NotificationsDialog(
            notifications = uiState.notifications,
            onDismiss = { viewModel.toggleNotificationsDialog(false) }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold"),
        topBar = {
            if (uiState.currentScreen is AppScreen.Main) {
                MunicipalTopBar(
                    title = "Mingəçevir Bələdiyyəsi",
                    subtitle = "Azərbaycan Respublikası",
                    canNavigateBack = uiState.currentTab != BottomNavTab.HOME,
                    onNavigateBack = { viewModel.selectTab(BottomNavTab.HOME) },
                    unreadNotificationCount = uiState.notifications.size,
                    onNotificationsClick = { viewModel.toggleNotificationsDialog(true) }
                )
            }
        },
        bottomBar = {
            if (uiState.currentScreen is AppScreen.Main) {
                MunicipalBottomNav(
                    selectedTab = uiState.currentTab,
                    onTabSelected = { viewModel.selectTab(it) },
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = uiState.currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    is AppScreen.Main -> {
                        when (uiState.currentTab) {
                            BottomNavTab.HOME -> HomeScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.NEWS -> NewsScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.APPEALS -> AppealsScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.CONTACT -> ContactScreen()
                            BottomNavTab.MENU -> MenuScreen(viewModel = viewModel)
                        }
                    }
                    is AppScreen.NewsDetail -> {
                        val newsItem = uiState.newsList.firstOrNull { it.id == targetScreen.newsId }
                        NewsDetailScreen(
                            news = newsItem,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.AnnouncementDetail -> {
                        val announcement = uiState.announcementsList.firstOrNull { it.id == targetScreen.announcementId }
                        AnnouncementDetailScreen(
                            announcement = announcement,
                            onBack = { viewModel.navigateBack() },
                            onContactUs = {
                                viewModel.selectTab(BottomNavTab.CONTACT)
                            }
                        )
                    }
                    is AppScreen.NewAppealForm -> {
                        AppealsScreen(uiState = uiState, viewModel = viewModel)
                    }
                    is AppScreen.AppealDetail -> {
                        AppealsScreen(uiState = uiState, viewModel = viewModel)
                    }
                    is AppScreen.TaxesAndPayments -> {
                        TaxesAndPaymentsScreen(
                            uiState = uiState,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.AboutMunicipality -> {
                        AboutMunicipalityScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.Hotlines -> {
                        HotlinesScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.SocialMedia -> {
                        SocialMediaScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.Documents -> {
                        AboutMunicipalityScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                    is AppScreen.CivicSurveyScreen -> {
                        HomeScreen(uiState = uiState, viewModel = viewModel)
                    }
                    is AppScreen.AdminLogin -> {
                        com.example.ui.screens.admin.AdminLoginScreen(
                            onBack = { viewModel.navigateBack() },
                            onLoginSuccess = { email ->
                                viewModel.setAdminLoggedIn(true, email)
                                viewModel.navigateTo(AppScreen.AdminDashboard)
                            }
                        )
                    }
                    is AppScreen.AdminDashboard -> {
                        com.example.ui.screens.admin.AdminDashboardScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }
                }
            }
        }
    }
}
