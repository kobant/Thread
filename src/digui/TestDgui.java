package digui;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 10:25
 */
public class TestDgui {

	public static void main(String[] args) {

		int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		System.out.println(binSearch(arr, 0, arr.length - 1, 6));
	}

	public static int binSearch(int arr[], int start, int end, int key) {
		int mid = start + (end - start) / 2;
		if (arr[mid] == key) {
			System.out.println("查找成功" + key + "在下标" + mid + "处");
			return mid;
		}
		if (start >= end) {
			return -1;
		} else if (key > arr[mid]) {
			System.out.println("查找的下标小于此中间下标,当前下标为" + mid);
			return binSearch(arr, mid + 1, end, key);
		} else if (key < arr[mid]) {
			System.out.println("查找的下标大于中间的下标,当前下标为" + mid);
			return binSearch(arr, start, mid - 1, key);

		}
		return -1;
	}
}
