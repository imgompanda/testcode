package com.example.testcode

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.ArithmeticException
import java.lang.NullPointerException

/**
 * PathExplorer 클래스에 대한 단위 테스트
 * 이 테스트 클래스는 PathExplorer의 여러 메서드와 기능을 검증합니다.
 * 
 * 학생들은 이 클래스를 참고하여 자신만의 테스트 코드를 작성하고
 * PathExplorer 클래스의 버그를 찾아 수정하는 연습을 할 수 있습니다.
 */
class PathExplorerTest {
    
    private lateinit var explorer: PathExplorer
    private lateinit var nodeA: Node
    private lateinit var nodeB: Node
    private lateinit var nodeC: Node
    private lateinit var nodeD: Node
    private lateinit var edges: List<Edge>
    
    @Before
    fun setup() {
        explorer = PathExplorer()
        
        // 테스트용 그래프 설정
        nodeA = Node("A")
        nodeB = Node("B")
        nodeC = Node("C")
        nodeD = Node("D")
        
        edges = listOf(
            Edge(nodeA, nodeB, 1.0),
            Edge(nodeA, nodeC, 2.0),
            Edge(nodeB, nodeD, 3.0),
            Edge(nodeC, nodeD, 1.0)
        )
    }
    
    /**
     * 테스트: 동일한 시작 및 목적지 노드 (정상 케이스)
     * 예상 결과: 시작 노드만 포함하는 경로 반환
     */
    @Test
    fun testFindOptimalPath_sameStartAndTarget_returnsSingleNodePath() {
        val result = explorer.findOptimalPath(nodeA, nodeA, edges)
        assertEquals(1, result.size)
        assertEquals(nodeA, result[0])
    }
    
    /**
     * 테스트: 간단한 그래프에서 최적 경로 찾기
     * 예상 결과: A -> C -> D (비용 3.0)
     * 
     * 참고: 현재 버그로 인해 이 테스트는 실패할 수 있습니다.
     * 학생들은 버그를 수정하여 테스트가 통과하도록 해야 합니다.
     */
    @Test
    fun testFindOptimalPath_simpleGraph_returnsOptimalPath() {
        val result = explorer.findOptimalPath(nodeA, nodeD, edges)
        
        // 기대되는 경로: A -> C -> D (비용 합계 3.0)
        val expectedPath = listOf(nodeA, nodeC, nodeD)
        assertEquals(expectedPath, result)
    }
    
    /**
     * 테스트: 엣지가 null일 때 NullPointerException 발생 확인
     * 예상 결과: NullPointerException 발생
     * 
     * 참고: 이 테스트는 버그를 검증합니다.
     * 학생들은 NPE가 발생하지 않도록 코드를 수정해야 합니다.
     */
    @Test(expected = NullPointerException::class)
    fun testFindOptimalPath_nullEdges_throwsNullPointerException() {
        // 이 테스트는 현재 버그를 검증합니다 (null 체크 없음)
        explorer.findOptimalPath(nodeA, nodeD, null)
    }
    
    /**
     * 테스트: 경로 비용 계산 시 0으로 나누기 예외 발생 확인 
     * 예상 결과: ArithmeticException 발생
     * 
     * 참고: 이 테스트는 의도적인 버그를 검증합니다.
     * 학생들은 0으로 나누기가 발생하지 않도록 코드를 수정해야 합니다.
     */
    @Test(expected = ArithmeticException::class)
    fun testCalculatePathCost_singleNodePath_throwsArithmeticException() {
        // private 메서드를 직접 테스트할 수 없으므로, 공개 메서드를 통해 간접적으로 테스트
        // 단일 노드 경로는 calculatePathCost에서 0으로 나누기 오류 발생
        explorer.findOptimalPath(nodeA, nodeA, edges)
    }
    
    /**
     * 테스트: 문자열 변환 오류 확인
     * 예상 결과: NumberFormatException 발생
     * 
     * 참고: 이 테스트는 의도적인 버그를 검증합니다.
     * 학생들은 안전한 변환 방법을 사용하도록 코드를 수정해야 합니다.
     */
    @Test(expected = NumberFormatException::class)
    fun testParseEdgeCosts_invalidInput_throwsNumberFormatException() {
        val invalidCosts = listOf("1.0", "2.5", "not-a-number", "3.0")
        explorer.parseEdgeCosts(invalidCosts)
    }
    
    /**
     * 테스트: 문자열에서 비용 변환 (정상 케이스)
     * 예상 결과: 문자열 리스트가 Double 리스트로 변환됨
     */
    @Test
    fun testParseEdgeCosts_validInput_returnsDoubleList() {
        val costs = listOf("1.0", "2.5", "3.0")
        val result = explorer.parseEdgeCosts(costs)
        
        assertEquals(3, result.size)
        assertEquals(1.0, result[0], 0.001)
        assertEquals(2.5, result[1], 0.001)
        assertEquals(3.0, result[2], 0.001)
    }
    
    // 학생들이 추가할 수 있는 더 많은 테스트 케이스:
    // 1. 순환 그래프에서 무한 루프 발생 검증
    // 2. 비연결 그래프에서의 결과 검증
    // 3. 가중치가 음수인 엣지 처리 검증
    // 4. 경로가 존재하지 않는 경우 검증
    // 5. 매우 큰 그래프에서의 성능 테스트
} 