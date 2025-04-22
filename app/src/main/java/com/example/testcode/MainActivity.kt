package com.example.testcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcode.ui.theme.TestcodeTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.Divider
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.nativeCanvas
import android.graphics.Paint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * MainActivity는 파인만의 경로적분(Path Integral) 아이디어를 적용한
 * PathExplorer 클래스를 사용하여 그래프의 최적 경로를 계산하고,
 * Compose UI로 결과를 보여줍니다.
 * 
 * 또한 테스트 코드 작성 연습을 위한 추가 함수와 UI 컴포넌트를 포함합니다.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestcodeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
    
    /**
     * 인사말 형식 지정 함수 - UtilityFunctions 클래스 사용
     */
    fun formatGreeting(name: String?): String {
        return UtilityFunctions.formatGreeting(name)
    }
    
    /**
     * 만기일 계산 함수 - UtilityFunctions 클래스 사용
     */
    fun calculateDueDate(startDate: LocalDate, days: Int, divisor: Int): LocalDate {
        return UtilityFunctions.calculateDueDate(startDate, days, divisor)
    }
    
    /**
     * 문자열 리스트의 숫자를 파싱하고 합산하는 함수 - UtilityFunctions 클래스 사용
     */
    fun parseAndSum(strings: List<String>): Int {
        return UtilityFunctions.parseAndSum(strings)
    }
}

/**
 * UI 컴포저블: 메인 화면
 * 여러 컴포저블 간에 전환할 수 있는 메인 화면입니다.
 */
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf("Greeting") }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // 상단 메뉴 버튼들
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { currentScreen = "Greeting" }) {
                Text("인사말")
            }
            Button(onClick = { currentScreen = "PathIntegral" }) {
                Text("경로적분")
            }
            Button(onClick = { currentScreen = "Counter" }) {
                Text("카운터")
            }
        }
        
        // 선택된 화면 표시
        when (currentScreen) {
            "Greeting" -> Greeting("Android")
            "PathIntegral" -> PathIntegralScreen()
            "Counter" -> Counter()
        }
    }
}

/**
 * UI 컴포저블: 파인만의 경로적분을 시각적으로 체험할 수 있는 화면
 */
