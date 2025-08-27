package com.example.projectmanagement.widgets

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.AppColor

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    modifierImage: Modifier = Modifier,
    contentDescription: String? = null,
    allowBackgroundColor: Boolean = false,
    url: String = "",
    roundedTop: Int = 0,
    roundedBottom: Int = 0,
    isCircle: Boolean = false,
    onErrorPainter: Painter? = null
) {
    Box(
        modifier = modifier.background(
            if (allowBackgroundColor) AppColor.primaryColor else Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        )
    ) {
        if (url.isEmpty() && onErrorPainter != null) {
            Image(
                painter = onErrorPainter,
                contentDescription = contentDescription,
                modifier = modifierImage.then(
                    if (isCircle) Modifier.clip(CircleShape)
                    else Modifier
                )
            )
        } else {
            AsyncImage(
                modifier = modifierImage
                    .fillMaxSize()
                    .then(
                        if (isCircle) Modifier.clip(CircleShape)
                        else Modifier.clip(
                            RoundedCornerShape(
                                topStart = roundedTop.dp,
                                topEnd = roundedTop.dp,
                                bottomEnd = roundedBottom.dp,
                                bottomStart = roundedBottom.dp
                            )
                        )
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
//                .transformations(CircleCropTransformation()) // Optional transformations
                    .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher), // Optional placeholder
                error = painterResource(id = R.drawable.ic_launcher) // Optional error image
            )
        }
    }

}

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    modifierImage: Modifier = Modifier,
    contentDescription: String? = null,
    allowBackgroundColor: Boolean = false,
    url: String = "",
    roundedTopStart: Int = 0,
    roundedBottomStart: Int = 0,
    roundedTopEnd: Int = 0,
    roundedBottomEnd: Int = 0,
    isCircle: Boolean = false,
    onErrorPainter: Painter? = null
) {
    Box(
        modifier = modifier.background(
            if (allowBackgroundColor) AppColor.primaryColor else Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        )
    ) {
        if (url.isEmpty() && onErrorPainter != null) {
            Image(
                painter = onErrorPainter,
                contentDescription = contentDescription,
                modifier = modifierImage.then(
                    if (isCircle) Modifier.clip(CircleShape)
                    else Modifier
                )
            )
        } else {
            AsyncImage(
                modifier = modifierImage
                    .fillMaxSize()
                    .then(
                        if (isCircle) Modifier.clip(CircleShape)
                        else Modifier.clip(
                            RoundedCornerShape(
                                topStart = roundedTopStart.dp,
                                topEnd = roundedTopEnd.dp,
                                bottomEnd = roundedBottomEnd.dp,
                                bottomStart = roundedBottomStart.dp
                            )
                        )
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
//                .transformations(CircleCropTransformation()) // Optional transformations
                    .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher), // Optional placeholder
                error = painterResource(id = R.drawable.ic_launcher) // Optional error image
            )
        }
    }

}


@Composable
fun AppImageUri(
    uri: Uri,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Image",
                    tint = AppColor.primaryColor
                )
            }
        }
    }
}
