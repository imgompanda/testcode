package com.example.testcode

/**
 * 그래프의 간선(엣지)을 나타내는 클래스
 * @param from 시작 노드
 * @param to 도착 노드
 * @param cost 비용 (가중치)
 */
data class Edge(val from: Node, val to: Node, val cost: Double) 