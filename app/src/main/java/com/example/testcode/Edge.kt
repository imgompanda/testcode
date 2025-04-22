package com.example.testcode

/**
 * 그래프의 간선(엣지)을 나타내는 데이터 클래스
 * @param from 시작 노드
 * @param to 목적지 노드
 * @param cost 간선의 비용(거리, 시간 등)
 */
data class Edge(val from: Node, val to: Node, val cost: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Edge) return false
        return from == other.from && to == other.to
    }
    
    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
} 