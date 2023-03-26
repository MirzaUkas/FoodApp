package com.mirz.foodapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mirz.foodapp.model.FoodData.foods
import com.mirz.foodapp.ui.theme.FoodAppTheme
import com.mirz.foodapp.ui.theme.Shapes
import com.mirz.foodapp.ui.theme.grey


@Composable
fun FoodItem(
    image: String,
    title: String,
    desc: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.subtitle2,
            color = grey,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FoodItemPreview() {
    FoodAppTheme {
        FoodItem(
            image = foods.first().photoUrl,
            title = foods.first().name,
            desc = foods.first().origin
        )
    }
}