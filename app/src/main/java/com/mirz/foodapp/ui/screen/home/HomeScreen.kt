package com.mirz.foodapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetreward.ui.common.UiState
import com.mirz.foodapp.R
import com.mirz.foodapp.di.Injection
import com.mirz.foodapp.model.Food
import com.mirz.foodapp.ui.ViewModelFactory
import com.mirz.foodapp.ui.components.FoodItem
import com.mirz.foodapp.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFoods()
            }
            is UiState.Success -> {
                HomeContent(
                    foods = uiState.data,
                    query = viewModel.query,
                    searchFoods = viewModel::searchFoods,
                    modifier = modifier,
                    navigateToProfile = navigateToProfile,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    foods: List<Food>,
    query: String,
    modifier: Modifier = Modifier,
    searchFoods: (String) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        ) {
            SearchBar(
                query = query,
                onQueryChange = searchFoods,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.about_page),
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateToProfile() }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(foods) { data ->
                FoodItem(
                    image = data.photoUrl,
                    title = data.name,
                    desc = data.origin,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }

}