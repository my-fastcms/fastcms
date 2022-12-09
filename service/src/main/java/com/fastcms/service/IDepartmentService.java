package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.TreeNode;
import com.fastcms.entity.Department;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门服务类
 * @author wjun_java@163.com
 * @since 2022-03-23
 */
public interface IDepartmentService extends IService<Department> {

	/**
	 * 根据状态获取部门树状数据
	 * @param status
	 * @return
	 */
	List<DepartmentNode> getDepartmentList(Integer status);

	/**
	 * 获取部门树状数据
	 * @return
	 */
	List<DepartmentNode> getDepartmentList();

	/**
	 * 保存用户部门
	 * @param userId
	 * @param deptIdList
	 */
	void saveUserDepartments(Long userId, List<Long> deptIdList);

	/**
	 * 获取部门员工
	 * @param deptId
	 * @return
	 */
	List<Long> getDepartmentUserIdList(Long deptId);

	/**
	 * 获取用户所在部门集合
	 * @param userId
	 * @return
	 */
	List<Department> getUserDepartment(Long userId);

	class DepartmentNode extends TreeNode {

		private String deptName;
		private String deptDesc;
		private Integer status;
		private String deptLeader;
		private String deptPhone;
		private LocalDateTime created;

		public DepartmentNode(Long id, Long parentId, String deptName, String deptDesc, Integer status, String deptLeader, Integer sortNum, String deptPhone, LocalDateTime created) {
			super(id, parentId, deptName, status == 1, sortNum);
			this.deptName = deptName;
			this.deptDesc = deptDesc;
			this.status = status;
			this.deptLeader = deptLeader;
			this.deptPhone = deptPhone;
			this.created = created;
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

		public String getDeptLeader() {
			return deptLeader;
		}

		public void setDeptLeader(String deptLeader) {
			this.deptLeader = deptLeader;
		}

		public String getDeptPhone() {
			return deptPhone;
		}

		public void setDeptPhone(String deptPhone) {
			this.deptPhone = deptPhone;
		}

		public LocalDateTime getCreated() {
			return created;
		}

		public void setCreated(LocalDateTime created) {
			this.created = created;
		}
	}

	interface DepartmentI18n {
		String DEPARTMENT_CHILDREN_NOT_DELETE = "fastcms.department.children.not.delete";
	}

}
