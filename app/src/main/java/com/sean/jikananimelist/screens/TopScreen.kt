package com.sean.jikananimelist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.sean.jikananimelist.model.JAnime
import com.sean.jikananimelist.viewmodel.TopViewModel

@Composable
fun TopScreen(
    viewModel: TopViewModel = hiltViewModel(),
    modifier: Modifier,
    onItemSelected: (JAnime) -> Unit
) {
    val topAnimeFlow = viewModel.topAnimeStateFlow.collectAsLazyPagingItems()
    LaunchedEffect(topAnimeFlow.loadState) {
        println("REFRESH: ${topAnimeFlow.loadState.refresh}")
        println("APPEND: ${topAnimeFlow.loadState.append}")
    }
    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        items(
            topAnimeFlow.itemCount,
            key = topAnimeFlow.itemKey { it.id }
            ) { index ->
            val item = topAnimeFlow[index]
            if (item != null) {
                CardItemView(item, modifier = Modifier, onClick = {
                    onItemSelected(item)
                })
            } else {
                Text("Placeholder")
            }
        }
    }
}

@Composable
fun CardItemView(jAnime: JAnime, modifier: Modifier, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier) {
            AsyncImage(
                model = jAnime.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Column() {
                Text("Rank: ${jAnime.rank ?: "N/A"}", fontSize = 12.sp)
                Text(jAnime.titles.firstOrNull()?.title ?: "N/A")
            }
        }
    }
}