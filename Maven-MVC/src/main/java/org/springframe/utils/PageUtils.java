package org.springframe.utils;

import java.util.List;

public class PageUtils<T> {
		private Integer pageIndex=1; // 当前页码
		private Integer pageSize=12; // 每页的数据量
		private Integer pageCount; // 总页码
		private Integer count; // count统计(总数据量 或 其他)
		private double sum;	// sum统计
		private List<T> list;
		
		// 构造方法
		public PageUtils(int pageIndex, int pageSize){
			this.pageIndex = pageIndex;
			this.pageSize = pageSize;
		}
		
		public Integer getPageIndex() {
			return pageIndex;
		}
		public void setPageIndex(Integer pageIndex) {
			this.pageIndex = pageIndex;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getPageCount() {
			return pageCount;
		}
		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}
		public Integer getCount() {
			return count;
		}

	public void setCount(Integer count) {
		this.count = count;

		if (this.pageSize != 0) {
			// 顺带将总页码给赋值了
			this.pageCount = this.count % this.pageSize == 0 ? this.count
					/ this.pageSize : this.count / this.pageSize + 1;
		}
	}
		public double getSum() {
			return sum;
		}
		public void setSum(double sum) {
			this.sum = sum;
		}
		public List<T> getList() {
			return list;
		}
		public void setList(List<T> list) {
			this.list = list;
		}
		
		
}