@Composable
fun PathIntegralScreen(modifier: Modifier = Modifier) {
    // 그래프 상태 관리
    var nodes by remember { mutableStateOf(listOf(
        Node("A"), Node("B"), Node("C"), Node("D")
    )) }
    
    var edges by remember { mutableStateOf(listOf(
        Edge(nodes[0], nodes[1], 1.0),  // A -> B 비용 1.0
        Edge(nodes[1], nodes[3], 1.5),  // B -> D 비용 1.5
        Edge(nodes[0], nodes[2], 2.0),  // A -> C 비용 2.0
        Edge(nodes[2], nodes[3], 0.5)   // C -> D 비용 0.5
    )) }

    // 시작 노드와 목표 노드
    var startNodeIndex by remember { mutableStateOf(0) } // A 노드
    var endNodeIndex by remember { mutableStateOf(3) }   // D 노드

    // 입력 필드 상태
    var newNodeName by remember { mutableStateOf("") }
    var newEdgeFrom by remember { mutableStateOf("") }
    var newEdgeTo by remember { mutableStateOf("") }
    var newEdgeCost by remember { mutableStateOf("") }
    
    // 다이얼로그 상태
    var showAddNodeDialog by remember { mutableStateOf(false) }
    var showAddEdgeDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // PathExplorer를 사용해 모든 경로 탐색 후 최적 경로 계산
    val explorer = PathExplorer()
    var optimalPath by remember { mutableStateOf(
        explorer.findOptimalPath(nodes[startNodeIndex], nodes[endNodeIndex], edges)
    ) }
    
    // 그래프 노드 위치 계산 (시각화용)
    val nodePositions = remember(nodes) {
        calculateNodePositions(nodes)
    }

    // 에러 다이얼로그
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("오류") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("확인")
                }
            }
        )
    }

    // 노드 추가 다이얼로그
    if (showAddNodeDialog) {
        AlertDialog(
            onDismissRequest = { showAddNodeDialog = false },
            title = { Text("새 노드 추가") },
            text = { 
                OutlinedTextField(
                    value = newNodeName,
                    onValueChange = { newNodeName = it },
                    label = { Text("노드 이름") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newNodeName.isNotBlank()) {
                            // 이름 중복 검사
                            if (nodes.any { it.name == newNodeName }) {
                                errorMessage = "이미 같은 이름의 노드가 존재합니다."
                                showErrorDialog = true
                            } else {
                                nodes = nodes + Node(newNodeName)
                                newNodeName = ""
                                showAddNodeDialog = false
                            }
                        } else {
                            errorMessage = "노드 이름을 입력해주세요."
                            showErrorDialog = true
                        }
                    }
                ) {
                    Text("추가")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNodeDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    // 엣지 추가 다이얼로그
    if (showAddEdgeDialog) {
        AlertDialog(
            onDismissRequest = { showAddEdgeDialog = false },
            title = { Text("새 엣지 추가") },
            text = { 
                Column {
                    // 출발 노드 입력
                    OutlinedTextField(
                        value = newEdgeFrom,
                        onValueChange = { newEdgeFrom = it },
                        label = { Text("시작 노드") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    // 도착 노드 입력
                    OutlinedTextField(
                        value = newEdgeTo,
                        onValueChange = { newEdgeTo = it },
                        label = { Text("도착 노드") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    // 비용 입력
                    OutlinedTextField(
                        value = newEdgeCost,
                        onValueChange = { newEdgeCost = it },
                        label = { Text("비용 (숫자)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newEdgeFrom.isBlank() || newEdgeTo.isBlank() || newEdgeCost.isBlank()) {
                            errorMessage = "모든 필드를 입력해주세요."
                            showErrorDialog = true
                            return@TextButton
                        }
                        
                        val cost = newEdgeCost.toDoubleOrNull()
                        if (cost == null) {
                            errorMessage = "비용은 숫자여야 합니다."
                            showErrorDialog = true
                            return@TextButton
                        }
                        
                        val fromNode = nodes.find { it.name == newEdgeFrom }
                        val toNode = nodes.find { it.name == newEdgeTo }
                        
                        if (fromNode == null || toNode == null) {
                            errorMessage = "존재하지 않는 노드입니다."
                            showErrorDialog = true
                            return@TextButton
                        }
                        
                        // 엣지 추가
                        edges = edges + Edge(fromNode, toNode, cost)
                        newEdgeFrom = ""
                        newEdgeTo = ""
                        newEdgeCost = ""
                        showAddEdgeDialog = false
                    }
                ) {
                    Text("추가")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddEdgeDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "파인만의 경로적분(Path Integral) 시뮬레이터",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // 그래프 시각화
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(Color(0xFFF5F5F5))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    // 그래프 그리기
                    GraphVisualization(
                        nodes = nodes,
                        edges = edges,
                        nodePositions = nodePositions,
                        optimalPath = optimalPath,
                        onNodeClick = { index ->
                            // 노드 클릭시 시작 또는 끝 노드로 설정
                            if (startNodeIndex == index) {
                                startNodeIndex = endNodeIndex
                                endNodeIndex = index
                            } else {
                                startNodeIndex = index
                            }
                            // 최적 경로 다시 계산
                            optimalPath = explorer.findOptimalPath(
                                nodes[startNodeIndex], 
                                nodes[endNodeIndex], 
                                edges
                            )
                        }
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // 시작/끝 노드 선택 UI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 시작 노드 선택
                    Column(modifier = Modifier.weight(1f)) {
                        Text("시작 노드:", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            nodes.forEachIndexed { index, node ->
                                NodeSelectionButton(
                                    node = node,
                                    isSelected = index == startNodeIndex,
                                    onClick = { 
                                        startNodeIndex = index
                                        optimalPath = explorer.findOptimalPath(
                                            nodes[startNodeIndex], 
                                            nodes[endNodeIndex], 
                                            edges
                                        )
                                    }
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // 끝 노드 선택
                    Column(modifier = Modifier.weight(1f)) {
                        Text("도착 노드:", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            nodes.forEachIndexed { index, node ->
                                NodeSelectionButton(
                                    node = node,
                                    isSelected = index == endNodeIndex,
                                    onClick = { 
                                        endNodeIndex = index
                                        optimalPath = explorer.findOptimalPath(
                                            nodes[startNodeIndex], 
                                            nodes[endNodeIndex], 
                                            edges
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // 현재 그래프 정보
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "그래프 정보",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // 에지 정보
                Column {
                    Text("엣지 목록:", fontWeight = FontWeight.Bold)
                    edges.forEach { edge ->
                        Text("${edge.from.name} → ${edge.to.name} (비용=${edge.cost})")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 그래프 조작 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { showAddNodeDialog = true }) {
                        Text("노드 추가")
                    }
                    
                    Button(onClick = { showAddEdgeDialog = true }) {
                        Text("엣지 추가")
                    }
                }
            }
        }
        
        // 결과 카드
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "최적 경로 결과",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // 최적 경로 결과 표시
                if (optimalPath.isNotEmpty()) {
                    Text(
                        text = "${nodes[startNodeIndex].name}에서 ${nodes[endNodeIndex].name}까지의 최적 경로:",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = optimalPath.joinToString(" → ") { it.name },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    
                    // 총 비용 계산
                    val totalCost = calculatePathCost(optimalPath, edges)
                    Text(
                        text = "총 비용: $totalCost",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        text = "경로를 찾을 수 없습니다.",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 다시 계산 버튼
                Button(
                    onClick = {
                        optimalPath = explorer.findOptimalPath(
                            nodes[startNodeIndex], 
                            nodes[endNodeIndex], 
                            edges
                        )
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("다시 계산")
                }
            }
        }
    }
}

/**
 * 그래프 시각화를 위한 Canvas 컴포넌트
 */
@Composable
fun GraphVisualization(
    nodes: List<Node>,
    edges: List<Edge>,
    nodePositions: Map<String, Offset>,
    optimalPath: List<Node>,
    onNodeClick: (Int) -> Unit
) {
    // 최적 경로에 포함된 엣지 찾기
    val optimalEdges = if (optimalPath.size > 1) {
        val edgePairs = mutableListOf<Pair<String, String>>()
        for (i in 0 until optimalPath.size - 1) {
            edgePairs.add(Pair(optimalPath[i].name, optimalPath[i + 1].name))
        }
        edgePairs
    } else {
        emptyList()
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        // 엣지 그리기
        edges.forEach { edge ->
            val startPos = nodePositions[edge.from.name] ?: return@forEach
            val endPos = nodePositions[edge.to.name] ?: return@forEach
            
            // 화살표 방향 계산
            val angle = atan2(endPos.y - startPos.y, endPos.x - startPos.x)
            val arrowLength = 15f
            
            // 노드 반경을 고려한 시작/끝 지점 조정
            val nodeRadius = 30f
            val startX = startPos.x + (nodeRadius * cos(angle))
            val startY = startPos.y + (nodeRadius * sin(angle))
            val endX = endPos.x - (nodeRadius * cos(angle))
            val endY = endPos.y - (nodeRadius * sin(angle))
            
            // 최적 경로에 포함된 엣지인지 확인
            val isOptimalEdge = optimalEdges.contains(Pair(edge.from.name, edge.to.name))
            
            // 선 색상 설정
            val color = if (isOptimalEdge) Color.Red else Color.Gray
            val strokeWidth = if (isOptimalEdge) 3f else 1.5f
            
            // 선 그리기
            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            
            // 화살표 헤드 그리기
            val arrowAngle1 = angle + Math.PI * 0.85
            val arrowAngle2 = angle - Math.PI * 0.85
            
            drawLine(
                color = color,
                start = Offset(endX, endY),
                end = Offset(
                    endX + (arrowLength * cos(arrowAngle1)).toFloat(),
                    endY + (arrowLength * sin(arrowAngle1)).toFloat()
                ),
                strokeWidth = strokeWidth
            )
            
            drawLine(
                color = color,
                start = Offset(endX, endY),
                end = Offset(
                    endX + (arrowLength * cos(arrowAngle2)).toFloat(),
                    endY + (arrowLength * sin(arrowAngle2)).toFloat()
                ),
                strokeWidth = strokeWidth
            )
            
            // 엣지 비용 표시
            val midX = (startX + endX) / 2
            val midY = (startY + endY) / 2
            val offsetX = sin(angle).toFloat() * 15f
            val offsetY = -cos(angle).toFloat() * 15f
            
            // Paint 객체 생성 및 설정
            val paint = Paint().apply {
                this.color = android.graphics.Color.BLACK
                textSize = 36f
                textAlign = Paint.Align.CENTER
            }
            
            // 텍스트 그리기
            drawContext.canvas.nativeCanvas.drawText(
                "${edge.cost}",
                midX + offsetX,
                midY + offsetY,
                paint
            )
        }
        
        // 노드 그리기
        nodes.forEachIndexed { index, node ->
            val position = nodePositions[node.name] ?: return@forEachIndexed
            
            // 노드가 최적 경로에 포함되는지 확인
            val isInOptimalPath = optimalPath.contains(node)
            
            // 노드 원 그리기
            drawCircle(
                color = if (isInOptimalPath) Color(0xFFF44336) else Color(0xFF2196F3),
                radius = 30f,
                center = position
            )
            
            // 노드 테두리 그리기
            drawCircle(
                color = Color.White,
                radius = 30f,
                center = position,
                style = Stroke(width = 2f)
            )
            
            // 노드 이름 표시
            val textPaint = Paint().apply {
                this.color = android.graphics.Color.WHITE
                textSize = 40f
                textAlign = Paint.Align.CENTER
            }
            
            drawContext.canvas.nativeCanvas.drawText(
                node.name,
                position.x,
                position.y + 12f,
                textPaint
            )
        }
    }
    
    // 노드 클릭 영역 처리 (Canvas 위에 투명한 Box로 클릭 이벤트 처리)
    Box(modifier = Modifier.fillMaxSize()) {
        nodes.forEachIndexed { index, node ->
            val position = nodePositions[node.name] ?: return@forEachIndexed
            
            Box(
                modifier = Modifier
                    .offset(
                        x = position.x.dp - 30.dp,
                        y = position.y.dp - 30.dp
                    )
                    .size(60.dp)
                    .clickable { onNodeClick(index) }
            )
        }
    }
}

/**
 * 노드 선택 버튼 컴포넌트
 */
@Composable
fun NodeSelectionButton(node: Node, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(36.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = node.name,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 * 그래프 시각화를 위한 노드 위치 계산
 */
private fun calculateNodePositions(nodes: List<Node>): Map<String, Offset> {
    val positions = mutableMapOf<String, Offset>()
    val centerX = 400f
    val centerY = 120f
    val radius = 100f
    
    nodes.forEachIndexed { index, node ->
        val angle = (index.toFloat() / nodes.size) * 2 * Math.PI
        val x = centerX + (radius * cos(angle)).toFloat()
        val y = centerY + (radius * sin(angle)).toFloat()
        positions[node.name] = Offset(x, y)
    }
    
    return positions
}

/**
 * 경로의 총 비용 계산
 */
private fun calculatePathCost(path: List<Node>, edges: List<Edge>): Double {
    var totalCost = 0.0
    
    if (path.size <= 1) return totalCost
    
    for (i in 0 until path.size - 1) {
        val edge = edges.find { it.from.name == path[i].name && it.to.name == path[i + 1].name }
        if (edge != null) {
            totalCost += edge.cost
        }
    }
    
    return totalCost
}

/**
 * UI 컴포저블: 인사말 표시 (테스트 대상)
 */
@Composable
fun Greeting(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello $name!")
    }
}

/**
 * UI 컴포저블: 카운터 기능 (무한 루프 버그 수정)
 */
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Count: $count")
        Button(onClick = {
            // 무한 루프 해결: 조건부 증가로 변경
            if (count < 10) {
                count += 1
            }
        }) {
            Text("Increment")
        }
        
        // 카운터 초기화 버튼 추가
        Button(
            onClick = { count = 0 },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Reset")
        }
    }
}
