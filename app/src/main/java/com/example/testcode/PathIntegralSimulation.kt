package com.example.testcode

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.math.sqrt

/**
 * 경로 적분 시뮬레이션을 위한 클래스
 * 노드와 엣지를 관리하고 경로 계산을 수행합니다.
 */
class PathIntegralSimulation {
    // 노드 리스트
    private val _nodes = mutableStateListOf<Node>()
    val nodes: List<Node> get() = _nodes
    
    // 엣지 리스트
    private val _edges = mutableStateListOf<Edge>()
    val edges: List<Edge> get() = _edges
    
    // 오류 메시지
    private val _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value
    
    // 경로 탐색기
    private val pathExplorer = PathExplorer()
    
    // 최적 경로
    private val _optimalPath = mutableStateListOf<Node>()
    val optimalPath: List<Node> get() = _optimalPath
    
    /**
     * 노드를 추가합니다.
     * @param name 노드 이름
     * @return 성공 여부
     */
    fun addNode(name: String): Boolean {
        // 입력 검증
        if (name.isBlank()) {
            _error.value = "노드 이름은 비어있을 수 없습니다."
            return false
        }
        
        // 중복 이름 검사
        if (_nodes.any { it.name == name }) {
            _error.value = "이미 '$name' 노드가 존재합니다."
            return false
        }
        
        // 노드 추가
        _nodes.add(Node(name))
        _error.value = null
        
        // 최적 경로 재계산
        calculateOptimalPath()
        return true
    }
    
    /**
     * 엣지를 추가합니다.
     * @param fromName 시작 노드 이름
     * @param toName 도착 노드 이름
     * @param cost 비용
     * @return 성공 여부
     */
    fun addEdge(fromName: String, toName: String, cost: Double): Boolean {
        // 입력 검증
        if (fromName.isBlank() || toName.isBlank()) {
            _error.value = "노드 이름은 비어있을 수 없습니다."
            return false
        }
        
        if (cost < 0) {
            _error.value = "비용은 음수일 수 없습니다."
            return false
        }
        
        // 노드 찾기
        val fromNode = _nodes.find { it.name == fromName }
        val toNode = _nodes.find { it.name == toName }
        
        // 존재하지 않는 노드 처리
        if (fromNode == null) {
            _error.value = "'$fromName' 노드가 존재하지 않습니다."
            return false
        }
        
        if (toNode == null) {
            _error.value = "'$toName' 노드가 존재하지 않습니다."
            return false
        }
        
        // 자기 자신으로의 엣지 처리
        if (fromNode == toNode) {
            _error.value = "자기 자신으로의 엣지는 추가할 수 없습니다."
            return false
        }
        
        // 중복 엣지 검사
        if (_edges.any { it.from == fromNode && it.to == toNode }) {
            _error.value = "'$fromName'에서 '$toName'으로의 엣지가 이미 존재합니다."
            return false
        }
        
        // 엣지 추가
        _edges.add(Edge(fromNode, toNode, cost))
        _error.value = null
        
        // 최적 경로 재계산
        calculateOptimalPath()
        return true
    }
    
    /**
     * 노드를 제거합니다.
     * @param node 제거할 노드
     */
    fun removeNode(node: Node) {
        // 해당 노드와 연결된 모든 엣지를 제거
        _edges.removeAll { it.from == node || it.to == node }
        
        // 노드 제거
        _nodes.remove(node)
        
        // 최적 경로 재계산
        calculateOptimalPath()
    }
    
    /**
     * 엣지를 제거합니다.
     * @param edge 제거할 엣지
     */
    fun removeEdge(edge: Edge) {
        _edges.remove(edge)
        
        // 최적 경로 재계산
        calculateOptimalPath()
    }
    
    /**
     * 최적 경로를 계산합니다.
     * 노드가 2개 이상일 때만 계산합니다.
     */
    fun calculateOptimalPath() {
        _optimalPath.clear()
        
        // 노드가 2개 미만이면 계산할 필요 없음
        if (_nodes.size < 2) return
        
        // 시작 노드와 목적지 노드 (첫 번째와 마지막 노드 사용)
        val startNode = _nodes.firstOrNull() ?: return
        val targetNode = _nodes.lastOrNull() ?: return
        
        // 경로 계산
        val path = pathExplorer.findOptimalPath(startNode, targetNode, _edges)
        
        // 결과 저장
        _optimalPath.clear()
        _optimalPath.addAll(path)
    }
    
    /**
     * 모든 데이터를 초기화합니다.
     */
    fun clear() {
        _nodes.clear()
        _edges.clear()
        _optimalPath.clear()
        _error.value = null
    }
    
    /**
     * 최적 경로의 총 비용을 계산합니다.
     * @return 총 비용
     */
    fun calculateTotalCost(): Double {
        if (_optimalPath.size <= 1) return 0.0
        
        var totalCost = 0.0
        for (i in 0 until _optimalPath.size - 1) {
            val from = _optimalPath[i]
            val to = _optimalPath[i + 1]
            
            val edge = _edges.find { it.from == from && it.to == to }
            if (edge != null) {
                totalCost += edge.cost
            }
        }
        
        return totalCost
    }
} 