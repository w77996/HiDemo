package com.w77996.index;


import java.util.Date;

/**
 * 支持批量刷新
 * @author wdh
 *
 */
public interface IBatchRefresh {
	
	/**
	 * 刷新索引数据
	 * @param startDate
	 * @param endDate
	 */
	void refresh(Date startDate, Date endDate);

}
