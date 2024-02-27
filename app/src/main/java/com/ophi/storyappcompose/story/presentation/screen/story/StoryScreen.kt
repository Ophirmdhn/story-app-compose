package com.ophi.storyappcompose.story.presentation.screen.story

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ophi.storyappcompose.story.domain.model.ListStoryItem
import com.ophi.storyappcompose.story.presentation.screen.story.component.StoryCard
import com.ophi.storyappcompose.story.presentation.util.UiState
import com.ophi.storyappcompose.story.presentation.util.component.Loading

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel = hiltViewModel()
) {

    viewModel.storyState.collectAsState(UiState.Loading).value.let { state ->
        when (state) {
            is UiState.Success -> {
                if (!state.data.error) {
                    StoryContent(
                        modifier = modifier,
                        story = state.data.listStory
                    )
                    Log.d("Story Screen", "Berhasil mengambil data story ${state.data.listStory}")
                } else {
                    Log.e("Story Screen", "Gagal mengambil data story ${state.data.listStory}")
                    Toast.makeText(LocalContext.current, state.data.message, Toast.LENGTH_LONG).show()
                }
            }

            is UiState.Loading -> {
                viewModel.getStories()
                Loading()
            }

            is UiState.Error -> {
                Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_LONG).show()
                Log.e("Story Screen", "Gagal mengambil data story")
            }
        }
    }
}

@Composable
fun StoryContent(
    modifier: Modifier = Modifier,
    story: List<ListStoryItem>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (story.isEmpty()) {
            item {
                Text(text = "Data tidak ada!")
            }
        } else {
            items(story) { data ->
                StoryCard(
                    image = data.photoUrl,
                    description = data.description
                )
            }
        }
    }
}