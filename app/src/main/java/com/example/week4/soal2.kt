package com.example.week4

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF080F1E)
private val Panel = Color(0xFF101B30)
private val PanelLight = Color(0xFF192944)
private val Border = Color(0xFF293D5C)
private val LightBlue = Color(0xFF7DB8FF)
private val HpColor = Color(0xFFFF3B55)
private val MpColor = Color(0xFF2B7FFF)
private val Green = Color(0xFF22C55E)
private val AvatarBg = Color(0xFF203653)

data class Hero(
    val name: String,
    val heroClass: String,
    val rarity: String,
    val level: Int,
    val cp: Int,
    val hp: Int, val maxHp: Int,
    val mp: Int, val maxMp: Int,
    val atk: Int, val def: Int, val crit: Int, val spd: Int,
    val skillName: String,
    val skillDesc: String,
    val accent: Color,
    val inSquad: Boolean = false,
    @DrawableRes val image: Int = R.drawable.ic_launcher_foreground
)

val heroes = listOf(
    Hero(
        "Alistair", "High Templar", "MYTHIC", 50, 5600, 1650, 1800, 650, 800,
        380, 540, 22, 105, "Wrath of the Heavens",
        "Calls down a celestial hammer, stunning all enemies for 3 seconds and taunting survivors.",
        Color(0xFFFFB300), image = R.drawable.paladin
    ),
    Hero(
        "Theresa", "Divine Oracle", "MYTHIC", 49, 5480, 1100, 1200, 2100, 2400,
        210, 320, 12, 98, "Benediction",
        "Restores health to all allies and cleanses one negative effect from each.",
        Color(0xFF22C55E), image = R.drawable.cleric
    ),
    Hero(
        "Vespera", "Shadowblade", "MYTHIC", 48, 5350, 890, 950, 410, 600,
        460, 210, 41, 140, "Umbral Flurry",
        "Strikes the three weakest enemies in a blur of shadow, guaranteeing a critical hit.",
        Color(0xFFD946EF), image = R.drawable.rogue
    ),
    Hero(
        "Elaria", "Archmage", "MYTHIC", 47, 5120, 450, 600, 1850, 2000,
        520, 150, 18, 92, "Arcane Cataclysm",
        "Unleashes a storm of arcane energy dealing massive damage to every enemy.",
        Color(0xFF22D3EE), inSquad = true, image = R.drawable.archmage
    ),
    Hero(
        "Brogar", "Berserker", "LEGENDARY", 45, 4700, 300, 1500, 100, 400,
        480, 280, 30, 100, "Blood Rage",
        "Sacrifices health to boost attack power and attack speed for 8 seconds.",
        Color(0xFFFF3B55), inSquad = true, image = R.drawable.berserker
    ),
    Hero(
        "Kael", "Berserker", "LEGENDARY", 44, 4520, 1320, 1400, 200, 350,
        450, 260, 27, 96, "Cleaving Axe",
        "Swings a mighty axe hitting all enemies in front for heavy damage.",
        Color(0xFFFF7A3B), image = R.drawable.berserker
    )
)

private val filters = listOf("All (14)", "Berserker (2)", "Archmage (3)", "Healer (2)", "Assassin (3)", "Tank (4)")

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PartyRosterPreview() {
    MaterialTheme { PartyRosterScreen() }
}

