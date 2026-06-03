package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.platform.LocalContext
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Category
import com.example.data.Guide
import com.example.data.SurvivalData
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurvivalBorder
import com.example.ui.theme.SurvivalCardBg
import com.example.ui.theme.SurvivalPrimary
import com.example.ui.theme.SurvivalTextSecondary

// Sealed class representing different destinations inside the Guides tab navigation stack
sealed interface GuideStackScreen {
    object Home : GuideStackScreen
    data class CategoryDetail(val categoryId: String) : GuideStackScreen
    data class GuideDetail(val categoryId: String?, val guideId: String) : GuideStackScreen
}

enum class Tab {
    GUIDES,
    KIT,
    CONTACTS,
    ABOUT
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContainer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer() {
    val context = LocalContext.current
    var selectedTab by rememberSaveable { mutableStateOf(Tab.GUIDES) }
    
    // Stack-based state navigation for the GUIDES tab to ensure full offline robustness and back stack flow
    val guidesBackStack = remember { mutableStateListOf<GuideStackScreen>(GuideStackScreen.Home) }
    
    // Global query state to persist if navigating to a guide from search and then clicking back
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // Persistent state map of survival kit item checkboxes backed by local SharedPreferences
    val sharedPreferences = remember { context.getSharedPreferences("survival_kit_prefs_v2", Context.MODE_PRIVATE) }
    val checkedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            SurvivalData.kitItems.forEach { item ->
                this[item.id] = sharedPreferences.getBoolean(item.id, false)
            }
        }
    }

    fun toggleKitItem(itemId: String) {
        val current = checkedStates[itemId] ?: false
        checkedStates[itemId] = !current
        sharedPreferences.edit().putBoolean(itemId, !current).apply()
    }

    // Unified Back Button interceptor
    BackHandler {
        if (selectedTab != Tab.GUIDES) {
            selectedTab = Tab.GUIDES
        } else if (guidesBackStack.size > 1) {
            guidesBackStack.removeAt(guidesBackStack.size - 1)
        } else {
            // Root of Guides tab and main screen, let the application close.
            (context as? ComponentActivity)?.finish()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .testTag("app_bottom_bar")
                    .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == Tab.GUIDES,
                    modifier = Modifier.testTag("nav_tab_guides"),
                    onClick = { selectedTab = Tab.GUIDES },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == Tab.GUIDES) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Guías de Supervivencia"
                        )
                    },
                    label = { Text("Guías") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == Tab.KIT,
                    modifier = Modifier.testTag("nav_tab_kit"),
                    onClick = { selectedTab = Tab.KIT },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == Tab.KIT) Icons.Filled.FormatListBulleted else Icons.Outlined.FormatListBulleted,
                            contentDescription = "Kit de Supervivencia"
                        )
                    },
                    label = { Text("Mi Kit") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                
                NavigationBarItem(
                    selected = selectedTab == Tab.CONTACTS,
                    modifier = Modifier.testTag("nav_tab_contacts"),
                    onClick = { selectedTab = Tab.CONTACTS },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == Tab.CONTACTS) Icons.Filled.PhoneInTalk else Icons.Outlined.PhoneInTalk,
                            contentDescription = "Contactos de Emergencia"
                        )
                    },
                    label = { Text("Emergencias" ) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                
                NavigationBarItem(
                    selected = selectedTab == Tab.ABOUT,
                    modifier = Modifier.testTag("nav_tab_about"),
                    onClick = { selectedTab = Tab.ABOUT },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == Tab.ABOUT) Icons.Filled.Info else Icons.Outlined.Info,
                            contentDescription = "Acerca de la App"
                        )
                    },
                    label = { Text("Acerca de") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Apply fluid constraint for tablets/foldables so content remains super clean and centralized
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
                    .align(Alignment.TopCenter)
            ) {
                when (selectedTab) {
                    Tab.GUIDES -> {
                        when (val currentScreen = guidesBackStack.last()) {
                            GuideStackScreen.Home -> {
                                HomeScreen(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it },
                                    onCategoryClick = { catId ->
                                        guidesBackStack.add(GuideStackScreen.CategoryDetail(catId))
                                    },
                                    onGuideClick = { catId, guideId ->
                                        guidesBackStack.add(GuideStackScreen.GuideDetail(catId, guideId))
                                    },
                                    onKitClick = {
                                        selectedTab = Tab.KIT
                                    }
                                )
                            }
                            is GuideStackScreen.CategoryDetail -> {
                                CategoryDetailScreen(
                                    categoryId = currentScreen.categoryId,
                                    onBackClick = { guidesBackStack.removeLast() },
                                    onGuideClick = { guideId ->
                                        guidesBackStack.add(GuideStackScreen.GuideDetail(currentScreen.categoryId, guideId))
                                    }
                                )
                            }
                            is GuideStackScreen.GuideDetail -> {
                                GuideDetailScreen(
                                    categoryId = currentScreen.categoryId,
                                    guideId = currentScreen.guideId,
                                    onBackClick = { guidesBackStack.removeLast() }
                                )
                            }
                        }
                    }
                    Tab.KIT -> {
                        KitScreen(
                            checkedStates = checkedStates,
                            onToggleItem = { toggleKitItem(it) }
                        )
                    }
                    Tab.CONTACTS -> {
                        EmergencyContactsScreen()
                    }
                    Tab.ABOUT -> {
                        AboutScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onGuideClick: (String?, String) -> Unit,
    onKitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // App Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = "Shield Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(42.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "SurviVal",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Guía de supervivencia offline — v2.0",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = SurvivalTextSecondary
                )
            }
        }

        // Orange emergency banner at top
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "📶",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Funciona 100% sin internet",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Search Bar with explicit test tag
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("search_bar_input"),
            placeholder = { 
                Text(
                    "Buscar guías y procedimientos v2.0...", 
                    color = SurvivalTextSecondary,
                    fontSize = 16.sp
                ) 
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { onQueryChange("") },
                        modifier = Modifier.testTag("search_clear_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear text",
                            tint = SurvivalTextSecondary
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = SurvivalCardBg,
                unfocusedContainerColor = SurvivalCardBg,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = SurvivalBorder,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (query.isNotBlank()) {
            // Search Mode
            val searchResults = remember(query) { SurvivalData.searchGuides(query) }
            
            Text(
                text = "Resultados de búsqueda (${searchResults.size})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            if (searchResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SurvivalBorder, RoundedCornerShape(12.dp))
                            .background(SurvivalCardBg)
                            .padding(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "Not found",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Sin resultados",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "No se encontraron guías para \"$query\". Buscá términos más generales como \"agua\", \"fuego\", \"batería\", \"RCP\" o \"vendas\".",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SurvivalTextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(searchResults) { (category, guide) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onGuideClick(category.id, guide.id) }
                                .testTag("search_result_item_${guide.id}"),
                            colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                            border = BorderStroke(1.dp, SurvivalBorder),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Category pill
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                            RoundedCornerShape(50.dp)
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${category.emoji} ${category.name}",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(10.dp))
                                
                                Text(
                                    text = guide.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                
                                Spacer(modifier = Modifier.height(4.dp))
                                
                                Text(
                                    text = guide.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = SurvivalTextSecondary,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Default Category Grid - Displays 8 cards total (7 categories + 1 custom Kit de Supervivencia card)
            Text(
                text = "Manuales y Kit de Emergencia",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Render the 7 standard content categories
                items(SurvivalData.categories) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                            .clickable { onCategoryClick(category.id) }
                            .testTag("category_card_${category.id}"),
                        colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                        border = BorderStroke(1.dp, SurvivalBorder),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = category.emoji,
                                    fontSize = 20.sp
                                )
                            }

                            Column {
                                Text(
                                    text = category.name.substringAfter(" ").trim(),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 20.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${category.guides.size} guías",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SurvivalTextSecondary
                                )
                            }
                        }
                    }
                }

                // Render the 8th card (interactive Kit de Supervivencia) which links directly to the Tab.KIT Checklist !
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                            .clickable { onKitClick() }
                            .testTag("category_card_kit"),
                        colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                        border = BorderStroke(1.dp, SurvivalBorder),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🧰",
                                    fontSize = 20.sp
                                )
                            }

                            Column {
                                Text(
                                    text = "Kit de Supervivencia",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 20.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Checklist e Insumos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SurvivalTextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    categoryId: String,
    onBackClick: () -> Unit,
    onGuideClick: (String) -> Unit
) {
    val category = remember(categoryId) {
        SurvivalData.categories.find { it.id == categoryId }
    }

    if (category == null) {
        onBackClick()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Toolbar with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("back_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = category.emoji + " " + category.name.substringAfter(" ").trim(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Guías complementarias offline",
                    style = MaterialTheme.typography.bodySmall,
                    color = SurvivalTextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(category.guides) { guide ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onGuideClick(guide.id) }
                        .testTag("guide_item_${guide.id}"),
                    colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                    border = BorderStroke(1.dp, SurvivalBorder),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                    text = guide.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 22.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = guide.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = SurvivalTextSecondary,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            
                            // Difficulty Badge
                            val difficultyColor = when {
                                guide.difficulty.contains("Fácil") -> Color(0xFF30D158)
                                guide.difficulty.contains("Moderado") -> Color(0xFFFF9F0A)
                                else -> Color(0xFFFF453A)
                            }
                            
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = guide.difficulty,
                                    color = difficultyColor,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                                )
                                if (guide.time != null) {
                                    Text(
                                        text = "•  ${guide.time}",
                                        color = SurvivalTextSecondary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Abrir guía",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GuideDetailScreen(
    categoryId: String?,
    guideId: String,
    onBackClick: () -> Unit
) {
    val result = remember(categoryId, guideId) {
        val cat = SurvivalData.categories.find { it.id == categoryId }
        val g = cat?.guides?.find { it.id == guideId }
            ?: SurvivalData.categories.flatMap { it.guides }.find { it.id == guideId }
        val categoryContext = cat ?: SurvivalData.categories.find { c -> c.guides.any { it.id == guideId } }
        Triple(categoryContext, g, g?.steps ?: emptyList())
    }

    val category = result.first
    val guide = result.second
    val steps = result.third

    if (guide == null) {
        onBackClick()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("back_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = category?.name?.substringAfter(" ")?.trim() ?: "Búsqueda",
                    style = MaterialTheme.typography.bodySmall,
                    color = SurvivalTextSecondary
                )
                Text(
                    text = "Instrucciones de Emergencia",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            item {
                Text(
                    text = guide.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 32.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                // Difficulty & Time badges
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    val difficultyColor = when {
                        guide.difficulty.contains("Fácil") -> Color(0xFF30D158)
                        guide.difficulty.contains("Moderado") -> Color(0xFFFF9F0A)
                        else -> Color(0xFFFF453A)
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(difficultyColor.copy(alpha = 0.15f), RoundedCornerShape(50.dp))
                            .border(1.dp, difficultyColor, RoundedCornerShape(50.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = guide.difficulty,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = difficultyColor
                        )
                    }
                    
                    if (guide.time != null) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(50.dp))
                                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = guide.time,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                Text(
                    text = guide.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SurvivalTextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Divider(color = SurvivalBorder, thickness = 1.dp)
            }

            // Illustration if available
            if (guide.illustrationAsset != null) {
                item {
                    val context = LocalContext.current
                    val resId = remember(guide.illustrationAsset) {
                        context.resources.getIdentifier(
                            guide.illustrationAsset, "drawable", context.packageName
                        )
                    }
                    if (resId != 0) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, SurvivalBorder, RoundedCornerShape(12.dp))
                                .background(Color(0xFF0D1117), RoundedCornerShape(12.dp))
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AndroidView(
                                factory = { ctx ->
                                    ImageView(ctx).apply {
                                        setImageResource(resId)
                                        scaleType = ImageView.ScaleType.FIT_CENTER
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                            )
                            Text(
                                text = "📊 Diagrama de referencia",
                                style = MaterialTheme.typography.labelSmall,
                                color = SurvivalTextSecondary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // High priority Warning Note box if present
            if (guide.warningNotes != null) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("⚠️", fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = "ADVERTENCIA CRÍTICA:",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = guide.warningNotes,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            // Numbered Steps
            items(steps.size) { index ->
                val stepText = steps[index]
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                    border = BorderStroke(1.dp, SurvivalBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Badge of step number in bold orange circle
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Text(
                            text = stepText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 26.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Tip box if present
            if (guide.tipNotes != null) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFF30D158), RoundedCornerShape(12.dp))
                            .background(Color(0xFF30D158).copy(alpha = 0.08f))
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡", fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = "CONSEJO ÚTIL:",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp
                                ),
                                color = Color(0xFF30D158)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = guide.tipNotes,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            // Household Sources box - where to find materials at home
            if (!guide.householdSources.isNullOrEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFFFFB340), RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFB340).copy(alpha = 0.08f))
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🏠", fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = "¿DÓNDE CONSEGUIRLO EN CASA?",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp
                                ),
                                color = Color(0xFFFFB340)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        guide.householdSources.forEach { source ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = source,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KitScreen(
    checkedStates: Map<String, Boolean>,
    onToggleItem: (String) -> Unit
) {
    val totalItems = SurvivalData.kitItems.size
    val checkedCount = SurvivalData.kitItems.count { checkedStates[it.id] == true }
    val progress = if (totalItems > 0) checkedCount.toFloat() / totalItems else 0f
    
    val totalCost = SurvivalData.kitItems.sumOf { it.estimatedCost }
    val spentCost = SurvivalData.kitItems.filter { checkedStates[it.id] == true }.sumOf { it.estimatedCost }
    
    // Group kit items by section to make it extremely organized and easy to scan
    val groupedItems = remember { SurvivalData.kitItems.groupBy { it.section } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Title banner
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.FormatListBulleted,
                    contentDescription = "Kit icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kit de Supervivencia",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Chequeá los elementos esenciales de tu mochila. El progreso y presupuesto estimativo se regulan localmente.",
                style = MaterialTheme.typography.bodyMedium,
                color = SurvivalTextSecondary
            )
        }

        // Progress Panel Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
            border = BorderStroke(1.dp, SurvivalBorder),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header of Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progreso del Kit",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$checkedCount / $totalItems items (${(progress * 100).toInt()}%)",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                // Sleek progress indicator
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = SurvivalBorder
                )
                
                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = SurvivalBorder, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(12.dp))
                
                // Budget indicators
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Presupuesto ya invertido",
                            style = MaterialTheme.typography.bodySmall,
                            color = SurvivalTextSecondary
                        )
                        Text(
                            text = "$${formatCost(spentCost)} ARS",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF30D158)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Total estimado del Kit",
                            style = MaterialTheme.typography.bodySmall,
                            color = SurvivalTextSecondary
                        )
                        Text(
                            text = "$${formatCost(totalCost)} ARS",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grouped Items list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            groupedItems.forEach { (section, items) ->
                item {
                    Text(
                        text = section,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
                
                items(items) { item ->
                    val isChecked = checkedStates[item.id] == true
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isChecked) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else SurvivalCardBg)
                            .border(1.dp, if (isChecked) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else SurvivalBorder, RoundedCornerShape(12.dp))
                            .clickable { onToggleItem(item.id) }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .testTag("kit_item_row_${item.id}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { onToggleItem(item.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = SurvivalTextSecondary,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.testTag("kit_item_checkbox_${item.id}")
                        )
                        
                        Spacer(modifier = Modifier.width(10.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (isChecked) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                            )
                        }
                        
                        Text(
                            text = if (item.estimatedCost == 0) "Gratis" else "$${formatCost(item.estimatedCost)}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (item.estimatedCost == 0) Color(0xFF30D158) else SurvivalTextSecondary
                        )
                    }
                }
            }
        }
    }
}

// Simple cost formatter
fun formatCost(cost: Int): String {
    return java.text.NumberFormat.getNumberInstance(java.util.Locale.GERMANY).format(cost)
}

@Composable
fun EmergencyContactsScreen() {
    val context = LocalContext.current
    val groupedContacts = remember { SurvivalData.emergencyContacts.groupBy { it.category } }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Title
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.PhoneInTalk,
                    contentDescription = "Phone icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Números de Emergencia",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Llamadas inmediatas de auxilio práctico médico y de servicios vitales nacionales.",
                style = MaterialTheme.typography.bodyMedium,
                color = SurvivalTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            groupedContacts.forEach { (categoryName, contacts) ->
                item {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                items(contacts) { contacto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("emergency_contact_${contacto.number}"),
                        colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
                        border = BorderStroke(1.dp, SurvivalBorder),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = contacto.name,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = contacto.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SurvivalTextSecondary
                                    )
                                }
                                
                                Spacer(modifier = Modifier.width(12.dp))

                                // Large interactive call button for direct Action Dial
                                Button(
                                    onClick = {
                                        try {
                                            // Filter digits and dial
                                            val filteredNumber = contacto.number.replace("[^0-9+]".toRegex(), "")
                                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$filteredNumber"))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "No se pudo iniciar la llamada: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    modifier = Modifier
                                        .testTag("call_button_${contacto.number}")
                                        .height(44.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Phone,
                                        contentDescription = "Llamar",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = contacto.number,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Acerca de icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Información del Proyecto",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = SurvivalCardBg),
            border = BorderStroke(1.dp, SurvivalBorder),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large styled shield badge representing our app icon identity (embosomed with electricity)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFFFF8B42))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Shield,
                            contentDescription = "SurviVal Shield Large",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                        Icon(
                            imageVector = Icons.Filled.FlashOn,
                            contentDescription = "Rayo",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "SurviVal",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        fontSize = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Versión 2.0",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Divider(color = SurvivalBorder, thickness = 1.dp)

                Spacer(modifier = Modifier.height(20.dp))

                // Highlighted indicators requested by user:
                
                // Indicators 1: Funcionalidad 100% offline
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1B5E20).copy(alpha = 0.2f))
                        .border(1.dp, Color(0xFF2E7D32), RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🟢",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "Esta app funciona 100% sin internet",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF81C784)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Indicators 2: Contenido para Argentina
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0D47A1).copy(alpha = 0.2f))
                        .border(1.dp, Color(0xFF1565C0), RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🇦🇷",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "Contenido de emergencia para Argentina",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64B5F6)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "8 categorías | 35+ guías detalladas | Kit de supervivencia",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Diseñado para emergencias reales: apagones, pandemias, desastres naturales",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Todo el material, guías de purificación, maniobras de primeros auxilios y de energía solar están compilados estáticamente de forma local para garantizar acceso instantáneo en situaciones sin red eléctrica, telefonía ni WiFi.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SurvivalTextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = SurvivalBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))

                // Credits
                Text(
                    text = "Idea original: Franco",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Desarrollado con Google AI Studio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SurvivalTextSecondary,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = "Proyecto open source - Uso libre y gratuito",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF30D158),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
