package com.example.testcode

/**
 * 그래프의 노드(정점)를 나타내는 데이터 클래스
 * @param id 노드 고유 식별자
 * @param label 노드 레이블
 */
data class Node(val id: Int, val label: String) {
    /**
     * 간단한 이름 생성자
     * - 레이블만 지정하고 ID는 자동 생성
     */
    constructor(name: String) : this(name.hashCode(), name)
    
    /**
     * 노드 이름 속성
     * - 하위 호환성을 위해 label을 반환
     */
    val name: String
        get() = label
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id
    }
} 