<template>
	<div class="system-menu-container">
		<el-dialog title="权限设置" fullscreen v-model="isShowDialog" width="769px">
			<el-row>
				<el-col :span="6">
					<span>菜单权限</span>
					<el-tree :data="treeData" 
							show-checkbox 
							node-key="id" 
							:default-expand-all="true"
							ref="treeTable" 
							:props="treeDefaultProps" 
							:default-checked-keys="defaultCheckedKeys"
							@check="onCheckTree">
					</el-tree>
				</el-col>
				<el-col :span="8">
					<span>接口权限</span>
					<el-table
						ref="resourceTableRef"
						:data="tableData"
						style="width: 100%;"
						@selection-change="handleSelectionChange"
					>
						<el-table-column type="selection" width="55" />
						<el-table-column property="resourceName" label="接口名称" width="120" />
						<el-table-column property="resourcePath" label="权限标识" show-overflow-tooltip />
					</el-table>
				</el-col>
			</el-row>
			
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
import { reactive, toRefs, ref, getCurrentInstance, onUpdated, onMounted, nextTick } from 'vue';
import { ElMessage } from 'element-plus';
import { getRolePermissions, saveRolePermissions } from '/@/api/role/index';
import { i18n } from '/@/i18n/index';
import qs from 'qs';

export default {
	name: 'pagesTree',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const multipleSelection = ref();
		const resourceTableRef = ref();
		const state = reactive({
			row: null,
			isShowDialog: false,
			treeCheckAll: false,
			treeLoading: false,
			defaultCheckedKeys: [],
			treeData: [],
			tableData: [],
			treeDefaultProps: {
				children: 'children',
				label: 'label',
			},
			treeSelArr: [],
			treeLength: 0,
		});
		// 打开弹窗
		const openDialog = (row?: object) => {
			state.isShowDialog = true;
			state.row = row;
			//getTreeData();
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
			state.treeSelArr = proxy.$refs.treeTable.getCheckedNodes(false, true);
			state.treeSelArr.length == state.treeLength ? (state.treeCheckAll = true) : (state.treeCheckAll = false);
		};
		// 选择元素按钮
		const onSelect = () => {
			let treeArr = proxy.$refs.treeTable.getCheckedNodes(false, true);
			if (treeArr.length <= 0) {
				ElMessage.warning('请选择元素');
				return;
			} else {
				console.log(proxy.$refs.treeTable.getCheckedNodes(false, true));
			}
		};
		// 初始化树模拟数据
		const getTreeData = () => {
			if(state.row && state.row.id) {
				state.defaultCheckedKeys = [];
				getRolePermissions(state.row.id).then((res) => {
					state.treeData = res.data.permissions;
					i18nTreeData(state.treeData);
					initTreeLengh(state.treeData);
					state.tableData = res.data.roleResources;
					onCheckTree();
					selectRes();
				})
			}		
		};

		const selectRes = () => {
			nextTick(() => {
				console.log(">>>data len:" + state.tableData.length);
				state.tableData.forEach(item => {
					//resourceTableRef.value.toggleRowSelection(item, true);
					if(item.roleId != null) {
						proxy.$refs['resourceTableRef'].toggleRowSelection(item, true);
					}
				})
			})
		}

		//表格选中项
		const handleSelectionChange = (val: any) => {
			multipleSelection.value = val;	
		}

		// 页面加载前
		onUpdated(() => {
			console.log("===>onUpdated")
			getTreeData();
		});

		onMounted(() => {
			console.log("===>onMounted")
			// state.tableData.forEach(item => {
			// 	resourceTableRef.value!.toggleRowSelection(item, true);
			// })
		})

		//递归处理国际化
		const i18nTreeData= ((treeData: any) => {
			treeData.forEach(item => {
				item.label = i18n.global.t(item.label);
				if(item.checked && item.children == null) {
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
			const selectResourcePathList = new Array();
			if(multipleSelection.value && multipleSelection.value.length && multipleSelection.value.length >0) {
				multipleSelection.value.forEach(item => {
					selectResourcePathList.push(item.resourcePath);
				});
			}
			saveRolePermissions(state.row.id, qs.stringify({"permissionIdList": selectPermissionIdList, "resourcePathList": selectResourcePathList}, {arrayFormat: 'repeat'})).then(() => {
				ElMessage.success("保存成功");
				closeDialog();
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
			handleSelectionChange,
			...toRefs(state),
		};
	},
};
</script>
