package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.TreeNode;
import com.fastcms.entity.Department;

import java.util.List;

/**
 * 部门服务类
 * @author wjun_java@163.com
 * @since 2022-03-23
 */
public interface IDepartmentService extends IService<Department> {

	/**
	 * 获取部门树状数据
	 * @return
	 */
	List<DepartmentNode> getDepartmentList();

	class DepartmentNode extends TreeNode {

		private String deptName;
		private String deptDesc;
		private Integer status;
		private Long leaderId;

		public DepartmentNode(Long id, Long parentId, String deptName, String deptDesc, Integer status, Long leaderId, Integer sortNum) {
			super(id, parentId, deptName, status == 1, sortNum);
			this.deptName = deptName;
			this.deptDesc = deptDesc;
			this.status = status;
			this.leaderId = leaderId;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public String getDeptDesc() {
			return deptDesc;
		}

		public void setDeptDesc(String deptDesc) {
			this.deptDesc = deptDesc;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Long getLeaderId() {
			return leaderId;
		}

		public void setLeaderId(Long leaderId) {
			this.leaderId = leaderId;
		}

	}

}
