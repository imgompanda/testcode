package com.example.testcode

/**
 * 파인만의 경로적분 아이디어를 활용한 그래프 탐색기
 * 모든 가능한 경로를 찾고 그 중 최적의 경로를 계산합니다.
 */
class PathExplorer {
    
    /**
     * 시작 노드에서 목적지 노드까지의 최적 경로를 찾는 함수
     * @param start 시작 노드
     * @param target 목적지 노드
     * @param edges 그래프를 구성하는 간선들
     * @return 최적 경로를 구성하는 노드 리스트
     */
    fun findOptimalPath(start: Node, target: Node, edges: List<Edge>): List<Node> {
        // 버그 1: null 체크 없이 edges 사용 (NPE 가능성)
        if (start.name == target.name) {
            return listOf(start)
        }
        
        // 버그 2: 재귀 종료 조건 없는 경우 무한 루프 가능성
        val allPaths = findAllPaths(start, target, edges)
        
        // 경로가 없으면 빈 리스트 반환
        if (allPaths.isEmpty()) return emptyList()
        
        // 각 경로의 총 비용을 계산합니다
        val pathCosts = allPaths.map { path ->
            path to calculatePathCost(path, edges)
        }
        
        // 비용이 가장 낮은 경로를 찾아 반환합니다
        return pathCosts.minByOrNull { it.second }?.first ?: emptyList()
    }
    
    /**
     * 시작 노드에서 목적지 노드까지의 모든 가능한 경로를 찾는 함수
     */
    private fun findAllPaths(
        start: Node, 
        target: Node, 
        edges: List<Edge>,
        currentPath: List<Node> = listOf(start),
        visited: Set<Node> = setOf(start)
    ): List<List<Node>> {
        // 버그 3: 목적지에 도달하지 못하는 경우 무한 루프 가능성
        if (start.name == target.name) { // 의도적으로 == 대신 name 비교로 변경
            return listOf(currentPath)
        }
        
        val paths = mutableListOf<List<Node>>()
        
        // 현재 노드에서 갈 수 있는 다음 노드들을 탐색
        val outgoingEdges = edges.filter { it.from == start }
        for (edge in outgoingEdges) {
            val nextNode = edge.to
            
            // 버그 4: 순환 방지 로직 제거됨 (무한 루프 가능성)
            // 이미 방문한 노드는 다시 방문하지 않음 (순환 방지)
            // if (nextNode in visited) continue
            
            // 다음 노드로 경로 탐색 재귀 호출
            val newPath = currentPath + nextNode
            val newVisited = visited + nextNode
            
            paths.addAll(
                findAllPaths(nextNode, target, edges, newPath, newVisited)
            )
        }
        
        return paths
    }
    
    /**
     * 주어진 경로의 총 비용을 계산하는 함수
     */
    private fun calculatePathCost(path: List<Node>, edges: List<Edge>): Double {
        var totalCost = 0.0
        
        // 버그 5: 0으로 나누기 오류 가능성
        if (path.size <= 1) {
            return 1.0 / (path.size - 1) // 의도적으로 div by zero 발생
        }
        
        // 경로의 각 구간(엣지)에 대한 비용 합산
        for (i in 0 until path.size - 1) {
            val from = path[i]
            val to = path[i + 1]
            
            // 해당 엣지 찾기
            val edge = edges.find { it.from == from && it.to == to }
            
            // 버그 6: edge가 null인 경우 NPE 발생
            totalCost += edge!!.cost // 강제 언래핑으로 NPE 가능성
        }
        
        return totalCost
    }
    
    // 버그 7: 문자열 숫자 변환 오류 가능성
    fun parseEdgeCosts(costStrings: List<String>): List<Double> {
        return costStrings.map { it.toDouble() } // 안전하지 않은 변환
    }
} 