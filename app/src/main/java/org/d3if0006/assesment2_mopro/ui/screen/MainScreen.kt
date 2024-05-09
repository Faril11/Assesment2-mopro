package org.d3if0006.assesment2_mopro.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0006.assesment2_mopro.R
import org.d3if0006.assesment2_mopro.model.Outfits
import org.d3if0006.assesment2_mopro.routing.Screen
import org.d3if0006.assesment2_mopro.ui.theme.Assesment2moproTheme
import org.d3if0006.assesment2_mopro.util.SettingsDataStore

@Composable
fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    showList: Boolean
) {
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.emptyoutfits),
                contentDescription = stringResource(id = R.string.list_outfits_kosong),
                modifier = Modifier
                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.35f)
                    .aspectRatio(1f) // 1:1 aspect ratio
            )
            Text(text = stringResource(id = R.string.list_outfits_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(outfits = it) {
                        navController.navigate(Screen.DetailOutfits.withId(it.id))
                    }
                    Divider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(data) {
                    GridItem(outfits = it) {
                        navController.navigate(Screen.DetailOutfits.withId(it.id))
                    }
                }
            }
        }
    }
}


@Composable
fun ListItem(outfits: Outfits, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column {
            Text(
                text = outfits.nama,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = outfits.tinggi,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = outfits.berat)
        }
        Column {
            // Size Pakaian
            Text(
                text = determineClothingSize(outfits.tinggi.toInt(), outfits.berat.toInt()),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun GridItem(outfits: Outfits, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(
                    text = outfits.nama,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = outfits.tinggi,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = outfits.berat)
            }
            Column {
                // Size Pakaian
                Text(
                    text = determineClothingSize(outfits.tinggi.toInt(), outfits.berat.toInt()),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

fun determineClothingSize(height: Int, weight: Int): String {
    return when {
        height < 165 -> {
            when {
                weight < 65 -> "S"
                weight in 66..75 -> "M"
                weight in 76..85 -> "L"
                weight in 86..92 -> "XL"
                else -> "XXL"
            }
        }

        height in 166..175 -> {
            when {
                weight < 65 -> "M"
                weight in 66..75 -> "L"
                weight in 76..85 -> "XL"
                weight in 86..92 -> "XL"
                else -> "XXL"
            }
        }

        height in 176..185 -> {
            when {
                weight < 65 -> "L"
                weight in 66..75 -> "L"
                weight in 76..85 -> "XL"
                weight in 86..92 -> "XL"
                else -> "XXL"
            }
        }

        else -> {
            when {
                weight < 65 -> "XL"
                weight in 66..75 -> "XL"
                weight in 76..85 -> "XL"
                weight in 86..92 -> "XL"
                else -> "XXL"
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.list
                                else R.drawable.grid
                            ),
                            modifier = Modifier.size(28.dp),
                            contentDescription = stringResource(
                                if (showList) R.string.list
                                else R.string.grid
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.store),
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(Screen.AddOutfits.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_outfits),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = R.string.tambah_outfits),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding), navController, viewModel, showList)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment2moproTheme {
        MainScreen(rememberNavController(), viewModel())
    }
}