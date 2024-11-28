/**
 * stack 에 단순 값 저장이 아닌 메타 데이터를 저장해서 최적화를 할 수 있다.
 */
class StockSpanner() {

    // first=price, second=span
    private val stack = Stack<Pair<Int, Int>>()

    fun next(price: Int): Int {
        var span = 1

        while (stack.isNotEmpty() && stack.peek().first <= price) {
            span += stack.pop().second
        }

        stack.push(Pair(price, span))

        return span
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * var obj = StockSpanner()
 * var param_1 = obj.next(price)
 */
