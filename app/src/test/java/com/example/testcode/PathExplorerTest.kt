package com.example.testcode

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import android.util.Log

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PathExplorerTest {

    private lateinit var pathExplorer: PathExplorer
    
    // 테스트에 사용할 노드 정의
    private val nodeA = Node(id = 1, label = "A")
    private val nodeB = Node(id = 2, label = "B")
    private val nodeC = Node(id = 3, label = "C")
    private val nodeD = Node(id = 4, label = "D")
    private val nodeE = Node(id = 5, label = "E")
    
    // 테스트에 사용할 간선 정의
    private lateinit var edges: List<Edge>
    
    companion object {
        private const val TAG = "PathExplorerTest"
    }
    
    @Before
    fun setup() {
        // Robolectric에서 로그를 콘솔에 출력하도록 설정
        ShadowLog.stream = System.out
        
        pathExplorer = PathExplorer()
        
        // 간선 설정
        edges = listOf(
            Edge(from = nodeA, to = nodeB, cost = 2.0),
            Edge(from = nodeA, to = nodeC, cost = 4.0),
            Edge(from = nodeB, to = nodeD, cost = 3.0),
            Edge(from = nodeC, to = nodeD, cost = 1.0),
            Edge(from = nodeC, to = nodeE, cost = 5.0),
            Edge(from = nodeD, to = nodeE, cost = 2.0)
        )
        
        Log.d(TAG, "테스트 초기화 완료: 노드와 간선 구성됨")
        edges.forEach { edge ->
            Log.d(TAG, "간선: ${edge.from.label} -> ${edge.to.label}, 비용: ${edge.cost}")
        }
    }
    
    // findOptimalPath 함수 성공 케이스 테스트
    @Test
    fun findOptimalPath_validGraph_returnsCorrectPath() {
        // Given
        val start = nodeA
        val target = nodeE
        
        // When
        Log.d(TAG, "테스트 시작: A에서 E까지의 최적 경로 찾기")
        val optimalPath = pathExplorer.findOptimalPath(start, target, edges)
        
        // Then
        // A -> C -> D -> E 또는 A -> B -> D -> E
        val expectedPathLabels1 = listOf("A", "C", "D", "E")
        val expectedPathLabels2 = listOf("A", "B", "D", "E")
        
        val pathLabels = optimalPath.map { it.label }
        Log.d(TAG, "찾은 경로: ${pathLabels.joinToString(" -> ")}")
        
        val isValidPath = pathLabels == expectedPathLabels1 || pathLabels == expectedPathLabels2
        assertTrue("최적 경로가 예상 경로와 일치하지 않습니다", isValidPath)
        Log.d(TAG, "테스트 성공: 최적 경로 확인됨")
    }
    
    // findOptimalPath 함수의 동일 노드 테스트
    @Test
    fun findOptimalPath_sameStartAndTarget_returnsSingleNodePath() {
        // Given
        val start = nodeA
        val target = nodeA
        
        // When
        Log.d(TAG, "테스트 시작: 시작점과 목적지가 동일한 경우 (A에서 A까지)")
        val path = pathExplorer.findOptimalPath(start, target, edges)
        
        // Then
        Log.d(TAG, "찾은 경로: ${path.map { it.label }.joinToString(" -> ")}")
        assertEquals(1, path.size)
        assertEquals(start.id, path.first().id)
        Log.d(TAG, "테스트 성공: 단일 노드 경로 반환됨")
    }
    
    // findOptimalPath 함수의 경로 없음 테스트
    @Test
    fun findOptimalPath_noPathExists_returnsEmptyList() {
        // Given
        val start = nodeA
        val target = nodeE
        // 노드 E로의 모든 간선을 제거한 새로운 간선 목록 생성
        val edgesWithoutPathToE = edges.filter { it.to != nodeE }
        
        // When
        Log.d(TAG, "테스트 시작: 경로가 존재하지 않는 경우 (E로 가는 간선 제거)")
        edgesWithoutPathToE.forEach { edge ->
            Log.d(TAG, "남은 간선: ${edge.from.label} -> ${edge.to.label}")
        }
        val path = pathExplorer.findOptimalPath(start, target, edgesWithoutPathToE)
        
        // Then
        Log.d(TAG, "결과: ${if (path.isEmpty()) "경로 없음" else path.map { it.label }.joinToString(" -> ")}")
        assertTrue(path.isEmpty())
        Log.d(TAG, "테스트 성공: 경로가 없을 때 빈 리스트 반환됨")
    }
    
    // findOptimalPath 함수의 null 간선 테스트
    @Test
    fun findOptimalPath_nullEdges_returnsEmptyList() {
        // Given
        val start = nodeA
        val target = nodeE
        
        // When
        Log.d(TAG, "테스트 시작: edges가 null인 경우")
        val path = pathExplorer.findOptimalPath(start, target, null)
        
        // Then
        Log.d(TAG, "결과: ${if (path.isEmpty()) "경로 없음" else path.map { it.label }.joinToString(" -> ")}")
        assertTrue(path.isEmpty())
        Log.d(TAG, "테스트 성공: null edges 처리 확인됨")
    }
    
    // parseEdgeCosts 함수 성공 케이스 테스트
    @Test
    fun parseEdgeCosts_validStrings_returnsCorrectDoubles() {
        // Given
        val costStrings = listOf("1.5", "2.0", "3.7")
        val expected = listOf(1.5, 2.0, 3.7)
        
        // When
        Log.d(TAG, "테스트 시작: 유효한 문자열 비용 파싱")
        Log.d(TAG, "입력 문자열: ${costStrings.joinToString(", ")}")
        val result = pathExplorer.parseEdgeCosts(costStrings)
        
        // Then
        Log.d(TAG, "파싱 결과: ${result.joinToString(", ")}")
        assertArrayEquals(expected.toDoubleArray(), result.toDoubleArray(), 0.001)
        Log.d(TAG, "테스트 성공: 문자열 비용이 올바르게 파싱됨")
    }
    
    // parseEdgeCosts 함수 실패 케이스 테스트
    @Test
    fun parseEdgeCosts_invalidStrings_filterOutInvalidValues() {
        // Given
        val costStrings = listOf("1.5", "invalid", "3.7", "wrong", "5.2")
        val expected = listOf(1.5, 3.7, 5.2)
        
        // When
        Log.d(TAG, "테스트 시작: 일부 유효하지 않은 문자열 비용 파싱")
        Log.d(TAG, "입력 문자열: ${costStrings.joinToString(", ")}")
        val result = pathExplorer.parseEdgeCosts(costStrings)
        
        // Then
        Log.d(TAG, "파싱 결과: ${result.joinToString(", ")}")
        assertArrayEquals(expected.toDoubleArray(), result.toDoubleArray(), 0.001)
        assertEquals(3, result.size)
        Log.d(TAG, "테스트 성공: 유효하지 않은 값이 올바르게 필터링됨")
    }
    
    // parseEdgeCosts 함수 빈 입력 테스트
    @Test
    fun parseEdgeCosts_emptyList_returnsEmptyList() {
        // Given
        val costStrings = emptyList<String>()
        
        // When
        Log.d(TAG, "테스트 시작: 빈 문자열 목록 비용 파싱")
        val result = pathExplorer.parseEdgeCosts(costStrings)
        
        // Then
        Log.d(TAG, "파싱 결과: 빈 목록")
        assertTrue(result.isEmpty())
        Log.d(TAG, "테스트 성공: 빈 목록 처리 확인됨")
    }
    
    // 복잡한 그래프에서의 findOptimalPath 테스트
    @Test
    fun findOptimalPath_complexGraph_returnsCorrectPath() {
        // Given
        val nodeF = Node(id = 6, label = "F")
        val nodeG = Node(id = 7, label = "G")
        
        val complexEdges = edges + listOf(
            Edge(from = nodeA, to = nodeF, cost = 1.0),
            Edge(from = nodeF, to = nodeG, cost = 1.0),
            Edge(from = nodeG, to = nodeE, cost = 10.0)
        )
        
        // When
        Log.d(TAG, "테스트 시작: 복잡한 그래프에서 A에서 E까지의 최적 경로 찾기")
        complexEdges.forEach { edge ->
            Log.d(TAG, "복잡한 그래프 간선: ${edge.from.label} -> ${edge.to.label}, 비용: ${edge.cost}")
        }
        
        val optimalPath = pathExplorer.findOptimalPath(nodeA, nodeE, complexEdges)
        
        // Then
        // A -> C -> D -> E 또는 A -> B -> D -> E가 최적 경로여야 함 (비용이 7)
        // A -> F -> G -> E는 비용이 12로 더 높음
        val pathLabels = optimalPath.map { it.label }
        Log.d(TAG, "찾은 경로: ${pathLabels.joinToString(" -> ")}")
        
        val expectedPathLabels1 = listOf("A", "C", "D", "E")
        val expectedPathLabels2 = listOf("A", "B", "D", "E")
        
        val isValidPath = pathLabels == expectedPathLabels1 || pathLabels == expectedPathLabels2
        assertTrue("복잡한 그래프에서 최적 경로가 예상 경로와 일치하지 않습니다", isValidPath)
        Log.d(TAG, "테스트 성공: 복잡한 그래프에서 최적 경로 확인됨")
    }
}

// 테스트를 위한 노드 클래스 (PathExplorer에서 필요로 하는 클래스 구조)
data class Node(val id: Int, val label: String)

// 테스트를 위한 간선 클래스 (PathExplorer에서 필요로 하는 클래스 구조)
data class Edge(val from: Node, val to: Node, val cost: Double)
