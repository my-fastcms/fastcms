<template>
	<div class="system-menu-container">
		<el-dialog title="权限设置" fullscreen v-model="isShowDialog" width="769px">
			<el-tree :data="treeData" 
					show-checkbox 
					node-key="id" 
					:default-expand-all="true"
					ref="treeTable" 
					:props="treeDefaultProps" 
					:default-checked-keys="defaultCheckedKeys"
					:check-strictly="true"
					@check="onCheckTree">
             </el-tree>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, getCurrentInstance, onBeforeMount } from 'vue';
import { ElMessage } from 'element-plus';
import { getRolePermissions, saveRolePermissions } from '/@/api/role/index';
import { i18n } from '/@/i18n/index';
import qs from 'qs';

export default {
	name: 'pagesTree',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			row: null,
			isShowDialog: false,
			treeCheckAll: false,
			treeLoading: false,
			defaultCheckedKeys: [],
			treeData: [],
			treeDefaultProps: {
				children: 'children',
				label: 'label',
			},
			treeSelArr: [],
			treeSelParentArr: [],
			treeLength: 0,
		});
		// 打开弹窗
		const openDialog = (row?: object) => {
			state.isShowDialog = true;
			state.row = row;
			getTreeData();
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
		};
		// 取消
		const onCancel = () => {
			closeDialog();
		};

		const initTreeLengh = (arr: any) => {
			let count = 0;
			arr.map((item) => {
				if (item.children) {
					count += item.children.length;
				}
			});
			state.treeLength = count + arr.length;
		};
		// 全选改变时
		const onCheckAllChange = () => {
			if (state.treeCheckAll) {
				proxy.$refs.treeTable.setCheckedNodes(state.treeData);
			} else {
				proxy.$refs.treeTable.setCheckedKeys([]);
			}
		};
		// 节点选中状态发生变化时的回调
		const onCheckTree = () => {
			state.treeSelArr = [];
			state.treeSelArr = proxy.$refs.treeTable.getCheckedNodes();
			state.treeSelArr.length == state.treeLength ? (state.treeCheckAll = true) : (state.treeCheckAll = false);

			let parentIds = proxy.$refs.treeTable.getHalfCheckedNodes();
			parentIds.forEach(item => {
				//父节点id
				state.treeSelParentArr.push(item.id);
			})
		};
		// 选择元素按钮
		const onSelect = () => {
			let treeArr = proxy.$refs.treeTable.getCheckedNodes();
			if (treeArr.length <= 0) {
				ElMessage.warning('请选择元素');
				return;
			} else {
				console.log(proxy.$refs.treeTable.getCheckedNodes());
			}
		};
		// 初始化树模拟数据
		const getTreeData = () => {
			if(state.row && state.row.id) {
				getRolePermissions(state.row.id).then((res) => {
					state.treeData = res.data;
					i18nTreeData(state.treeData);
					initTreeLengh(state.treeData);
				})
			}		
		};
		// 页面加载前
		onBeforeMount(() => {
			getTreeData();
		});
		//递归处理国际化
		const i18nTreeData= ((treeData) => {
			treeData.forEach(item => {
				item.label = i18n.global.t(item.label);
				if(item.checked) {
					state.defaultCheckedKeys.push(item.id);
				}
				if(item.children && item.children.length >0) {
					i18nTreeData(item.children);
				}
			});
		})

		//提交数据
		const onSubmit = () => {
			const selectPermissionIdList = new Array();
			state.treeSelArr.forEach(item => {
				selectPermissionIdList.push(item.id);
			})
			state.treeSelParentArr.forEach(item => {
				//提交父节点id
				selectPermissionIdList.push(item.id);
			})
			saveRolePermissions(state.row.id, qs.stringify({"permissionIdList": selectPermissionIdList}, {arrayFormat: 'repeat'})).then(() => {
				ElMessage.success("保存成功", function() {
					closeDialog();
				});
			}).catch((res) => {ElMessage.error(res.message);})

		}

		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
			getTreeData,
			onCheckAllChange,
			onCheckTree,
			onSelect,
			...toRefs(state),
		};
	},
};
</script>
