package com.example.testcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcode.ui.theme.TestcodeTheme

/**
 * MainActivity는 파인만의 경로적분(Path Integral) 아이디어를 적용한
 * PathExplorer 클래스를 사용하여 그래프의 최적 경로를 계산하고,
 * Compose UI로 결과를 보여줍니다.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestcodeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    PathIntegralScreen(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

/**
 * UI 컴포저블: 파인만의 경로적분을 시각적으로 체험할 수 있는 화면
 */
@Composable
fun PathIntegralScreen(modifier: Modifier = Modifier) {
    // 노드와 엣지를 정의 (실제 문제에서는 입력폼으로 대체 가능)
    val a = Node("A")
    val b = Node("B")
    val c = Node("C")
    val d = Node("D")
    val edges = listOf(
        Edge(a, b, 1.0),  // A -> B 비용 1.0
        Edge(b, d, 1.5),  // B -> D 비용 1.5
        Edge(a, c, 2.0),  // A -> C 비용 2.0
        Edge(c, d, 0.5)   // C -> D 비용 0.5
    )

    // PathExplorer를 사용해 모든 경로 탐색 후 최적 경로 계산
    val explorer = PathExplorer()
    val optimalPath = remember { mutableStateOf(explorer.findOptimalPath(a, d, edges)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "파인만의 경로적분(Path Integral) 예제", modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "그래프:", modifier = Modifier.padding(vertical = 4.dp))
        
        // 명시적으로 타입을 지정하여 오류 해결
        edges.forEach { edge: Edge ->
            Text("${edge.from.name} → ${edge.to.name} (비용=${edge.cost})")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "최적 경로 계산 중...", modifier = Modifier.padding(vertical = 4.dp))
        Spacer(modifier = Modifier.height(8.dp))
        
        // 최적 경로 결과 표시
        if (optimalPath.value.isNotEmpty()) {
            Text(text = "최적 경로: " + optimalPath.value.joinToString(" → ") { node -> node.name })
        } else {
            Text(text = "경로를 찾을 수 없습니다.")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        // 다시 계산 버튼 (시뮬레이션용)
        Button(onClick = {
            // 단순히 재계산만 수행 (경로 데이터가 바뀌면 recomposition 발생)
            optimalPath.value = explorer.findOptimalPath(a, d, edges)
        }) {
            Text("다시 계산")
        }
    }
}