package com.mirz.foodapp.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetreward.ui.common.UiState
import com.mirz.foodapp.R
import com.mirz.foodapp.di.Injection
import com.mirz.foodapp.ui.ViewModelFactory
import com.mirz.foodapp.ui.theme.grey

@Composable
fun DetailFoodScreen(
    foodId: Int,
    viewModel: DetailFoodViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    shareAction: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFood(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.photoUrl,
                    data.name,
                    data.origin,
                    data.description,
                    onBackClick = navigateBack,
                    shareAction = shareAction
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    origin: String,
    description: String,
    shareAction: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = photoUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentDescription = null
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name, style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.origin), style = MaterialTheme.typography.subtitle2,
            color = grey,
        )
        Text(
            text = origin, style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.description), style = MaterialTheme.typography.subtitle2,
            color = grey,
        )
        Text(
            text = description, style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                shareAction("Check this Food! $description")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text(text = stringResource(id = R.string.share).uppercase())
        }
    }
}

