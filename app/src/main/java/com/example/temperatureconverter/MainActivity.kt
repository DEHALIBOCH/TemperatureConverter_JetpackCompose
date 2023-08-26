package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewMainActivity() {
    MainActivityContent()
}

@Composable
private fun MainActivityContent() {
    val celsius = remember { mutableStateOf(.0) }
    val newCelsius = remember { mutableStateOf("") }

    Column {
        Header(image = R.drawable.sunrise, description = "sunrise image")
        EnterTemperature(temperature = newCelsius.value, changed = { newCelsius.value = it })
        ConvertButton { newCelsius.value.toDoubleOrNull()?.let { celsius.value = it } }
        TemperatureText(celsius = celsius.value)
    }
}

@Composable
private fun Header(image: Int, description: String) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TemperatureText(celsius: Double) {
    val fahrenheit = (celsius * 9 / 5) + 32
    Text(text = "$celsius Celsius is $fahrenheit Fahrenheit")
}

@Composable
private fun ConvertButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text(text = "Convert")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterTemperature(temperature: String, changed: (String) -> Unit) {
    TextField(
        value = temperature,
        onValueChange = changed,
        label = { Text(text = "Enter a temperature in Celsius") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        )
    )
}