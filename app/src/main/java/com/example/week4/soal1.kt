package com.example.week4

import androidx.annotation.DrawableRes
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private val Blue = Color(0xFF2563EB)
private val Bg = Color(0xFFF0F6FC)
private val Ink = Color(0xFF172B4D)
private val CardBorder = Color(0xFFD9E5F2)
private val Red = Color(0xFFEF4444)

class Car(
    val name: String,
    val transmission: String,
    val fuel: String,
    val year: Int,
    val mileage: Int,
    val location: String,
    val price: String,
    val description: String,
    @DrawableRes val image: Int = R.drawable.ic_launcher_foreground,
    favorite: Boolean = false
) {
    var favorite by mutableStateOf(favorite)
    val mileageText: String get() = String.format(Locale.US, "%,d km", mileage)
}

private fun fuelColor(fuel: String) = when (fuel.uppercase()) {
    "PHEV" -> Color(0xFF6D4AFF)
    "HYBRID" -> Color(0xFF0E9F8E)
    "EV" -> Color(0xFF03A9F4)
    "DIESEL" -> Color(0xFFF81212)
    else -> Color(0xFFFFA80F)
}

val cars = listOf(
    Car(
        "Tiggo 9 CSH", "Auto", "PHEV", 2024, 5000, "Jakarta", "Rp 650.000.000",
        "A premium plug-in hybrid SUV that combines elegant design, advanced technology, spacious interiors, and powerful performance for a comfortable driving experience.",
        image = R.drawable.tiggo,
        false
    ),
    Car(
        "Honda CRV", "Auto", "Petrol", 2013, 15200, "Surabaya", "Rp 190.000.000",
        "A stylish and versatile SUV offering a spacious cabin, reliable performance, advanced safety features, and excellent comfort for everyday driving and family trips.",
        image = R.drawable.crv,
        false
    ),
    Car(
        "Toyota Yaris Cross", "Auto", "Hybrid", 2024, 30500, "Bandung", "Rp 420.000.000",
        "A modern compact SUV with a sporty design, fuel-efficient performance, and practical features, perfect for urban driving and daily adventures.",
        image = R.drawable.yariscross,
        false
    ),
    Car(
        "Daihatsu Xenia", "Manual", "Petrol", 2022, 28000, "Malang", "Rp 215.000.000",
        "A practical and affordable family MPV with spacious seating, good fuel efficiency, and reliable performance, making it ideal for everyday family use.",
        image = R.drawable.xenia,
         false
    ),
    Car(
        "Toyota Yaris 1M", "Auto", "Petrol", 2016, 80000, "Jagir, Surabaya", "Rp 1.000.000.000",
        "Sebuah Yaris yang setiap hari melewati jagir sehingga menjadi legenda Yaris 1M",
        image = R.drawable.yaris1m,
        true
    ),
    Car(
        "Toyota Innova 1 of 1", "Auto", "Diesel", 2021, 100000, "Greenlake", "DM For More Info",
        "Toyota Innova dengan konsep 1 of 1 yang menjadi pelopor di Indonesia, dengan gaya menyetirnya yang sangat luar biasa disertai dengan lagu kebanggaannya 'sampe bawah' ",
        image = R.drawable.innova1of1,
        true
    ),
    Car(
        "Honda HRV", "Auto", "Petrol", 2024, 53500, "Semarang", "Rp 320.000.000",
        "A sleek and sporty SUV featuring a modern interior, smooth performance, and smart technology, offering the perfect balance of style and practicality.",
        image = R.drawable.hrv,
        false
    ),
    Car(
        "Pajero Anomali RFS", "Auto", "Diesel", 2025, 20000, "Graha Family", "Rp 670.000.000",
        "Pajero anomali dari Graha Family, konon katanya drivernya sedang patah hati maka parkirnya sering miring",
        image = R.drawable.pajero_anomali,
        true
    ),
    Car(
        "Jaecoo J5", "Auto", "EV", 2023, 42300, "Semarang", "Rp 278.000.000",
        "A modern SUV with a bold exterior, comfortable interior, and advanced features, designed for drivers who value style, versatility, and convenience.",
        image = R.drawable.j5,
        false
    ),
    Car(
        "MG 4", "Auto", "EV", 2024, 20400, "Probolinggo", "Rp 325.000.000",
        "A fully electric hatchback with a futuristic design, responsive acceleration, and modern technology, delivering an enjoyable and eco-friendly driving experience.",
        image = R.drawable.mg4,
        false
    )
)

