package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BottomNavTab
import com.example.viewmodel.MunicipalViewModel

data class MenuItemSpec(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val testTag: String
)

@Composable
fun MenuScreen(
    viewModel: MunicipalViewModel,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        MenuItemSpec(
            title = "Bələdiyyə haqqında",
            subtitle = "Rəhbərlik, struktur, tarix və vəzifələr",
            icon = Icons.Default.AccountBalance,
            onClick = { viewModel.navigateTo(AppScreen.AboutMunicipality) },
            testTag = "menu_item_about"
        ),
        MenuItemSpec(
            title = "Vergi və ödənişlər",
            subtitle = "Əmlak/torpaq vergisi hesablama və HÖP",
            icon = Icons.Default.Calculate,
            onClick = { viewModel.navigateTo(AppScreen.TaxesAndPayments) },
            testTag = "menu_item_taxes"
        ),
        MenuItemSpec(
            title = "Qaynar xətlər",
            subtitle = "Fövqəladə hallar və şəhər xidmətləri nömrələri",
            icon = Icons.Default.Phone,
            onClick = { viewModel.navigateTo(AppScreen.Hotlines) },
            testTag = "menu_item_hotlines"
        ),
        MenuItemSpec(
            title = "Sosial media",
            subtitle = "Rəsmi Facebook, Instagram, Telegram səhifələri",
            icon = Icons.Default.Share,
            onClick = { viewModel.navigateTo(AppScreen.SocialMedia) },
            testTag = "menu_item_social"
        ),
        MenuItemSpec(
            title = "Şəhər Rəy Sorğusu",
            subtitle = "Bələdiyyə qərarlarında sakinlərin səsi",
            icon = Icons.Default.Poll,
            onClick = { viewModel.selectTab(BottomNavTab.HOME) },
            testTag = "menu_item_poll"
        ),
        MenuItemSpec(
            title = "Rəsmi elan və hərraclar",
            subtitle = "Bələdiyyə qərarları və müsabiqə elanları",
            icon = Icons.Default.Campaign,
            onClick = {
                viewModel.selectTab(BottomNavTab.NEWS)
                viewModel.setSelectedNewsTab(1)
            },
            testTag = "menu_item_announcements"
        ),
        MenuItemSpec(
            title = "Elektron müraciət və izləmə",
            subtitle = "Şikayət və təklif göndər, statusu yoxla",
            icon = Icons.Default.Edit,
            onClick = { viewModel.selectTab(BottomNavTab.APPEALS) },
            testTag = "menu_item_appeals"
        ),
        MenuItemSpec(
            title = "Əlaqə və qəbul saatları",
            subtitle = "Ünvan, xəritə, telefonlar və qəbul cədvəli",
            icon = Icons.Default.ContactPhone,
            onClick = { viewModel.selectTab(BottomNavTab.CONTACT) },
            testTag = "menu_item_contact"
        ),
        MenuItemSpec(
            title = "İnzibatçı Paneli (Admin Girişi)",
            subtitle = "Dinamik məzmun, xəbərlər, qaynar xətlər və idarəetmə",
            icon = Icons.Default.AdminPanelSettings,
            onClick = {
                if (viewModel.uiState.value.isAdminLoggedIn) {
                    viewModel.navigateTo(AppScreen.AdminDashboard)
                } else {
                    viewModel.navigateTo(AppScreen.AdminLogin)
                }
            },
            testTag = "menu_item_admin"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("menu_screen")
    ) {
        // Municipal App Info Header
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_mingachevir_logo),
                        contentDescription = "Mingəçevir Bələdiyyəsi",
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Mingəçevir Bələdiyyəsi",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Rəsmi Mobil Tətbiq • Versiya 1.0.0",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = "Azərbaycan Respublikası Yerli Özünüidarəetmə",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "BÜTÜN BÖLMƏLƏR VƏ XİDMƏTLƏR",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.1.sp
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                items.forEachIndexed { index, item ->
                    Card(
                        onClick = item.onClick,
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(item.testTag)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    if (index < items.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "© 2026 Mingəçevir Bələdiyyəsi",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Bütün hüquqlar qorunur • www.mingecevir-belediyyesi.gov.az",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