@Composable
fun PartyRosterScreen() {
    var selectedFilter by remember { mutableIntStateOf(0) }
    val squad = heroes.filter { it.inSquad }

    Surface(modifier = Modifier.fillMaxSize(), color = Bg) {
        LazyColumn(
            modifier = Modifier.statusBarsPadding(),
            contentPadding = PaddingValues(16.dp, 20.dp, 16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { RosterTitle() }
            item { SquadPanel(squad) }
            item { SearchBar() }
            item { FilterRow(selectedFilter) { selectedFilter = it } }
            itemsIndexed(heroes) { _, hero -> HeroCard(hero) }
        }
    }
}

@Composable
private fun RosterTitle() {
    Row(verticalAlignment = Alignment.Top) {
        Column(Modifier.weight(1f)) {
            Text("Party Roster", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Text("Guild Vanguard • 14 Heroes Available", fontSize = 11.sp, color = Color.White.copy(alpha = 0.55f))
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(LightBlue.copy(alpha = 0.12f))
                .border(1.dp, LightBlue.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Star, null, tint = LightBlue, modifier = Modifier.size(12.dp))
            Spacer(Modifier.width(4.dp))
            Text("Expedition", fontSize = 11.sp, color = LightBlue, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SquadPanel(squad: List<Hero>) {
    val totalCp = squad.sumOf { it.cp }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Panel),
        border = BorderStroke(1.dp, LightBlue.copy(alpha = 0.35f))
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(7.dp).clip(RoundedCornerShape(50)).background(Green))
                Spacer(Modifier.width(6.dp))
                Text(
                    "EXPEDITION SQUAD (${squad.size}/4)",
                    fontSize = 10.sp, letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.Close, null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("$totalCp CP", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = LightBlue)
            }
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                squad.forEach { h ->
                    Image(
                        painter = painterResource(h.image),
                        contentDescription = h.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AvatarBg)
                            .border(2.dp, h.accent, RoundedCornerShape(10.dp))
                    )
                    Spacer(Modifier.width(8.dp))
                }
                repeat(4 - squad.size) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .border(1.dp, Border, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Add, null, tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightBlue)
                        .clickable { /* logic tidak perlu berfungsi */ }
                        .padding(horizontal = 16.dp, vertical = 11.dp)
                ) {
                    Text("Deploy ⚡", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun SearchBar() {
    var query by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, null, tint = Color.White.copy(alpha = 0.55f), modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text("Search by hero name, class, or rarity...", fontSize = 12.sp, color = Color.White.copy(alpha = 0.38f))
            }
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontSize = 13.sp),
                cursorBrush = SolidColor(LightBlue),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FilterRow(selected: Int, onSelect: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(filters) { i, label ->
                val isSelected = i == selected
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) LightBlue.copy(alpha = 0.15f) else Panel)
                        .border(1.dp, if (isSelected) LightBlue else Border, RoundedCornerShape(20.dp))
                        .clickable { onSelect(i) }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        label, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                        color = if (isSelected) LightBlue else Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .height(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightBlue.copy(alpha = 0.15f))
                .border(1.dp, LightBlue.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Sort: Power", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = LightBlue)
        }
    }
}

@Composable
fun HeroCard(hero: Hero) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Panel),
        border = BorderStroke(if (hero.inSquad) 1.5.dp else 1.dp, if (hero.inSquad) LightBlue else Border)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Tag(hero.rarity, HpColor, outlined = true)
                Spacer(Modifier.width(6.dp))
                Tag(hero.heroClass, hero.accent)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.Close, null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(3.dp))
                Text("${hero.cp} CP", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = LightBlue)
                if (hero.inSquad) {
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "IN SQUAD", fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, color = LightBlue,
                        modifier = Modifier
                            .border(1.dp, LightBlue, RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .border(1.5.dp, hero.accent, RoundedCornerShape(10.dp))
                        .padding(1.5.dp)
                ) {
                    Image(
                        painter = painterResource(hero.image),
                        contentDescription = hero.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                            .background(AvatarBg)
                    )
                    Text(
                        "Lvl ${hero.level}",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.85f))
                            .padding(horizontal = 4.dp, vertical = 1.dp),
                        fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            hero.name, modifier = Modifier.weight(1f),
                            fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Color.White
                        )
                        Icon(
                            if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            null, tint = Color.White.copy(alpha = 0.55f), modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    StatBar("HP", hero.hp, hero.maxHp, HpColor)
                    Spacer(Modifier.height(4.dp))
                    StatBar("MP", hero.mp, hero.maxMp, MpColor)
                }
            }

            AnimatedVisibility(visible = expanded) {
                HeroDetails(hero)
            }
        }
    }
}

@Composable
private fun Tag(text: String, color: Color, outlined: Boolean = false) {
    Text(
        text,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = if (outlined) 0.1f else 0.18f))
            .then(if (outlined) Modifier.border(1.dp, color, RoundedCornerShape(4.dp)) else Modifier)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = color
    )
}

@Composable
private fun HeroDetails(hero: Hero) {
    Column {
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = Border)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatTile("ATK", "${hero.atk}", Color(0xFF60A5FA), Modifier.weight(1f))
            StatTile("DEF", "${hero.def}", Color(0xFF9CA3AF), Modifier.weight(1f))
            StatTile("CRIT", "${hero.crit}%", HpColor, Modifier.weight(1f))
            StatTile("SPD", "${hero.spd}", Color(0xFF93C5FD), Modifier.weight(1f))
        }
        Spacer(Modifier.height(10.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(PanelLight)
                .border(1.dp, Border, RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, null, tint = LightBlue, modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(4.dp))
                Text(hero.skillName, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = LightBlue)
            }
            Spacer(Modifier.height(4.dp))
            Text(hero.skillDesc, fontSize = 10.sp, lineHeight = 14.sp, color = Color.White.copy(alpha = 0.55f))
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButton("Heal (+250 HP)", Green, Modifier.weight(1f))
            ActionButton("Add to Squad", LightBlue, Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatTile(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PanelLight)
            .border(1.dp, Border, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Spacer(Modifier.height(2.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
    }
}

@Composable
private fun ActionButton(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
            .clickable { /* logic tidak perlu berfungsi */ },
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

/** Bar HP/MP: lebar mengikuti value / max. */
@Composable
fun StatBar(label: String, value: Int, max: Int, color: Color) {
    val ratio = (value.toFloat() / max).coerceIn(0f, 1f)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            label, modifier = Modifier.width(22.dp),
            fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.55f)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(7.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Black)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(ratio)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "$value / $max", modifier = Modifier.width(70.dp),
            fontSize = 9.sp, fontWeight = FontWeight.SemiBold,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}