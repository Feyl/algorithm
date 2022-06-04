package datastructure.tree;

/**
 * Recommend: https://halfrost.com/binary_indexed_tree/
 * Practice: https://leetcode.cn/problems/range-sum-query-mutable/
 *
 * @author Feyl
 * @date 2022/6/4 20:58
 */
public class BinaryIndexedTree {
    //“树状”数组（累加和）
    int[] tree;

    // 数组
    int[] nums;


    /**
     * 初始化 「树状数组」，要默认数组是从 1 开始
     *
     * @param nums 初始化数组
     */
    public BinaryIndexedTree(int[] nums) {
        int n = nums.length;
        // 原数组长度+1， +1的原因是计算 lowbit 时，使用下标 0 会进入死循环。
        this.tree = new int[n + 1];
        this.nums = nums;
        //初始化累加和数组
        for (int i = 0; i < n; i++) {
            add(i + 1, nums[i]);
        }
    }

    private int lowbit(int x) {
        return x & -x;
    }

    /**
     * 在树状数组 x 位置中增加值 u （并迭代更新父元素）
     *
     * @param x 增加值的树状数组中的下标
     * @param u 增加的值
     */
    private void add(int x, int u) {
        for (int i = x; i < tree.length; i += lowbit(i)) {
            tree[i] += u;
        }
    }


    /**
     * 更新数组以及累加和
     *
     * @param x 更新数组的下标
     * @param u 更新的值
     */
    public void update(int x, int u) {
        // 原有的值是 nums[i]，要使得修改为 u，需要增加 u - nums[i]
        add(x + 1, u - nums[x]);
        nums[x] = u;
    }

    /**
     * 返回数组nums中索引l和索引r之间（包含）的nums元素的和（即，nums[l] + nums[l + 1], ..., nums[r]）
     *
     * @param l 起始索引
     * @param r 结束索引
     * @return 区间和
     */
    public int sumRange(int l, int r) {
        return query(r + 1) - query(l);
    }

    /**
     * 查询前缀和
     *
     * @param x 要查询的前缀和的末尾下标
     * @return 以x为结尾数组的前缀和
     */
    public int query(int x) {
        int s = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            s += tree[i];
        }
        return s;
    }
}
