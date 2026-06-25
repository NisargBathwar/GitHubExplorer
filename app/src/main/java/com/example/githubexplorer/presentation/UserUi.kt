package com.example.githubexplorer.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.CallSplit
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubexplorer.domain.model.User
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun UserUi(vm : UserViewModel = hiltViewModel() , onClick: (String) -> Unit) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    Column(Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(12.dp)) {

        Text(
            "GitHub Explorer",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Discover developers instantly",
            color = Color.Gray
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = state.query,
            onValueChange = {
                vm.onActions(UserActions.QueryChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Search GitHub users")
            },
            leadingIcon = {
                Icon(Icons.Default.Search,null)
            },
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if(state.query.isNotBlank()){ vm.onActions(UserActions.GetUsers(state.query)) }
                }
            )
        )

        Spacer(Modifier.height(20.dp))

        LaunchedEffect(listState) {
            snapshotFlow {
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }.collect { lastIndex ->
                if (lastIndex == state.users.lastIndex &&
                    state.users.isNotEmpty()
                ) {
                    vm.onActions(UserActions.LoadMore)
                }
            }
        }

        when {

            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            state.users.isEmpty() -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Search GitHub Users",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Discover developers instantly",
                        color = Color.Gray
                    )
                }
            }

            else -> {

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Results (${state.users.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(12.dp))

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.users,
                        key = { it.id }
                    ) { user ->
                        UserItem(
                            user = user,
                            onClick = {
                                onClick(user.login)
                            }
                        )
                    }
                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: (String) -> Unit
) {

    ElevatedCard(
        onClick = {
            onClick(user.login)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = user.login,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = user.login,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "GitHub Developer",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Tap to view profile →",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUser(
    vm: UserViewModel = hiltViewModel(),
    username : String ,
    onBack: () -> Unit
) {

    LaunchedEffect(username) {
        vm.onActions(UserActions.DetailUser(username))
        delay(500.milliseconds)
        vm.onActions(UserActions.GetRepos(username))
    }
    val state by vm.uiState.collectAsStateWithLifecycle()
    val scrollBarBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.detailUser?.login ?: "User",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                } ,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent ,
                    scrolledContainerColor = Color.Transparent
                ),
                scrollBehavior = scrollBarBehavior
            )
        }
    ) { padding ->

        when {

            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            state.detailUser != null -> {

                val user = state.detailUser!!

                LazyColumn(
                    modifier = Modifier
                        .nestedScroll(scrollBarBehavior.nestedScrollConnection)
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(
                        top = 20.dp ,
                        bottom = 20.dp ,
                        end = 20.dp ,
                        start = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    item {
                        AsyncImage(
                            model = user.avatar,
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                                .border(
                                    3.dp,
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                                .animateContentSize()
                        )
                        Spacer(Modifier.height(20.dp))
                        Text(
                            text = user.name ?: user.login,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "@${user.login}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                    item {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(15.dp) ,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            StartItem(
                                value = user.followers.toString(),
                                title = "Followers"
                            )
                            StartItem(
                                value = user.following.toString(),
                                title = "Following"
                            )
                            StartItem(
                                value = state.repos.size.toString(),
                                title = "Repos"
                            )
                        }
                    }

                    item{
                        DetailCard(
                            Icons.Default.LocationOn, "locations",
                            value = user.location
                        )
                    }
                    item {
                        DetailCard(
                            Icons.Default.Email, "email",
                            value = user.email
                        )
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                Icons.Default.Folder,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = "Repositories",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    items(state.repos) { repo->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                                .padding(horizontal = 4.dp)
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(22.dp),
                            elevation = CardDefaults.elevatedCardElevation(3.dp)
                        ) {

                            Column(
                                Modifier.padding(18.dp)
                            ) {

                                Text(
                                    repo.name ?: "",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(6.dp))

                                AssistChip(
                                    onClick = {},
                                    enabled = false,
                                    label = {
                                        Text(repo.language ?: "Unknown")
                                    }
                                )

                                Spacer(Modifier.height(10.dp))

                                Row {

                                    Icon(
                                        Icons.Default.Star,
                                        null,
                                        tint = Color(0xFFFFC107)
                                    )

                                    Spacer(Modifier.width(4.dp))

                                    Text("${repo.stars}")

                                    Spacer(Modifier.width(20.dp))

                                    Icon(
                                        Icons.AutoMirrored.Filled.CallSplit,
                                        null
                                    )

                                    Spacer(Modifier.width(4.dp))

                                    Text("${repo.forks}")
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
fun StartItem(
    value: String,
    title: String
) {

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp)
            .padding(horizontal = 4.dp, vertical = 8.dp) ,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DetailCard(
    icon: ImageVector,
    title: String,
    value: String?
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = value ?: "Not Available",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


