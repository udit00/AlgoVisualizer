
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradientColors: List<Color>,
    borderColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        contentPadding = PaddingValues()
    ) {
        Box(
           modifier = Modifier
               .background(
                   brush = Brush.horizontalGradient(colors = gradientColors)
               )
//               .border(color = borderColor, width = 2.dp)
               .padding(horizontal = 25.dp, vertical = 15.dp)

        )
        {
            Text(
                color = textColor,
                text = text
            )
        }
    }
}