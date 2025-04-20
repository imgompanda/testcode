package com.example.testcode

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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
    fun findOptimalPath(start: Node, target: Node, edges: List<Edge>?): List<Node> {
        // 버그 수정: null 체크 추가
        if (edges == null) {
            return emptyList()
        }
        
        // 동일 노드 처리
        if (start == target) {
            return listOf(start)
        }
        
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
        // 목적지에 도달한 경우 현재 경로 반환
        if (start == target) {
            return listOf(currentPath)
        }
        
        val paths = mutableListOf<List<Node>>()
        
        // 현재 노드에서 갈 수 있는 다음 노드들을 탐색
        val outgoingEdges = edges.filter { it.from == start }
        for (edge in outgoingEdges) {
            val nextNode = edge.to
            
            // 버그 수정: 순환 방지 로직 복원
            if (nextNode in visited) continue
            
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
        // 버그 수정: 0으로 나누기 방지
        if (path.size <= 1) {
            return 0.0 // 단일 노드 경로는 비용 0
        }
        
        var totalCost = 0.0
        
        // 경로의 각 구간(엣지)에 대한 비용 합산
        for (i in 0 until path.size - 1) {
            val from = path[i]
            val to = path[i + 1]
            
            // 해당 엣지 찾기
            val edge = edges.find { it.from == from && it.to == to }
            
            // 버그 수정: null 안전한 처리 추가
            if (edge != null) {
                totalCost += edge.cost
            } else {
                // 엣지가 존재하지 않으면 연결되지 않은 경로이므로 매우 큰 값 반환
                return Double.MAX_VALUE
            }
        }
        
        return totalCost
    }
    
    /**
     * 문자열 목록을 Double 비용으로 변환하는 함수
     * 버그 수정: 안전한 변환 추가
     */
    fun parseEdgeCosts(costStrings: List<String>): List<Double> {
        return costStrings.mapNotNull { 
            try {
                it.toDouble()
            } catch (e: NumberFormatException) {
                null // 변환 실패한 항목은 null로 처리하여 필터링됨
            }
        }
    }
} 