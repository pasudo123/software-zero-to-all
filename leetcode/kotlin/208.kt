/**
 * trie 자료구조. 
 * 문자열의 각 문자 하나하나를 트리구조로 저장한다. 
 * 기존 코드는 mutableSetOf<String>() 을 사용했는데, 그것보다 속도가 2배 더 빠름.
 */
class Trie() {

    class TrieNode {
        val child = mutableMapOf<Char, TrieNode>()
        var isEnd = false
    }

    private val root = TrieNode()

    fun insert(word: String) {
        var node = root
        for (c in word) {
            node = node.child.getOrPut(c) { TrieNode() }
        }

        node.isEnd = true
    }

    fun search(word: String): Boolean {
        val node = findNodeOrNull(word) ?: return false  
        return node.isEnd
    }

    fun startsWith(prefix: String): Boolean {
        return findNodeOrNull(prefix) != null
    }

    private fun findNodeOrNull(prefix: String): TrieNode? {
        var node = root
        for (c in prefix) {
            node = node.child[c] ?: return null
        }

        return node
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * var obj = Trie()
 * obj.insert(word)
 * var param_2 = obj.search(word)
 * var param_3 = obj.startsWith(prefix)
 */