@Composable
fun CarSaleApp() {
    var selected by remember { mutableStateOf<Car?>(null) }
    val car = selected

    if (car == null) {
        CarListScreen(onCarClick = { selected = it })
    } else {
        BackHandler { selected = null }
        CarDetailScreen(car = car, onBack = { selected = null })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarSaleAppPreview() {
    MaterialTheme { CarSaleApp() }
}

@Composable
fun CarListScreen(onCarClick: (Car) -> Unit) {
    val saved = cars.count { it.favorite }

    Surface(modifier = Modifier.fillMaxSize(), color = Bg) {
        LazyColumn(
            modifier = Modifier.statusBarsPadding(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 24.dp, 16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { CarHeader(saved) }
            items(cars) { car ->
                CarCard(car = car, onClick = { onCarClick(car) })
            }
        }
    }
}

@Composable
private fun CarHeader(saved: Int) {
    Column(Modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
        Box(Modifier.width(32.dp).height(3.dp).background(Blue))
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Column(Modifier.weight(1f)) {
                Text("Our Car Selections", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                Text("Find the perfect car for your style", fontSize = 12.sp, color = Color.Black.copy(alpha = 0.45f))
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE8F1FF))
                    .border(1.dp, Blue.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Favorite, null, tint = Red, modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(4.dp))
                Text("$saved Favourited", fontSize = 11.sp, color = Blue, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun CarCard(car: Car, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Row(Modifier.padding(10.dp)) {
            Box(Modifier.size(88.dp)) {
                Image(
                    painter = painterResource(car.image),
                    contentDescription = car.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFD9D9D9))
                )
                FuelBadge(
                    fuel = car.fuel,
                    modifier = Modifier.align(Alignment.BottomStart).padding(4.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f).height(88.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        car.name,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink
                    )
                    FavoriteIcon(car, size = 20.dp)
                }
                Text(
                    car.transmission,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFEAF1F8))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    fontSize = 10.sp,
                    color = Color.Black.copy(alpha = 0.55f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(6.dp).clip(CircleShape).background(Blue))
                    Spacer(Modifier.width(4.dp))
                    Text("${car.year}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Text(car.mileageText, fontSize = 11.sp, color = Color.Black.copy(alpha = 0.45f))
                }
            }
        }
    }
}

@Composable
fun FuelBadge(fuel: String, modifier: Modifier = Modifier) {
    val c = fuelColor(fuel)
    Text(
        fuel.uppercase(),
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(c.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 3.dp),
        fontSize = 8.sp,
        fontWeight = FontWeight.ExtraBold,
        color = c
    )
}

/** Ikon favorit yang bisa di-toggle. */
@Composable
fun FavoriteIcon(car: Car, size: androidx.compose.ui.unit.Dp = 22.dp) {
    Icon(
        imageVector = if (car.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        contentDescription = "Favorite",
        tint = if (car.favorite) Red else Color.Black.copy(alpha = 0.25f),
        modifier = Modifier
            .size(size + 8.dp)
            .clip(CircleShape)
            .clickable { car.favorite = !car.favorite }
            .padding(4.dp)
    )
}

@Composable
fun CarDetailScreen(car: Car, onBack: () -> Unit) {
    Scaffold(
        containerColor = Bg,
        bottomBar = { DetailBottomBar(car) }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding())) {
            Image(
                painter = painterResource(car.image),
                contentDescription = car.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(Color(0xFFD9D9D9))
            )

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(280.dp))
                Box {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .background(Bg)
                            .padding(16.dp, 28.dp, 16.dp, 24.dp)
                    ) {
                        DetailContent(car)
                    }
                    FuelBadge(
                        fuel = car.fuel,
                        modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp)
                    )
                }
            }

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(12.dp)
                    .size(36.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(Icons.Filled.ArrowBack, "Back", modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun DetailContent(car: Car) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            car.name, modifier = Modifier.weight(1f),
            fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Ink
        )
        FavoriteIcon(car, size = 26.dp)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(Blue))
        Spacer(Modifier.width(6.dp))
        Text(car.location, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.55f))
    }
    Spacer(Modifier.height(14.dp))
    Text(
        car.price,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE8F1FF))
            .border(1.dp, Blue.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        color = Blue, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp
    )
    Spacer(Modifier.height(20.dp))
    SectionLabel("SPECIFICATIONS")
    Spacer(Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SpecTile("YEAR", "${car.year}", Modifier.weight(1f))
        SpecTile("MILEAGE", car.mileageText, Modifier.weight(1f))
    }
    Spacer(Modifier.height(10.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SpecTile("TRANSMISSION", car.transmission, Modifier.weight(1f))
        SpecTile("FUEL TYPE", car.fuel, Modifier.weight(1f))
    }
    Spacer(Modifier.height(20.dp))
    SectionLabel("ABOUT THIS CAR")
    Spacer(Modifier.height(8.dp))
    Text(
        car.description,
        fontSize = 12.sp, lineHeight = 19.sp,
        color = Color.Black.copy(alpha = 0.55f)
    )
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text, fontSize = 10.sp, letterSpacing = 1.2.sp,
        fontWeight = FontWeight.SemiBold, color = Color.Black.copy(alpha = 0.38f)
    )
}

@Composable
private fun SpecTile(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Column(Modifier.padding(12.dp).fillMaxWidth()) {
            Text(label, fontSize = 8.sp, letterSpacing = 1.sp, color = Color.Black.copy(alpha = 0.38f))
            Spacer(Modifier.height(6.dp))
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DetailBottomBar(car: Car) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg)
            .navigationBarsPadding()
            .padding(16.dp, 8.dp, 16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { car.favorite = !car.favorite },
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(14.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
            border = BorderStroke(1.dp, Blue.copy(alpha = 0.5f))
        ) {
            Icon(
                if (car.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                null, tint = Blue
            )
        }
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.horizontalGradient(listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))))
                .clickable { /* contact seller */ },
            contentAlignment = Alignment.Center
        ) {
            Text("Contact Seller", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}