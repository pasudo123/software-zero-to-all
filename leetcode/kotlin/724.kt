/**
 * 피벗 인덱스를 기준으로 왼쪽에 있는 요소 합, 오른쪽에 있는 요소 합이 같다면 피벗 인덱스를 리턴한다.
 * 최초에 0인덱스를 시작하는 시점에 leftSum = 0, rightSum = 0인덱스를 제외한 나머지 합으로 할당한다.
 * 
 * num.forEachIndexed {} 를 수행하는 시점부턴 왼쪽합과 오른쪽합을 비교하고 현재 index 를 오른쪽으로 이동시킨다.
 * leftSum 과 rightSum 의 값은 매 반복마다 새롭게 값을 갱신한다.
 **/
class Solution {
    fun pivotIndex(nums: IntArray): Int {
        var leftSum = 0
        var rightSum = nums.sum() - nums[0]

        nums.forEachIndexed { index, num ->
            if (leftSum == rightSum) {
                return index
            }

            if (index + 1 >= nums.size) return -1

            leftSum += nums[index]
            rightSum -= nums[index + 1]
        }

        return -1
    }
}
